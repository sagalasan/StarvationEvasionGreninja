package starvationevasion.greninja.gameControl;

import starvationevasion.common.EnumRegion;
import starvationevasion.common.PolicyCard;
import starvationevasion.common.messages.AvailableRegions;
import starvationevasion.greninja.model.AIPlayer;
import starvationevasion.greninja.model.State;

import java.util.*;


/**
 * Created by Erin on 11/27/15.
 */
public class AIDecisions
{
  private AIPlayer player;
//  List<PolicyCard> playerHand;
  private int turnNumber;
  private State localRegion;
  private State worldRegion;
  private double regionPop, worldPop, localHDI, worldHDI;
  private double[] probabilities;
  private HashMap<Integer, Integer> rankedCards = new HashMap<Integer, Integer>();
  private Random rand = new Random();

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

  private void initializeProbabilities()
  {
//    probabilities = new double[player.getPlayerHand().size()];
    probabilities = new double[rankedCards.size()];

    int i = 0;
    double s = 0;
    for (int probability : rankedCards.keySet())
    {
      probabilities[i] = probability;
      s += probabilities[i];
      i++;
    }
//    for (int i = 0; i < probabilities.length; i++)
//    {
////      probabilities[i] = rankedCards.
//      s += probabilities[i];
//    }

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

  /**
   * Chooses a card based on its effects on the game state. A card that benefits the player is more likely to be selected,
   * but it is not guaranteed.
   * @param rankedCards
   * @return the index of the card to be selected.
   */
  public int chooseCard(HashMap<Integer, Integer> rankedCards)
  {
    // key = rank, value = index
    // Bigger rank number = higher probability
    if (probabilities == null) initializeProbabilities();

    int selectedIndex = 0; // Initialize to zero

    // Select card based on probability
    double q = rand.nextDouble(); // Uniformly distributed random number
    for (int i = 0; i < probabilities.length; i++)
    {
      if ((q -= probabilities[i]) < 0)
      {
        selectedIndex = i;
        if (DEBUG) System.out.println("Selected index in HashMap: " + selectedIndex);
        return selectedIndex;
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
    HashMap<Integer, Integer> rankedCards = new HashMap(); // <rank, index>  or <rank, card>?
    int numCards = cards.size();
    int chosenIndex = 0; // Zero by default
    for (int i = 0; i < numCards; i++)
    {
      // rank cards
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
