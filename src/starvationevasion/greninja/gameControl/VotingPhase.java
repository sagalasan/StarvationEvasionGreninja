package starvationevasion.greninja.gameControl;

import starvationevasion.greninja.clientCommon.ClientConstant;
import starvationevasion.greninja.gui.componentPane.TimerPane;
import starvationevasion.greninja.util.PhaseTimer;

/**
 * class that controls the voting phase of the game.
 */
public class VotingPhase extends GamePhase
{
  private PhaseTimer phaseTimer;
  private GameController control;

  /**
   * Instantiate voting phase and assign members.
   * @param control             game controller.
   * @param visibleTimer        reference to gui timer.
   */
  public VotingPhase(GameController control, TimerPane visibleTimer)
  {
    this.phaseTimer = new PhaseTimer(ClientConstant.VOTING_TIME_LIMIT, this,
                                    visibleTimer);
    phaseTimer.start();
    this.control = control;
  }

  public void phaseOver()
  {
    control.endPolicyVotingPhase();
  }
}
