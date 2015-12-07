package starvationevasion.greninja.gameControl;

import starvationevasion.common.*;
//import starvationevasion.greninja.gui.componentPane.RegionalStatistics;
import starvationevasion.greninja.model.AIPlayer;
import starvationevasion.greninja.model.State;

import java.util.*;


/**
 * The AIDecision class delegates how an AI player will choose which cards to draft or vote for.
 * @author Erin Sosebee
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

  private boolean DEBUG = false;

  // Default constructor is test constructor. To be removed possibly.
  public AIDecisions()
  {
    setCurrentInfo();

    // Higher rank = higher chance of selection
    rankedCards.put(10.0, 3);
    rankedCards.put(20.0, 1);
    rankedCards.put(30.0, 2);
    rankedCards.put(40.0, 5);
    rankedCards.put(50.0, 4);
    chooseCard(rankedCards);
  }


  /**
   * Constructor to create an AIDecisions object.
   * @param player      the AI player to draft/vote for cards.
   * @param turnNumber  the current turn in the game.
   */
  public AIDecisions(AIPlayer player, int turnNumber)
  {
    this.player = player;
    this.turnNumber = turnNumber;
    setCurrentInfo();
  }

  private void setCurrentInfo()
  {
    playerRegion = player.getPlayerRegion();
    california = State.CALIFORNIA;
    heartland = State.HEARTLAND;
    northernPlains = State.NORTHERN_PLAINS;
    southeast = State.SOUTHEAST;
    northernCrescent = State.NORTHERN_CRESCENT;
    southernPlains = State.SOUTHERN_PLAINS;
    mountain = State.MOUNTAIN;
  }

  /*
   * Initialize array of probabilities.
   */
  private void initializeCardProbabilities()
  {
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

  /**
   * Selects the region for the player.
   * @param availableRegions  a set of the currently available regions.
   * @return  true if the player has selected a region else false.
   */
  public boolean selectRegion(Set<EnumRegion> availableRegions) // Handle race conditions here or somewhere else?
  {
    if (!availableRegions.isEmpty())
    {
      player.setPlayerRegion(availableRegions.iterator().next());
      return true;
    }
    else return false;
  }

  /**
   * Casts a vote during the voting phase.
   * @param rankedCards   a hash map of cards that are ranked by probability of being chosen.
   * @return the index for the card to be voted for.
   */
  public int voteCard(LinkedHashMap<Integer, Integer> rankedCards)
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

    // rankedCards is a LinkedHashMap<K, V> where K = probability, V = the index of the card in the player's hand
    // A higher "probability" means that a card is more likely to be selected
    if (probabilities == null) initializeCardProbabilities();
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
    return 0; // Return the first card if all else fails
  }

  /*
   * Sees the potential future world values if a card is played.
   */
  private double cardEffects(PolicyCard card)
  {
    int x, y;
    EnumPolicy cardType = card.getCardType();
    double cardRank = 0;

    switch (cardType)
    {
      case Clean_River_Incentive:
        // X% tax break for farmers who reduce fertilizer outflow by Y%
        x = card.getX();
        y = card.getY();
        cardRank = rand.nextDouble(); // Just assigning cards random ranks now
        break;
      case Covert_Intelligence:
        // Ignoring covert intelligence now
        cardRank = 0;
        break;
      case Educate_the_Women_Campaign:
        // US sends out 7X million dollars to foreign country to educate women
        x = (card.getX() * 7) * 1000000;
        cardRank = rand.nextDouble();
        break;
      case Efficient_Irrigation_Incentive:
        // X% of money spent by farmers in player's region for improved irrigation efficiency
        // is tax deductible
        x = card.getX();
        cardRank = rand.nextDouble();
        break;
      case Ethanol_Tax_Credit_Change:
        // Currently, ethanol producer located in player's region is entitled to Y% tax credit
        // to cost of ethanol production. This policy changes it to X%.
        x = card.getX();
        y = card.getY();
        cardRank = rand.nextDouble();
        break;
      case Fertilizer_Subsidy:
        // Offers subsidy of X% rebate to farmers in player region purchasing commercial fertilizer
        x = card.getX();
        cardRank = rand.nextDouble();
        break;
      case Foreign_Aid_for_Farm_Infrastructure:
        // US sends 7X million dollars in foreign aid for capital development/farming infrastructure of target world region
        x = (card.getX() * 7) * 1000000;
        cardRank = rand.nextDouble();
        break;
      case GMO_Seed_Insect_Resistance_Research:
        // Each participating region spends X million dollars to fund GMO seed research
        x = card.getX() * 1000000;
        cardRank = rand.nextDouble();
        break;
      case International_Food_Relief_Program:
        // Each participating region spends X million dolars to purchase their own regions commodity food
        // for relief of world hunger
        x = card.getX() * 1000000;
        cardRank = rand.nextDouble();
        break;
      case Loan:
        // Target player region lends you X million dollars at 10% interest.
        x = card.getX() * 1000000;
        cardRank = rand.nextDouble();
        break;
      case MyPlate_Promotion_Campaign:
        // Player spends X million dollars on advertising campaign within region promoting public awareness of USDA's nutrition guide
        x = card.getX() * 1000000;
        cardRank = rand.nextDouble();
        break;
    }
    return cardRank;
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
    }

    int chosenIndex = 0; // Zero by default
    for (int i = 0; i < cards.size(); i++)
    {
      // Assign a "rank" to each card to determine how likely it will be selected by the AI
      double cardRank = cardEffects(cards.get(i));
      rankedCards.put(cardRank, i);
    }
    chosenIndex = chooseCard(rankedCards);
    return chosenIndex;
  }

  public static void main(String[] args)
  {
    new AIDecisions();
  }
}
