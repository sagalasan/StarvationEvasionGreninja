package starvationevasion.io.CSVhelpers;

import spring2015code.model.geography.Territory;
import starvationevasion.common.EnumFood;
import spring2015code.common.AbstractScenario;
import spring2015code.common.EnumGrowMethod;

//TODO: comments, demographic & crop methods
/**
 * Class with static methods for filling in country data omitted
 * from CSV file.
 */
public final class CountryCSVDataGenerator implements CountryCSVDefaultData
{
  private static final int START_YEAR = AbstractScenario.START_YEAR;
  
  /**
   * Assigns values to country's fields based on world median values
   * for demographics and ratio of arable land/total land.
   * @param country   country with missing data
   * @param field     field that needs to be assigned
   */
  public static void fixDemographic(Territory country, String field)
  {
    switch (field)
    {
      case "averageAge":
        country.setMedianAge(WORLD_AVG_AGE);
        break;
      case "births":
        country.setBirths(WORLD_BIRTH_RATE);
        break;
      case "mortality":
        country.setMortality(START_YEAR, WORLD_MORTALITY);
        break;
      case "migration":
        country.setMigration(WORLD_MIGRATION);
        break;
      case "undernourish":
        country.setUndernourished(START_YEAR, WORLD_UNDERNOURISH/100); //divide int by 100
        break;
      case "arableOpen":
        country.setArableLand(START_YEAR, WORLD_PERCENT_ARABLE * country.getLandTotal(START_YEAR));
        break;
    }
  }
  
  /**
   * Assigns values to agriculturalUnit's fields for a particular crop based on world median need for
   * that crop. Assumes agriculturalUnit imports its entire need for the crop for the sake of simplicity and
   * also because countries for which agricultural data is unavailable tend to import much of their
   * food (e.g., Monaco, Bahrain, Singapore).
   * @param agriculturalUnit   agriculturalUnit with missing data
   * @param crop      crop for which we need to fill in production, imports, exports, and land
   */
  public static void fixCropData(Territory agriculturalUnit, EnumFood crop)
  {
    int population = agriculturalUnit.getPopulation(START_YEAR);
    // get world median ton/capita
    int index = -1;
    for (int i = 0; i < CountryCSVDefaultData.cropTypes.length; i++)
    {
      if (crop == CountryCSVDefaultData.cropTypes[i])
      {
        index = i;
        break;
      }
    }
    double worldPerCap = CountryCSVDefaultData.cropTonPerCapita[index];
    double countryNeed = worldPerCap * agriculturalUnit.getPopulation(START_YEAR);
    
    // set agriculturalUnit's need to world per capita media need * population; assume all imported
    agriculturalUnit.setCropProduction(START_YEAR, crop, 0);
    // agriculturalUnit.setCropExport(START_YEAR, crop, 0);
    // agriculturalUnit.setCropImport(START_YEAR, crop, countryNeed);
    agriculturalUnit.setCropLand(START_YEAR, crop, 0);
    agriculturalUnit.setCropYield(START_YEAR, crop, 0);
    agriculturalUnit.setCropNeedPerCapita(crop, worldPerCap);
  }
  
  /**
   * Assigns values to agriculturalUnit's fields for organic, conventional, and GMO percentages when
   * one or more of them is missing or invalid, using world median values.
   * @param agriculturalUnit     Territory with missing or invalid data
   */
  public static void fixGrowMethods(Territory agriculturalUnit)
  {  
      //assign world median values to all methods      
      agriculturalUnit.setMethodPercentage(START_YEAR, EnumGrowMethod.ORGANIC, WORLD_ORGANIC);
      agriculturalUnit.setMethodPercentage(START_YEAR, EnumGrowMethod.CONVENTIONAL, WORLD_CONVENTIONAL);
      agriculturalUnit.setMethodPercentage(START_YEAR, EnumGrowMethod.GMO, WORLD_GMO);
  }
  
 
}
