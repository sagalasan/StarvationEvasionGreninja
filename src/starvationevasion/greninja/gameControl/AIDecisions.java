package starvationevasion.greninja.gameControl;

import starvationevasion.common.EnumRegion;
import starvationevasion.common.PolicyCard;
import starvationevasion.greninja.model.AIPlayer;
import starvationevasion.greninja.model.State;

import java.util.List;


/**
 * Created by Erin on 11/27/15.
 */
public class AIDecisions
{
  private AIPlayer player;
  List<PolicyCard> playerHand;
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
    playerHand = player.getPlayerHand();

    localRegion = new State(EnumRegion.HEARTLAND);
    worldRegion = new State(EnumRegion.CENTRAL_ASIA);

    getCurrentInfo();
  }

  public void getCurrentInfo()
  {
    regionPop = localRegion.getPopulation(turnNumber);
    worldPop = worldRegion.getPopulation(turnNumber);
    localHDI = localRegion.getHDI(turnNumber);
  }

  /**
   * Looks at each individual card and returns the index of the card that has the most beneficial effect.
   * @param cards
   * @return
   */
  public int analyzeCards(List<PolicyCard> cards)
  {
    int numCards = cards.size();
    //
    return 0;
  }

  // Voting and drafting will call analyzeCards?
  // vote()
  // draft()
}
