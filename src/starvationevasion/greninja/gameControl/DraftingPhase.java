package starvationevasion.greninja.gameControl;

import starvationevasion.common.EnumPolicy;
import starvationevasion.greninja.gui.componentPane.TimerPane;
import starvationevasion.greninja.util.PhaseTimer;

/**
 * Controls the policy drafting phase.
 */
public class DraftingPhase extends GamePhase
{
  private static final int DRAFTING_TIME_LIMIT = 5;

  //player may make to actions per turn, either drafting a policy or discarding.
  private int actionsTaken = 0;
  private boolean hasDiscarded;
  private GameController control;
  private PhaseTimer phaseTimer;

  /**
   * Instantiate phase timer, assign reference to control,
   * @param control
   */
  public DraftingPhase(GameController control, TimerPane visibleTimer)
  {
    this.phaseTimer = new PhaseTimer(DRAFTING_TIME_LIMIT, this, visibleTimer);
    phaseTimer.start();
    this.control = control;
    hasDiscarded = false;
  }

  /**
   * Discard 3 and draw 3 action.  Checks if turn is still on, and if card
   * selections are valid
   * @param cardsSelected     Array of indices of cards selected.
   * @return                  True if discard was successful.
   */
  public boolean discardThree(int[] cardsSelected)
  {
    boolean discardPerformed = true;
    if(actionsTaken < 2 && phaseTimer.phaseNotOver())
    {
      //check if card selections are valid for discard.
      for(int i = 0; i < 3; ++i)
      {
        if(selectCardFromHand(cardsSelected[i]) == null)
        {
          discardPerformed = false;
        }
      }
      if(discardPerformed)
      {
        //if valid
        //remove cards from hand and add to discard.
        //discardPerformed = true
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
    if(!hasDiscarded && phaseTimer.phaseNotOver())
    {
      //check if card selection is valid discard
      if(selectCardFromHand(cardIndex) != null)
      {
        //if valid
        //remove card from hand and put in discard
        hasDiscarded = true;
        discardPerformed = true;
        System.out.println("Card discarded.");
      }
      else
      {
        //inform control -> gui that discard failed.
        System.out.println("Discard failed.");
      }
    }
    return discardPerformed;//
  }

  /**
   * Play a policy card, should inform game that card has been played.
   * @param cardIndex       index of card to get.
   * @return                return success or failure.
   */
  public boolean draftPolicy(int cardIndex)
  {
    boolean policyDrafted = false;
    if(actionsTaken < 2 && phaseTimer.phaseNotOver())
    {
      //check if valid card is played
      EnumPolicy cardSelected = selectCardFromHand(cardIndex);
      if(cardSelected != null)
      {
        //if valid
        //remove card from hand and put in discard.
        //notify control -> server of policy drafted.
        //notify control -> gui of updates to display.
        policyDrafted = true;
      }
    }
    return policyDrafted;
  }

  /**
   * Informs control that phase is over.
   */
  public void phaseOver()
  {
    control.endPolicyDraftingPhase();
  }

  /**
   * Check if there is a card at hand index.
   * @param index       card selected from gui.
   * @return            Policy card returned, null if blank.
   */
  private EnumPolicy selectCardFromHand(int index)
  {
    EnumPolicy cardSelected = null;
    //if hand at index != null
    //cardSelected = hand at index
    return cardSelected;
  }
}
