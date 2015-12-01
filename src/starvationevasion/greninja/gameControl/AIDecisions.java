package starvationevasion.greninja.gameControl;

import starvationevasion.common.EnumRegion;
import starvationevasion.common.PolicyCard;
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
//  List<PolicyCard> playerHand;

  private State localRegion;
  private State worldRegion;
  private double regionPop, worldPop, localHDI, worldHDI;

  private double[] probabilities;
  private LinkedHashMap<Integer, Integer> rankedCards = new LinkedHashMap<Integer, Integer>();
  private Random rand = new Random();

  private boolean communication = false;
  private PolicyCard suggestedCard;

  private boolean DEBUG = true;

  // Test constructor. To be removed possibly.
  public AIDecisions()
  {
    // Higher rank = higher chance of selection
    rankedCards.put(10, 3);
    rankedCards.put(20, 1);
    rankedCards.put(30, 2);
    rankedCards.put(40, 5);
    rankedCards.put(50, 4);
    chooseCard(rankedCards);
  }

  public AIDecisions(AIPlayer player, int turnNumber)
  {
    // TODO: hardcoded for testing
    this.player = player;
    this.turnNumber = 2;
    //    this.turnNumber = turnNumber;
//    playerHand = player.getPlayerHand();

    //Temporary code. Subject to change.
    localRegion = State.CALIFORNIA;
    worldRegion = State.HEARTLAND;

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
  }

  private void initializeProbabilities()
  {
    if (DEBUG) System.out.println("AIDecisions: initializeProbabilities()");
//    probabilities = new double[player.getPlayerHand().size()];
    probabilities = new double[rankedCards.size()];

    int i = 0;
    double s = 0;
    for (int probability : rankedCards.keySet())
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
    regionPop = localRegion.getPopulation(turnNumber);
    worldPop = worldRegion.getPopulation(turnNumber);
    localHDI = localRegion.getHDI(turnNumber);
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
  public int chooseCard(LinkedHashMap<Integer, Integer> rankedCards)
  {
    if (DEBUG) System.out.println("AIDecisions: chooseCard()");

    if (DEBUG) for (int probability : rankedCards.keySet())
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
    if (DEBUG) System.out.println("Selected index: -1");
    return -1;
  }

  /**
   * Looks at each individual card and returns the index of the card that may have the most beneficial effect.
   * @param cards   the cards currently in the player's hand
   * @return
   */
  public int analyzeCards(List<PolicyCard> cards)
  {
    LinkedHashMap<Integer, Integer> rankedCards = new LinkedHashMap<Integer, Integer>(); // <rank, index>  or <rank, card>?
    int numCards = cards.size();
    int chosenIndex = 0; // Zero by default
    for (int i = 0; i < numCards; i++)
    {
      // TODO: Sort cards from best to worst??
    }

    chosenIndex = chooseCard(rankedCards);
    return chosenIndex;
  }

  // TODO: Remove this main later (for testing only)
  public static void main(String[] args)
  {
    new AIDecisions();
  }

}
