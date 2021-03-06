package starvationevasion.greninja.gameControl;

import starvationevasion.common.PolicyCard;
import starvationevasion.greninja.clientCommon.ClientConstant;
import starvationevasion.greninja.gui.componentPane.TimerPane;
import starvationevasion.greninja.model.HumanPlayer;
import starvationevasion.greninja.model.PlayerInterface;

/**
 * Controls the policy drafting phase.
 * Unused, game shaped up alot different than I thought.
 */
public class DraftingPhase extends GamePhase
{

  //player may make two actions per turn, either drafting a policy or discarding.
  private int actionsTaken = 0;
  private boolean hasPlayedVoteCard;
  private boolean hasDiscarded;
  private GameController control;
  //private PhaseTimer phaseTimer;
  private PlayerInterface player;

  /**
   * Instantiate phase timer, assign reference to control,
   * @param control
   */
  public DraftingPhase(GameController control, PlayerInterface player)
  {
    this.player = player;
    this.control = control;
    hasDiscarded = false;
    hasPlayedVoteCard = false;
  }

  /**
   * Discard 3 and draw 3 action.  Checks if turn is still on, and if player
   * is allowed to perform this action.
   * @param cardsSelected     Array of indices of cards selected.
   * @return                  True if discard was successful.
   */
  public boolean discardThree(int[] cardsSelected)
  {
    boolean discardPerformed = true;
    if(actionsTaken < 2)
    {
      discardPerformed = player.discardThree(cardsSelected);
      if(discardPerformed)
      {
        actionTaken();
      }
    }
    return discardPerformed;
  }

  /**
   * Discard a card.  Can be performed once per phase.
   * @param cardIndex       index of card to be discarded.
   * @return                true if discard was performed.
   */
  public boolean discardOne(int cardIndex)
  {
    boolean discardPerformed = false;
    if(!hasDiscarded)
    {
      discardPerformed = player.discardCard(cardIndex);
    }
    return discardPerformed;//
  }

  /**
   * Play a policy card, should inform game that card has been played.
   * @param cardIndex       index of card to get.
   * @return                return card to be drafted.
   */
  public PolicyCard draftPolicy(int cardIndex)
  {
    PolicyCard cardSelected = null;
    //if can take action.
    if(actionsTaken < 2)
    {
      //check if valid card is played
      cardSelected = selectCardFromHand(cardIndex);
      if(cardSelected != null)
      {
        if(validateCard(cardSelected))
        {
          //if valid
          actionTaken();
          //remove card from hand and put in discard.
          player.discardCard(cardIndex);
        }
      }
    }
    return cardSelected;
  }

  /**
   * Informs control that phase is over.
   */
  public void phaseOver()
  {
    control.endPolicyDraftingPhase();
  }

  private void actionTaken()
  {
    //increment actions taken.
    actionsTaken++;
    //inform server if two actions have been taken.
    if(actionsTaken >= 2)
    {
      System.out.println("All actions taken.");
    }
  }

  /**
   * Check if there is a card at hand index.
   * @param index       card selected from gui.
   * @return            Policy card returned, null if blank.
   */
  private PolicyCard selectCardFromHand(int index)
  {
    PolicyCard cardSelected = player.getCard(index);
    return cardSelected;
  }

  /**
   * Check to see if the selected card is a vote card.  Check to see if the card
   * can be played.  If it can be played, set hasPlayedVoteCard to true.
   * @param cardSelected        Card player selected to play.
   * @return          true if player can play the selected card.
   */
  private boolean validateCard(PolicyCard cardSelected)
  {
    boolean isValid = true;
    if(cardSelected.votesRequired() > 0)
    {
      if (hasPlayedVoteCard)
      {
        isValid = false;
      }
      else
      {
        hasPlayedVoteCard = true;
      }
    }
    return isValid;
  }
}
