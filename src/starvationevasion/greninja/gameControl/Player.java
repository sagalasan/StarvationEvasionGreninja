package starvationevasion.greninja.gameControl;

import starvationevasion.common.EnumRegion;
import starvationevasion.common.PolicyCard;

// TODO: perhaps we should make a player interface?

/**
 * Class that represents a generic player (human player and AI player).
 */
public class Player
{
  EnumRegion region;
  PolicyCard[] cards; // Cards the player is currently holding
  int votingPolicyCount = 0;

  public Player(EnumRegion region, PolicyCard[] cards)
  {
    this.region = region;
    this.cards = cards;
  }

  public EnumRegion getPlayerRegion()
  {
    return this.region;
  }

  public void setPlayerRegion(EnumRegion region)
  {
    this.region = region;
  }

  /**
   * Get the player's current hand
   * @return
   */
  public PolicyCard[] getPlayerHand()
  {
    return this.cards;
  }

  /**
   * Set the current player's hand
   * @param hand
   */
  public void setPlayerHand(PolicyCard[] hand)
  {
     this.cards = hand;
  }

  /**
   * Get a specific card from the player's hand
   * @param index
   * @return
   */
  public PolicyCard getCard(int index)
  {
    return this.cards[index];
  }

  // TODO: I'm not entirely sure if we want to handle the voting and drafting in this class
  public void draft()
  {

    votingPolicyCount = 0;
  }
  public void vote() {}
  public void discardCards() {}
}
