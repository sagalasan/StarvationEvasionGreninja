package starvationevasion.greninja.gui.basePane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import starvationevasion.greninja.gui.GuiBase;
import starvationevasion.greninja.gui.componentPane.TimerPane;

/**
 * Parent pane for policy pane and voting pane.  Contains common methods.
 */
public class GamePhasePane extends VBox
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
  public void initPane()
  {
    timer = new TimerPane();
    timer.initPhaseTimer("5:00");
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
