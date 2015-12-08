package starvationevasion.sim;

import starvationevasion.common.*;
import starvationevasion.io.WorldLoader;
import starvationevasion.io.CropCSVLoader;
import starvationevasion.sim.events.AbstractEvent;
import starvationevasion.sim.events.Drought;
import starvationevasion.sim.events.Hurricane;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Simulator class is the main API for the Server to interact with the simulator.
 * This Model class is home to the calculations supporting that API.
 *
 * Each year the model advances, the model applies:
 * <ol>
 * <li>Most Policy Card Effects: Any changes in land use, fertilizer use, and world
 * trade penalty functions are calculated and applied.</li>
 * <li>Changes in land use: At the start of each simulated year, it is assumed that
 * farmers in each region of the world each adjust how they use land based on currently
 * enacted policies, the last year's crop yields and the last year's crop prices so as
 * to maximize individual profit while staying within any enacted laws.</li>
 * <li>Population: In this model, each region's population is based only on data from
 * external population projections and a random number chosen at the start of the game.
 * Note that the occurrence of wide spread famine causes the game to end with all players
 * losing. Thus, it is not necessary to model the after effects of a famine. Random game
 * events such as hurricanes, typhoons, and political unrest are assumed to have
 * negligible effect on population.</li>
 * <li>Sea Level: This depends only on external model data, a random value chosen at the
 * start of the program and the year. Sea level only has two effects on the model:
 * higher sea level reduces costal farm productivity and increases damage probabilities
 * of special storm events (hurricane, and typhoons).</li>
 * <li>Climate: In this model, climate consists of annual participation, average annual
 * day and night temperatures and the annual number of frost free days on a 10 km x 10 km
 * grid across all arable land areas of the Earth.
 * <li>Occurrence of Special Events: Each year, there is a probability of each of many
 * random special events occurring. These include major storms, drought, floods,
 * unseasonable frost, a harsh winter, or out breaks of crop disease, blight, or insects.
 * Special events can also be positive the result in bumper crops in some areas. While
 * these events are random, their probabilities are largely affected by actions players
 * may or may not take. For example, policies encouraging improving irrigation can
 * mitigate the effects of drought, preemptive flood control spending can mitigate the
 * effects of floods and major storms and policies that encourage / discourage
 * monocropping can increase / decrease the probability of crop disease, blight, or
 * insects problems.</li>
 * <li>Farm Product Yield: The current year's yield or each crop in each region is largely
 * a function of the current year's land use, climate and special events as already
 * calculated.</li>
 * <li>Farm Product Need: This is based on each region's population, regional dietary
 * preferences, and required per capita caloric and nutritional needs.</li>
 * <li>Policy Directed Food Distribution: Some player enacted policies may cause the
 * distribution of some foods to take place before the calculation of farm product
 * prices and such distributions may affect farm product prices. For example, sending
 * grain to very low income populations reduces the supply of that grain while not
 * affecting the world demand. This is because anyone who's income is below being able
 * to afford a product, in economic terms, has no demand for that product.</li>
 * <li>Farm Product Demand and price: Product foodPrice on the world market and demand are
 * highly interdependent and therefore calculated together.
 * <li>Food Distribution: In the global market, food distribution is effected by many
 * economic, political, and transportation factors. The Food Trade Penalty Function
 * (see below) is an attempt to assign each country a single number that adjusts the
 * efficiency of food on the global market being traded into that country (even if it
 * originated in that country). The game simulation allocates food products to each
 * country by maximizing the number of people feed under the application of the country's
 * penalty function.</li>
 * <li>Human Development Index: Each country, based on the extent to which its nutritional
 * needs have been met, has its malnutrition, infant mortality, and life expectancy rates
 * adjusted. These are then used to calculate the country's HDI.</li>
 * <li>Player Region Income: Each player receives tax revenue calculated as a percentage
 * of the player's region's total net farm income with any relevant enacted policy applied.</li>
 </ol>
 */


public class Model
{
  public static double EVENT_CHANCE = 0.02;

  EnumRegion debugRegion = EnumRegion.CALIFORNIA;
  private final static Logger LOGGER = Logger.getGlobal(); // getLogger(Model.class.getName())

  // Verbosity of debug information during startup
  //
  private final static Level debugLevel = Level.FINE;

  private final int startYear;
  private int year;

  private World world;

  // The set of world regions includes all of the regions in the enum, plus an
  // extra United States region aggregating all of the US states for book keeping
  // purposes.
  //
  private Region[] regionList = new Region[EnumRegion.SIZE + 1];

  private SeaLevel seaLevel;
  private CropCSVLoader cropLoader = null;

  private List<AbstractEvent> specialEvents = new ArrayList<>();

  public Model(int startYear)
  {

    this.startYear = startYear;
    year = startYear;
    seaLevel = new SeaLevel();
    //System.out.println("MODEL INIT");
  }

  public Region getRegion(EnumRegion r)
  {
    return regionList[r.ordinal()];
  }

  public List<AbstractEvent> getSpecialEvents()
  {
    return specialEvents;
  }

  /**
   * This method is used to create USState objects along with
   * the Region data structure
   */
  protected void instantiateRegions()
  {
    // The load() operation is very time consuming.
    //
    for (int i=0; i<EnumRegion.SIZE; i++)
    {
      regionList[i] = new Region(EnumRegion.values()[i]);
    }

    // Add the US book keeping region.
    //
    regionList[EnumRegion.SIZE] = Region.createBookKeepingRegion("UNITED_STATES");

    try{cropLoader = new CropCSVLoader();} catch (Throwable t){ System.out.println("CROP_LOADER "+t);}
    //ArrayList<CropZoneData> categoryData = cropLoader.getCategoryData();
/*
    for (CropZoneData czd : categoryData)
    {
      System.out.println(czd.toString());
    }
*/
    WorldLoader loader = new WorldLoader(regionList);
    world = loader.getWorld();

    // Now add the US states to the book keeping regions.
    //
    Region unitedStates = regionList[EnumRegion.SIZE];
    for (EnumRegion r : EnumRegion.US_REGIONS)
    { for (Territory t : regionList[r.ordinal()].getTerritories())
      { unitedStates.addTerritory(t);
      }
    }

    Territory[] territories = world.getTerritories();
    int index = Arrays.binarySearch(territories, new Territory("US-Alaska"));
    if (index >= 0) unitedStates.addTerritory(territories[index]);
    else LOGGER.severe("Can not find Alaska?");

    index = Arrays.binarySearch(territories, new Territory("US-Hawaii"));
    if (index >= 0) unitedStates.addTerritory(territories[index]);
    else LOGGER.severe("Can not find Hawaii?");

    float[] avgConversionFactors = new float[EnumFood.SIZE];

    // Traverse all of the regions, estimating the initial yield.
    // Note that this includes the book-keeping regions.
    //
    for (Region region : regionList)
    { // Roll up the population and undernourished for each region.
      //
      region.updatePopulation(Constant.FIRST_YEAR);

      // Update the initial yield.
      //
      region.estimateInitialYield();
    }

    for (Region region : regionList) region.estimateInitialBudget(cropLoader.getCategoryData());
    for (Region region : regionList)
    {
      if (region.getRegionEnum() == null || !region.getRegionEnum().isUS())
      {
        region.estimateInitialCropLandArea(cropLoader.getCategoryData());
      }
    }

    // Now iterate over the enumeration to optimize planting for each game
    // region.
    //
    for (EnumRegion region : EnumRegion.values())
    {
      // TODO : The tile optimization function will only work if we have the
      // CropClimateData structure correctly populated for each of the crops.
      //
      // calculate OTHER_CROPS temp & rain requirements for each country
      for (Territory state : regionList[region.ordinal()].getTerritories())
      {
        // The loader loads 2014 data.  We need to adjust the data for 1981.  Joel's first estimate is
        // to simply multiply all of the territorial data by 50%
        //
        // state.scaleInitialStatistics(.50);
        CropOptimizer optimizer = new CropOptimizer(Constant.FIRST_YEAR, state);
        optimizer.optimizeCrops();
      }
    }

    // Finally, aggregate the totals for all regions (including book keeping).
    //
    if (debugLevel.intValue() < Level.INFO.intValue())
    { Simulator.dbg.println("*** Initialized territory data .............");
    }

    for (Region region : regionList)
    { region.aggregateTerritoryFields(Constant.FIRST_YEAR);
      if (debugLevel.intValue() < Level.INFO.intValue()) printRegion(region, Constant.FIRST_YEAR);
    }
  }

  /**
   *
   * @return the simulation year that has just finished.
   */
  protected int nextYear(ArrayList<PolicyCard> cards, WorldData threeYearData)
  {
    year++;
    LOGGER.info("Advancing year to " + year);

    if (debugLevel.intValue() < Level.INFO.intValue())
    { Simulator.dbg.println("******************************************* SIMULATION YEAR " + year);
    }

    applyPolicies(); // Not started.

    updateLandUse(); // Not started.

    updatePopulation(); // Done.

    updateClimate(); // Done.

    generateSpecialEvents(); // In progress (Alfred).

    applySpecialEvents(); // Done.

    updateFarmProductYield(); // Done.

    updateFarmProductNeed(); // Done.

    updateFarmProductMarket(); // Not started.

    updateFoodDistribution(); // Not started.

    updatePlayerRegionRevenue(); // Not started.

    updateHumanDevelopmentIndex(); // Done.

    appendWorldData(threeYearData); // Done

    if (debugLevel.intValue() < Level.INFO.intValue())
    { Simulator.dbg.println("******************************************* FINAL Stats for " + debugRegion + " in " + year);
      printRegion(regionList[debugRegion.ordinal()], year);
    }

    return year;
  }

  protected void appendWorldData(WorldData threeYearData)
  {
    ArrayList<CropZoneData> categoryData = cropLoader.getCategoryData();

    threeYearData.year = year;
    threeYearData.seaLevel = seaLevel.getSeaLevel(year);
    for (int i=0; i< EnumFood.SIZE; i++)
    {
      CropZoneData currentZone   = categoryData.get(i);
      threeYearData.foodPrice[i] = currentZone.pricePerMetricTon;
    }


    //Region Data
    for (int i=0; i<EnumRegion.SIZE; i++)
    {
      RegionData region = threeYearData.regionData[i];
      region.population = regionList[i].getPopulation(year);
      region.undernourished = regionList[i].getUndernourished();
      region.humanDevelopmentIndex = regionList[i].getHumanDevelopmentIndex();

      region.revenueBalance = regionList[i].getRevenue();

      for (EnumFood food : EnumFood.values())
      {
        region.foodProduced[food.ordinal()] += regionList[i].getCropProduction(food);

        //Simulator keeps income in $1000s but client is given income in millions of dollars.
        long thousandsOfDollars = regionList[i].getCropIncome(food);

        //If a very small amount, then make at least 1 million.
        if ((thousandsOfDollars > 1) && (thousandsOfDollars<500)) thousandsOfDollars+= 500;

        //Round up
        region.foodIncome[food.ordinal()]   += ( thousandsOfDollars + 600)/1000;

        region.farmArea[food.ordinal()] = regionList[i].getCropLand(food);
      }
    }
  }

  // TODO : Not implemented.
  //
  private void applyPolicies()
  {
    if (debugLevel.intValue() < Level.INFO.intValue())
    { Simulator.dbg.println("******************************************* Applying policies");
    }
  }

  private void updateLandUse()
  {
    // TODO : Land use is based on policies.
    // Notes :
    // Start with how much each country is producing v/s how much land they are using.
    // This gives us a yield factor.  If a country with a high yield applies irrigation
    // won't benefit as much as countries with a low yield.  Make an 'S' curve (bezier)
    // with a fit quadratic equation.
    //
    if (debugLevel.intValue() < Level.INFO.intValue())
    { Simulator.dbg.println("******************************************* Updating land use");
    }
  }

  /**
   * Updates the population of each region.
   */
  private void updatePopulation()
  {
    if (debugLevel.intValue() < Level.INFO.intValue())
    { Simulator.dbg.println("******************************************* Updating population");
    }

    // Iterate over all of the regions, including the book keeping regions
    //
    // Note : The total population for the region is updated in region.aggregateTerritoryFields().
    //
    for (int i=0; i<EnumRegion.SIZE; i++)
    {
      regionList[i].updatePopulation(year);
    }

    if (debugLevel.intValue() < Level.INFO.intValue())
    { printCurrentPopulation(regionList[debugRegion.ordinal()], year);
    }
  }

  private void updateClimate()
  {
    // Done.
    //
    if (debugLevel.intValue() < Level.INFO.intValue())
    { Simulator.dbg.println("******************************************* Updating climate");
    }

    world.getTileManager().setClimate(year);

    if (debugLevel.intValue() < Level.INFO.intValue())
    { printCurrentClimate(regionList[debugRegion.ordinal()], year);
    }
  }

  private void generateSpecialEvents()
  {
    // TODO: 12/6/2015 Alfred is working on this.
    //
    if (debugLevel.intValue() < Level.INFO.intValue())
    { Simulator.dbg.println("******************************************* Generating special events");
    }

    //check current year.
    int CURRENT_YEAR = 2015;
    if (year < CURRENT_YEAR)
    {
      //Then there should be a pre-existing event to draw upon. Then
      //there ought to have been a process that loaded the events to draw from
    }
    else
    {
      //If this is the case then examine the players behaviors. Is it probable
      //that their region could experience an event based on the leaders actions
      //through policy. 
    }

    // Temporary code just to make special events happen in the absence of Alfred's timeline.
    //
    int attempts = 5;
    Random rand = new Random();
    while (attempts > 0)
    {
      if (rand.nextFloat() < EVENT_CHANCE)
      {
        if (rand.nextBoolean())
        {
          // do a hurricane
          Region us = regionList[EnumRegion.SIZE];
          int idx = rand.nextInt(us.getTerritories().size()-1) + 1;
          for (Territory territory : us.getTerritories())
          {
            if (idx == 0)
            {
              specialEvents.add(new Hurricane(territory));
              break;
            }
            idx--;
          }
        }
        else
        {
          // do a drought
          int idx = rand.nextInt(EnumRegion.US_REGIONS.length);
          Region usRegion = regionList[EnumRegion.US_REGIONS[idx].ordinal()];
          specialEvents.add(new Drought(usRegion));
        }
      }
      attempts--;
    }
  }

  private void applySpecialEvents()
  {
    if (specialEvents.isEmpty()) return;

    for (Iterator<AbstractEvent> iterator = specialEvents.iterator(); iterator.hasNext(); )
    {
      AbstractEvent event = iterator.next();
      event.applyEffects();

      // remove the event if its duration is 0.
      if (event.getDuration() < 1)
      {
        iterator.remove();
      }
    }
  }

  private void updateFarmProductYield()
  {
    if (debugLevel.intValue() < Level.INFO.intValue())
    { Simulator.dbg.println("******************************************* Updating farm product yield");
    }

    // Iterate over all of the regions, including the book keeping regions.  Each
    // region invokes a territory update and then computes an aggregate number
    // for the region.  Territories that are in both game and book-keeping regions
    // may compute their yield twice, but this has no side effects.
    //
    for (Region region : regionList)
    {
        region.updateYield(year);
    }

    if (debugLevel.intValue() < Level.INFO.intValue())
    { printCropYield(regionList[debugRegion.ordinal()], year);
    }
  }

  private void updateFarmProductNeed()
  {
    if (debugLevel.intValue() < Level.INFO.intValue())
    { Simulator.dbg.println("******************************************* Updating farm product need");
    }

    // Iterate over only the game regions.
    //
    for (int i = 0; i < EnumRegion.SIZE; i++)
    {
      regionList[i].updateCropNeed(year);
    }

    if (debugLevel.intValue() < Level.INFO.intValue())
    { printCropNeed(regionList[debugRegion.ordinal()], year);
    }
  }

  private void updateFarmProductMarket()
  {
    // TODO : Not implemented.
  }

  private void updateFoodDistribution()
  {
    // TODO: Not implemented.  Tie in the new trading optimizer, and subtract revenue.
    // If a territory can't buy enough product then we need to
    // update the undernourishment factor.
    //
  }

  private void updatePlayerRegionRevenue()
  {
    // TODO : Not implemented.  The US will be trading food as a region.  The results
    // of these trades need to be propegated to the US regions.
    //
  }

  private void updateHumanDevelopmentIndex(){
    // TODO: HDI is updated in the roll-up of the territories into regions, based on the
    // undernourished factor.
    //
  }

  public void printCropNeed(Region region, int year)
  {
    // Print just the cell at the capital.
    //
    Simulator.dbg.println("Region " + region.getName() + " crop need per capita : ");
    for (EnumFood food : EnumFood.values()) Simulator.dbg.print(" " + region.getCropNeedPerCapita(food));
    Simulator.dbg.println();

    // Print each territory.
    //
    for (Territory territory : region.getTerritories())
    { Simulator.dbg.print("\t" + territory.getName() + ": ");
      for (EnumFood food : EnumFood.values()) Simulator.dbg.print(" " + territory.getCropNeedPerCapita(food));
      Simulator.dbg.println();
    }

    Simulator.dbg.println("Region " + region.getName() + " total crop need  : ");
    for (EnumFood food : EnumFood.values()) Simulator.dbg.print(" " + region.getTotalCropNeed(year, food));
    Simulator.dbg.println();
  }

  public void printCropYield(Region region, int year)
  {
    // Print just the cell at the capital.
    //
    Simulator.dbg.println("Region " + region.getName() + " crop yield : ");
    for (EnumFood food : EnumFood.values()) Simulator.dbg.print(" " + region.getCropYield(food));
    Simulator.dbg.println();

    // Print each territory.
    //
    for (Territory territory : region.getTerritories())
    { Simulator.dbg.print("\t" + territory.getName() + ": ");
      for (EnumFood food : EnumFood.values()) Simulator.dbg.print(" " + territory.getCropYield(food));
      Simulator.dbg.println();
    }
  }

  public void printCurrentPopulation(Region region, int year)
  {
    Simulator.dbg.println("Region " + region.getName() + " population " + region.getPopulation(year));
    Simulator.dbg.print("\tTerritories : ");
    for (Territory territory : region.getTerritories())
    {
      Simulator.dbg.print("\t" + territory.getPopulation(year));
    }
    Simulator.dbg.println();
  }

  public void printCurrentClimate(Region region, int year)
  {
    // Print just the cell at the capital.
    //
    Simulator.dbg.println("Region " + region.getName() + " climate : ");
    for (Territory territory : region.getTerritories())
    {
      MapPoint capitol = territory.getCapitolLocation();
      LandTile tile = world.getTileManager().getTile(capitol.longitude, capitol.latitude);
      Simulator.dbg.println("\t" + territory.getName() + ": " + tile.toDetailedString());
    }
  }

  public void printRegion(Region region, int year)
  {
    Simulator.dbg.println("Region : " + region.getName());
    Simulator.dbg.print("\tTerritories : ");
    for (Territory territory : region.getTerritories()) {
      Simulator.dbg.print("\t" + territory.getName());
    }
    Simulator.dbg.println();

    printData(region, year, "");

    for (Territory territory : region.getTerritories()) {
      if (debugLevel.intValue() <= Level.FINER.intValue()) printData(territory, year, "\t");
      if (debugLevel.intValue() <= Level.FINEST.intValue())
      {
        for (LandTile tile : territory.getLandTiles())
        { if (tile.getCurrentCrop() != null) Simulator.dbg.println("\t\t" + tile.toString());
        }
      }
    }
  }
  
  public void printData(AbstractTerritory unit, int year, String prefix)
  {
    Simulator.dbg.println(prefix + "Data for " + unit.getName() + " in year " + year);
    Simulator.dbg.print(prefix + prefix + "\t");
    if (unit instanceof Region) Simulator.dbg.print("sum ");

    if (year == Constant.FIRST_YEAR)
    {
      Simulator.dbg.println(" population : " + unit.getPopulation(year));
      Simulator.dbg.print(prefix + prefix + "\t");
      if (unit instanceof Region) Simulator.dbg.print("sum ");
    }
    else Simulator.dbg.print(" population : " + unit.getPopulation(year));

    Simulator.dbg.print(", medianAge : " + unit.getMedianAge());
    Simulator.dbg.print(", births : " + unit.getBirths());
    Simulator.dbg.print(", mortality : " + unit.getMortality());
    Simulator.dbg.print(", migration : " + unit.getMigration());
    Simulator.dbg.print(", undernourished : " + unit.getUndernourished());
    Simulator.dbg.print(", landTotal : " + unit.getLandTotal());
    Simulator.dbg.println();

    Simulator.dbg.print(prefix + "\t            ");
    for (EnumFood food : EnumFood.values()) Simulator.dbg.print("\t" + food);
    Simulator.dbg.println();

    Simulator.dbg.print(prefix + "\tcropYield : ");
    for (EnumFood food : EnumFood.values()) Simulator.dbg.print("\t" + unit.getCropYield(food));
    Simulator.dbg.println();

    Simulator.dbg.print(prefix + "\tcropNeedPerCapita : ");
    for (EnumFood food : EnumFood.values()) Simulator.dbg.print("\t" + unit.getCropNeedPerCapita(food));
    Simulator.dbg.println();

    Simulator.dbg.print(prefix + "\tcropProduction : ");
    for (EnumFood food : EnumFood.values()) Simulator.dbg.print("\t" + unit.getCropProduction(food));
    Simulator.dbg.println();

    Simulator.dbg.print(prefix + "\tcropIncome : ");
    for (EnumFood food : EnumFood.values()) Simulator.dbg.print("\t" + unit.getCropIncome(food));
    Simulator.dbg.println();

    Simulator.dbg.print(prefix + "\tlandCrop : ");
    for (EnumFood food : EnumFood.values()) Simulator.dbg.print("\t" + unit.getCropLand(food)); // Yes, they named it backwards.
    Simulator.dbg.println();

    if (unit instanceof Territory)
    {
      Simulator.dbg.print(prefix + "\t            ");
      for (EnumFarmMethod method : EnumFarmMethod.values()) Simulator.dbg.print("\t" + method);
      Simulator.dbg.println();
      Simulator.dbg.print(prefix + "\tcultivationMethod : ");
      for (EnumFarmMethod method : EnumFarmMethod.values()) Simulator.dbg.print("\t" + unit.getMethod(method));
      Simulator.dbg.println();
    }
  }
}
