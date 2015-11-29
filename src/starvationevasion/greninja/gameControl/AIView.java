package starvationevasion.greninja.gameControl;

import starvationevasion.common.EnumRegion;
import starvationevasion.common.messages.AvailableRegions;
import starvationevasion.greninja.clientCommon.EnumPhase;
import starvationevasion.greninja.gui.componentPane.TimerPane;
import starvationevasion.greninja.model.AIPlayer;
import starvationevasion.greninja.model.PlayerInterface;
import starvationevasion.greninja.model.State;

/**
 * Handles AI coms with game controller.  This is parallel to GuiBase, it is kind
 * of a GUI for robots.
 */
public class AIView implements ControlListener
{

  private GameController control;
  private AvailableRegions availableRegions;
  private AIDecisions decisions;
  private PlayerInterface player;

  private boolean DEBUG = true;

  public AIView(GameController control, PlayerInterface player)
  {
    this.control = control;
    this.player = player;
  }

  /**
   * set the decision making object.
   * @param decisions
   */
  public void setDecisionObject(AIDecisions decisions)
  {
    this.decisions = decisions;
  }
  /**
   * Get timerpane for ai returns null as of now.  Will be cleaned out.
   * @param phase
   * @return
   */
  @Override
  public TimerPane getTimerPane(EnumPhase phase)
  {
    return null;
  }

  /**
   * Inform view of player region, not entirely necessary.
   * @param state
   * @param pRegion
   */
  @Override
  public void initPlayerRegionInfo(State state, EnumRegion pRegion)
  {
    //Do Stuff
  }

  /**
   * Inform ai that staging phase has started.  Does not need to do anything,
   * server will inform ai of region assignment.
   */
  @Override
  public void swapToStagingPane()
  {
    //
  }

  /**
   * Inform AI that it is policy drafting phase.
   */
  @Override
  public void swapToPolicyPane()
  {
    if (DEBUG) System.out.println("I'm a robot and I'm drafting policies!");//
    decisions.analyzeCards(player.getPlayerHand());

  }

  /**
   * Inform AI that it is voting phase.
   */
  @Override
  public void swapToVotingPane()
  {
    if (DEBUG) System.out.println("I'm a robot and I'm voting on policies!");
    decisions.analyzeCards(player.getPlayerHand());
  }

  /**
   * Update availableRegions message.
   * @param availableRegions        AvailableRegions method.
   */
  @Override
  public void updateAvailableRegions(AvailableRegions availableRegions, PlayerInterface playerInterface)
  {
    this.availableRegions = availableRegions;
  }
}
