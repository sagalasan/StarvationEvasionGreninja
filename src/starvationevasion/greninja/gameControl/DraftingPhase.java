package starvationevasion.greninja.gameControl;

import starvationevasion.greninja.util.PhaseTimer;

/**
 * @author Justin Thomas(jthomas105@unm.edu)
 * Controls the policy drafting phase.
 */
public class DraftingPhase
{
  //player may make to actions per turn, either drafting a policy or discarding.
  private int actionsTaken = 0;
  private boolean hasDiscarded;
  private GameController control;
  private PhaseTimer phaseTimer;

  /**
   * Instantiate phase timer, assign reference to control,
   * @param control
   */
  public DraftingPhase(GameController control, PhaseTimer phaseTimer)
  {
    this.phaseTimer = phaseTimer;
    this.control = control;
    hasDiscarded = false;
    //this.player = player
  }

  /**
   * Discard 3 and draw 3 action.  Checks if turn is still on, and if card
   * selections are valid
   * @param cardIndex1        Hand index of cards to be discarded
   * @param cardIndex2                      "
   * @param cardIndex3                      "
   * @return                  true if discard was successful
   */
  public boolean discardThree(int cardIndex1, int cardIndex2, int cardIndex3)
  {
    boolean discardPerformed = false;
    if(actionsTaken < 2 && phaseTimer.phaseNotOver())
    {
      //check if card selections are valid for discard.
      //if valid
        //remove cards from hand and add to discard.
        //discardPerformed = true
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
      //if valid
        //remove card from hand and put in discard
        //discard performed = true
    }
    return discardPerformed;//
  }

  /**
   * Play a policy card, should inform game that card has been played.
   * @param cardIndex
   * @return
   */
  public boolean draftPolicy(int cardIndex)
  {
    boolean policyDrafted = false;
    if(actionsTaken < 2 && phaseTimer.phaseNotOver())
    {
      //check if valid card is played
      //if valid
        //remove card from hand and put in discard
        //policy drafted = true
    }
    return policyDrafted;
  }
}
