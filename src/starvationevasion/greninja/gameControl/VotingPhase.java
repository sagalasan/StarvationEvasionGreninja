package starvationevasion.greninja.gameControl;

import starvationevasion.greninja.clientCommon.ClientConstant;

/**
 * Class controls the voting phase of the game.
 */
public class VotingPhase extends GamePhase
{
  private GameController control;

  /**
   * Instantiate voting phase and assign members.
   * @param control             game controller.
   */
  public VotingPhase(GameController control)
  {
    this.control = control;
  }

  /**
   * End of phase cleanup.
   */
  public void phaseOver()
  {
    control.endPolicyVotingPhase();
  }
}
