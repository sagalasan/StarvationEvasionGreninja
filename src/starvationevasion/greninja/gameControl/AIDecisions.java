package starvationevasion.greninja.gameControl;

//import spring2015code.model.geography.Region;
import starvationevasion.common.*;
//import starvationevasion.greninja.gui.componentPane.RegionalStatistics;
import starvationevasion.greninja.model.AIPlayer;
import starvationevasion.greninja.model.State;

import java.util.*;


/**
 *
 */
public class AIDecisions
{
  private AIPlayer player;
  private int turnNumber;

  private EnumRegion playerRegion; // TODO: Should this be a State or an EnumRegion
  private State california, heartland, northernPlains, southeast, northernCrescent, southernPlains, mountain;
  private double regionPop, worldPop, localHDI, worldHDI;
  private double localUndernourishedPop, globalUndernourishedPop;

  private double[] probabilities;
  private LinkedHashMap<Double, Integer> rankedCards = new LinkedHashMap<Double, Integer>();
  private Random rand = new Random();

  private boolean communication = false;
  private PolicyCard suggestedCard;

  private boolean DEBUG = true;

  private boolean serverCreated = false;

  // Default constructor is test constructor. To be removed possibly.
  public AIDecisions()
  {
    getCurrentInfo();

    // Higher rank = higher chance of selection
    rankedCards.put(10.0, 3);
    rankedCards.put(20.0, 1);
    rankedCards.put(30.0, 2);
    rankedCards.put(40.0, 5);
    rankedCards.put(50.0, 4);
    chooseCard(rankedCards);
  }

  // Should take "State state" as an argument too?
  // State localState, State globalState
  public AIDecisions(AIPlayer player, int turnNumber)
  {
//    this.player = player;
//    this.turnNumber = turnNumber;
    // TODO: hardcoded for testing
    this.player = player;
    this.turnNumber = 2;

    getCurrentInfo();
  }

  /**
   * Constructor for the AI player to use if another player is communicating with them.
   * @param player        the AI player to control.
   * @param turnNumber    the current turn.
   * @param suggestedCard the card to consider voting for.
   */
  public AIDecisions(AIPlayer player, int turnNumber, PolicyCard suggestedCard)
  {
    this.communication = true;
    this.suggestedCard = suggestedCard;

    getCurrentInfo();
  }

  /*
   * Initialize array of probabilities.
   */
  private void initializeProbabilities()
  {
    if (DEBUG) System.out.println("AIDecisions: initializeProbabilities()");
//    probabilities = new double[player.getPlayerHand().size()];
    probabilities = new double[rankedCards.size()];

    int i = 0;
    double s = 0;
    for (double probability : rankedCards.keySet())
    {
      probabilities[i] = probability;
      if (DEBUG) System.out.println("probability[" + i + "]: " + probabilities[i]);
      s += probabilities[i];
      i++;
    }

    // Proportionate card selection
    if (s == 0)
    {
      s = 1 / probabilities.length;
      for (i = 0; i < probabilities.length; i++)
      {
        probabilities[i] = s;
      }
    }
    else
    {
      for (i = 0; i < probabilities.length; i++)
      {
        probabilities[i] /= s;
      }
    }
  }

  private void getCurrentInfo()
  {
    //playerRegion = player.getPlayerRegion();
    california = State.CALIFORNIA;
    heartland = State.HEARTLAND;
    northernPlains = State.NORTHERN_PLAINS;
    southeast = State.SOUTHEAST;
    northernCrescent = State.NORTHERN_CRESCENT;
    southernPlains = State.SOUTHERN_PLAINS;
    mountain = State.MOUNTAIN;


//    regionPop = localRegion.getPopulation(turnNumber);
//    worldPop = globalRegion.getPopulation(turnNumber);
//    localHDI = localRegion.getHDI(turnNumber);
//    localUndernourishedPop = localRegion.getUndernourishedPopulation();
//    globalUndernourishedPop = globalRegion.getUndernourishedPopulation();
    // TODO: get food cost?

  }

  public boolean selectRegion(Set<EnumRegion> availableRegions) // Handle race conditions here or somewhere else?
  {
    if (!availableRegions.isEmpty())
    {
      player.setPlayerRegion(availableRegions.iterator().next());
      return true;
    }
    else return false;
  }

  public int voteCard(LinkedHashMap<Integer, Integer> rankedCards, PolicyCard suggestedCard)
  {
    // TODO: Works similar to chooseCard but adds weight to suggestedCard?
    return 0;
  }

  /**
   * Chooses a card based on its effects on the game state. A card that benefits the player is more likely to be selected,
   * but it is not guaranteed.
   * @param rankedCards
   * @return the index of the card to be selected.
   */
  public int chooseCard(LinkedHashMap<Double, Integer> rankedCards)
  {
    if (DEBUG) System.out.println("AIDecisions: chooseCard()");

    if (DEBUG) for (double probability : rankedCards.keySet())
    {
      System.out.println("Rank: " + probability);
    }

    if (DEBUG) for (int index : rankedCards.values())
    {
      System.out.println("Index: " + index);
    }

    // key = rank, value = index
    // Bigger rank number = higher probability
    if (probabilities == null) initializeProbabilities();
    List<Integer> rankedCardsIndices = new ArrayList<Integer>(rankedCards.values());

    int selectedIndex = 0; // Initialize to zero

    // Select card based on probability
    double q = rand.nextDouble(); // Uniformly distributed random number
    for (int i = 0; i < probabilities.length; i++)
    {
      if (DEBUG) System.out.println("q - probabilities[" + i + "] = " + (q - probabilities[i]));
      if ((q -= probabilities[i]) < 0)
      {
        selectedIndex = i;
        if (DEBUG) System.out.println("Selected index in HashMap: " + selectedIndex);
        if (DEBUG) System.out.println("Index of card to be chosen: " + rankedCardsIndices.get(selectedIndex));
        return rankedCardsIndices.get(selectedIndex);
      }
    }
    return -1;
  }

  /*
   * Sees the potential future world values if a card is played.
   */
  private double cardEffects(PolicyCard card)
  {
    EnumPolicy cardType = card.getCardType();
    double cardRank = 0;

    switch (cardType)
    {
      case Clean_River_Incentive:
        break;
      case Covert_Intelligence:
        break;
      case Educate_the_Women_Campaign:
        break;
      case Efficient_Irrigation_Incentive:
        break;
      case Ethanol_Tax_Credit_Change:
        break;
      case Fertilizer_Subsidy:
        break;
      case Foreign_Aid_for_Farm_Infrastructure:
        break;
      case GMO_Seed_Insect_Resistance_Research:
        break;
      case International_Food_Relief_Program:
        break;
      case Loan:
        break;
      case MyPlate_Promotion_Campaign:
        break;
    }

//    int x = card.getX();
//    int y = card.getY();
//    int z = card.getZ();
//
//    double currentPopAvg = (california.getPopulation(turnNumber) + heartland.getPopulation(turnNumber) +
//      northernPlains.getPopulation(turnNumber) + southeast.getPopulation(turnNumber) + northernCrescent.getPopulation(turnNumber) +
//      southernPlains.getPopulation(turnNumber) + mountain.getPopulation(turnNumber)) / 7;
//
//    // TODO: subtract from the populations
//    double projectedPopAvg = (california.getPopulation(turnNumber) + heartland.getPopulation(turnNumber) +
//        northernPlains.getPopulation(turnNumber) + southeast.getPopulation(turnNumber) + northernCrescent.getPopulation(turnNumber) +
//        southernPlains.getPopulation(turnNumber) + mountain.getPopulation(turnNumber)) / 7;
//
//    if (projectedPopAvg > currentPopAvg) // If future population is bigger
//    {
//      if ((currentPopAvg / projectedPopAvg) >= 0.01 && (currentPopAvg / projectedPopAvg) <= 0.05)
//      {
////        cardRank +=
//      }
////      else cardRank +=
//    }
////    else cardRank +=
//
//    // Get average malnourished population
//    // Get projected average malnourished population
//    // If future average is higher, subtract from rank
//
//
//
//
//
//
////    int newRegionBalance = regionBalance - x;
////    int newRegionPop = regionPop - y;
////    int newWorldPop = worldPop - z;
//
//    // Minimizing undernourished population is priority?
//    // Minimizing money loss is next most important
//    // Increase in population is next priority (but check to make sure it's not too big)
////    if (newWorldPop / worldPop >= 0.01 && newWorldPop / worldPop <= 0.05) TODO: ideal population growth is within this range
//
    return 0.0;
  }

  /**
   * Looks at each individual card and returns the index of the card that may have the most beneficial effect.
   * @param cards   the cards currently in the player's hand
   * @return
   */
  public int analyzeCards(List<PolicyCard> cards)
  {
    LinkedHashMap<Double, Integer> rankedCards = new LinkedHashMap<Double, Integer>(); // <rank, index>  or <rank, card>?
    if (cards.size() < 7)
    {
      // TODO: need 'draw from deck' functionality
//      player.drawCard
    }
    int chosenIndex = 0; // Zero by default
    for (int i = 0; i < cards.size(); i++)
    {
      // TODO: assign each card a "rank" or some value that denotes how beneficial it is
      // Take an average for all the effects and use that average as the key?
      double cardRank = cardEffects(cards.get(i));
      rankedCards.put(cardRank, i);
    }

    chosenIndex = chooseCard(rankedCards);
    // should i do this in AIView instead?
    if (chosenIndex < 0)
    {
//      player.discardCard()
      // cardt to discard is card with lowest rank
      // draw card
      // run choosecard
    }
    return chosenIndex;
  }

  // TODO: Remove this main later (for testing only)
  public static void main(String[] args)
  {
    new AIDecisions();
  }

}
