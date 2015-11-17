package starvationevasion.greninja.model;

import starvationevasion.common.EnumPolicy;
import starvationevasion.common.EnumRegion;

import java.util.List;

/**
 * Used to describe a player (either human or AI).
 */
public interface PlayerInterface
{
  /**
   * Set the player's region.
   * @param region
   */
  void setPlayerRegion(EnumRegion region);

  /**
   * Get the region that the player was assigned.
   * @return
   */
  EnumRegion getPlayerRegion();


  /**
   * Set the current player's hand
   * @param hand
   */
  void setPlayerHand(List<EnumPolicy> hand);

  /**
   * Get the player's current hand
   * @return
   */
  List<EnumPolicy> getPlayerHand();

  /**
   * Add a card to the player's hand.
   * @param card    EnumPolicy to add to hand.
   * @return        true if addCard succeeded.
   */
  boolean addCard(EnumPolicy card);

  /**
   * Removes a card from the player's hand and puts it in the discard pile.
   * @param index   index of card selected.
   * @return        true if successful, false if no card to discard.
   */
  boolean discardCard(int index);

  /**
   * Discard three cards.  Get the cards at the specified index then
   * remove from hand and put into discard pile.
   * @param indices       3 indexes
   */
  boolean discardThree(int[] indices);

  /**
   * Get a specific card from the player's hand
   * @param index
   * @return
   */
  EnumPolicy getCard(int index);

  /**
   * Returns the card at the head of the given card list.
   * @param cardPile
   * @return
   */
  EnumPolicy firstCard(List<EnumPolicy> cardPile);

  /**
   * The card in the drafted policies from all 7 regions that the player decided to vote for.
   * @param index
   * @return
   */
  EnumPolicy vote(int index);

  /**
   * The player-selected card during the drafting phase.
   * @param index
   */
  void draft(int index);
}
