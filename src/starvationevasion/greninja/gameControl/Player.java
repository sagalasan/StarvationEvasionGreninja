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
   * @param card    EnumPolicy to add to hand.
   * @return        true if addCard succeeded.
   */
  public boolean addCard(EnumPolicy card)
  {
    if (cards.size() < 7)
    {
      cards.add(card);
      return true;
    }
    else
    {
      return false;
    }
  }

  /**
   * Removes a card from the player's hand and puts it in the discard pile.
   * @param index   index of card selected.
   * @return        true if successful, false if no card to discard.
   */
  public boolean discardCard(int index)
  {
    if(index < cards.size())
    {
      EnumPolicy discarded = cards.remove(index);
      discardPile.add(discarded);
      return true;
    }
    else
    {
      return false;
    }
  }

  /**
   * Discard three cards.  Get the cards at the specified index then
   * remove from hand and put into discard pile.
   * @param indices       3 indexes
   */
  public boolean discardThree(int[] indices)
  {
    if(cards.size() >= 3)
    {
      EnumPolicy[] cardsToDiscard = new EnumPolicy[3];
      for (int i = 0; i < 3; ++i)
      {
        cardsToDiscard[i] = getCard(indices[i]);
      }
      for(int i = 0; i < 3; ++i)
      {
        discardPile.add(cardsToDiscard[i]);
        cards.remove(cardsToDiscard[i]);
      }
      return true;
    }
    else
    {
      return false;
    }
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
