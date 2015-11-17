package starvationevasion.greninja.gameControl;

import starvationevasion.common.EnumPolicy;
import starvationevasion.greninja.clientCommon.ClientConstant;
import starvationevasion.greninja.gui.componentPane.TimerPane;
import starvationevasion.greninja.util.PhaseTimer;

/**
 * Controls the policy drafting phase.
 */
public class DraftingPhase extends GamePhase
{

  //player may make two actions per turn, either drafting a policy or discarding.
  private int actionsTaken = 0;
  private boolean hasDiscarded;
  private GameController control;
  private PhaseTimer phaseTimer;
  private Player player;

  /**
   * Instantiate phase timer, assign reference to control,
   * @param control
   */
  public DraftingPhase(GameController control, TimerPane visibleTimer,
                       Player player)
  {
    this.player = player;
    this.phaseTimer = new PhaseTimer(ClientConstant.DRAFTING_TIME_LIMIT,
                                      this, visibleTimer);
    phaseTimer.start();
    this.control = control;
    hasDiscarded = false;
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
    if(actionsTaken < 2 && phaseTimer.phaseNotOver())
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
    if(!hasDiscarded && phaseTimer.phaseNotOver())
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
  public EnumPolicy draftPolicy(int cardIndex)
  {
    EnumPolicy cardSelected = null;
    //if can take action.
    if(actionsTaken < 2 && phaseTimer.phaseNotOver())
    {
      //check if valid card is played
      cardSelected = selectCardFromHand(cardIndex);
      if(cardSelected != null)
      {
        //if valid
        actionTaken();
        //remove card from hand and put in discard.
        player.discardCard(cardIndex);
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
  private EnumPolicy selectCardFromHand(int index)
  {
    EnumPolicy cardSelected = player.getCard(index);
    return cardSelected;
  }
}
