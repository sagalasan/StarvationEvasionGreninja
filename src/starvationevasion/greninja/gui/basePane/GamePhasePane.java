package starvationevasion.greninja.gui.basePane;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import starvationevasion.greninja.clientCommon.ClientConstant;
import starvationevasion.greninja.gui.GuiBase;
import starvationevasion.greninja.gui.GuiTimerSubscriber;
import starvationevasion.greninja.gui.componentPane.TimerPane;

/**
 * Parent pane for policy pane and voting pane.  Contains common methods.
 * TODO change to stack pane, other components will be layered on top.
 */
public class GamePhasePane extends VBox implements GuiTimerSubscriber
{
  private TimerPane timer;
  private GuiBase base;

  public GamePhasePane(GuiBase base)
  {
    this.base = base;
  }

  /**
   * Makes the timer and other components common to the two game phases.
   */
  public void initTimerPane()
  {
    timer = new TimerPane();
    timer.initPhaseTimer(ClientConstant.TIME_LIMIT_STRING);
  }

  /**
   * Implements GuiTimerSubscriber, update the timer text.  May need to be moved
   */
  public void timerTick()
  {
    timer.updateTimeLabel();
  }

  /**
   * Get the timer pane.
   * @return        reference to timerPane.
   */
  public TimerPane getTimerPane()
  {
    return timer;
  }

}
