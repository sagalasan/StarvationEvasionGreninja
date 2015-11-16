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
  PolicyCard[] voteRequiredPolicies;
  int votingPolicyCount = 0;

  public Player(EnumRegion region, PolicyCard[] cards)
  {
    this.region = region;
    this.cards = cards;
  }

  /**
   * Get the region that the player was assigned.
   * @return
   */
  public EnumRegion getPlayerRegion()
  {
    return this.region;
  }

  /**
   * Set the player's region.
   * @param region
   */
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
  /**
   * Gets all the policies drafted by other players that need a vote.
   * @return
   */
  public PolicyCard[] getDraftedPolicies(PolicyCard[] draftedPolicies)
  {
    voteRequiredPolicies = draftedPolicies;
    return voteRequiredPolicies;
  }

  /**
   * The card in the drafted policies from all 7 regions that the player decided to vote for.
   * @param index
   * @return
   */
  public PolicyCard vote(int index)
  {
    return voteRequiredPolicies[index];
  }

  /**
   * The player-selected card during the drafting phase.
   * @param index
   */
  public void draft(int index)
  {
    PolicyCard selectedCard = getCard(index);
  }

  public void discardCards() {}

  // TODO: communication with other players
}
