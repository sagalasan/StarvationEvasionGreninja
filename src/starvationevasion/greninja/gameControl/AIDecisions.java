package starvationevasion.greninja.gameControl;

import starvationevasion.common.EnumRegion;
import starvationevasion.common.PolicyCard;
import starvationevasion.common.messages.AvailableRegions;
import starvationevasion.greninja.model.AIPlayer;
import starvationevasion.greninja.model.State;

import java.util.List;
import java.util.Set;


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

  public void getCurrentInfo()
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
   * Looks at each individual card and returns the index of the card that has the most beneficial effect.
   * @param cards   the cards currently in the player's hand
   * @return
   */
  public int analyzeCards(List<PolicyCard> cards)
  {
    int numCards = cards.size();
    int chosenIndex = 0; // Zero by default
    for (int i = 0; i < numCards; i++)
    {
      //
    }
    return chosenIndex;
  }

  // Voting and drafting will call analyzeCards?
  // Alternatively:
  // vote()
  // draft()
}
