package starvationevasion.greninja.gameControl;

import starvationevasion.common.EnumPolicy;
import starvationevasion.common.EnumRegion;

import java.util.ArrayList;
import java.util.List;

// TODO: perhaps we should make a player interface?

/**
 * Class that represents a generic player (human player and AI player).
 */
public class Player
{
  EnumRegion region;
  List<EnumPolicy> cards, discardPile, voteRequiredPolicies;
  int votingPolicyCount = 0;

  Player() {}

  Player(EnumRegion region, List<EnumPolicy> cards)
  {
    discardPile = new ArrayList<EnumPolicy>();
    voteRequiredPolicies = new ArrayList<EnumPolicy>();

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
  public List<EnumPolicy> getPlayerHand()
  {
    return this.cards;
  }

  /**
   * Set the current player's hand
   * @param hand
   */
  public void setPlayerHand(List<EnumPolicy> hand)
  {
     this.cards = hand;
  }

  /**
   * Add a card to the player's hand.
   * @param card
   */
  public void addCard(EnumPolicy card)
  {
    if (cards.size() < 7)
    {
      cards.add(card);
    }
    else System.err.println("Hand is already full");
  }

  /**
   * Removes a card from the player's hand and puts it in the discard pile.
   * @param index
   */
  public void discardCard(int index)
  {
    EnumPolicy discarded = cards.remove(index);
    discardPile.add(discarded);
  }

  /**
   * Get a specific card from the player's hand
   * @param index
   * @return
   */
  public EnumPolicy getCard(int index)
  {
    return cards.get(index);
  }

  /**
   * Returns the card at the head of the given card list.
   * @param cardPile
   * @return
   */
  public EnumPolicy firstCard(List<EnumPolicy> cardPile)
  {
    return cardPile.get(0);
  }

  // TODO: I'm not entirely sure if we want to/how we will handle the voting and drafting in this class
  /**
   * Gets all the policies drafted by other players that need a vote.
   * @return
   */
  public List<EnumPolicy> getDraftedPolicies(List<EnumPolicy> draftedPolicies)
  {
    voteRequiredPolicies = draftedPolicies;
    return voteRequiredPolicies;
  }

  /**
   * The card in the drafted policies from all 7 regions that the player decided to vote for.
   * @param index
   * @return
   */
  public EnumPolicy vote(int index)
  {
    return voteRequiredPolicies.get(index);
  }

  /**
   * The player-selected card during the drafting phase.
   * @param index
   */
  public void draft(int index)
  {
    EnumPolicy selectedCard = getCard(index);
    // TODO: add this to a policy pile?
  }

  // TODO: communication with other players
}
