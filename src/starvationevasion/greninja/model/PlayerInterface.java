package starvationevasion.greninja.model;

import starvationevasion.common.EnumRegion;
import starvationevasion.common.PolicyCard;
import starvationevasion.sim.CardDeck;

import java.util.List;

/**
 * Interface used to describe a generic player (either human or AI).
 * @author Erin Sosebee
 */
public interface PlayerInterface
{

  /**
   * Set the player's name from login
   * @param name the name to be used for the player.
   */
  void setPlayerName(String name);

  /**
   * Gets the player's login name.
   * @return the player's name.
   */
  String getPlayerName();

  /**
   * Set the player's region.
   * @param region the region to be set as the player's region.
   */
  void setPlayerRegion(EnumRegion region);

  /**
   * Get the region that the player was assigned.
   * @return the player's region.
   */
  EnumRegion getPlayerRegion();

  /**
   * Sets the player's deck of cards to draw from.
   * @param deck the cards to be used as the player's deck.
   */
  void setPlayerDeck(CardDeck deck);

  /**
   * Gets the player's current deck of cards.
   * @return the player's deck of cards.
   */
  CardDeck getPlayerDeck();

  /**
   * Set the current player's hand
   * @param hand
   */
  void setPlayerHand(List<PolicyCard> hand);

  /**
   * Get the player's current hand
   * @return
   */
  List<PolicyCard> getPlayerHand();


  /**
   * Add a card to the player's hand.
   * @param card    EnumPolicy to add to hand.
   * @return        true if addCard succeeded.
   */
  boolean addCard(PolicyCard card);

  /**
   * Removes the card at the specified index from the player's hand.
   * @param index   index of card to remove.
   * @return        true if removeCard succeeded.
   */
  boolean removeCard(int index);

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
  PolicyCard getCard(int index);

  /**
   * Returns the card at the head of the given card list.
   * @param cardPile
   * @return
   */
  PolicyCard firstCard(List<PolicyCard> cardPile);

  /**
   * The card in the drafted policies from all 7 regions that the player decided to vote for.
   * @param index
   * @return
   */
  PolicyCard vote(int index);

  /**
   * The player-selected card during the drafting phase.
   * @param index
   */
  PolicyCard draft(int index);
}
