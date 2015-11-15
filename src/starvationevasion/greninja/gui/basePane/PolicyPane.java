package starvationevasion.greninja.gui.basePane;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import starvationevasion.greninja.gui.GuiBase;
import starvationevasion.greninja.gui.componentPane.TimerPane;

/**
 * Base pane for Policy Drafting Phase.
 * TODO Change to stack pane.  Other gui elements will be layered on top.
 * @author Justin Thomas
 */
public class PolicyPane extends VBox
{

  private GuiBase base;
  private TimerPane timer;

  public PolicyPane(GuiBase base)
  {
    this.base = base;
  }

  /**
   * Instantiate and add components.
   */
  public void initPane()
  {
    timer = new TimerPane("5:00");
    timer.initPhaseTimer();
    Button button = new Button();
    button.setText("Next State");
    button.setOnAction(new ButtonControl(this));
    getChildren().add(new Label("Policy Pane, draft policies"));

    //TODO goes into timer pane
    getChildren().add(timer);

    getChildren().add(button);
  }

  /**
   * Swap to voting pane
   * TODO will not need
   */
  public void swapPane()
  {
    base.swapToVotingPane();
  }

  /**
   * Change timer color to new color.
   * @param minuteMark        color selected hinges on minutes left.
   *                          at two minutes set color to orange.
   *                          at one minute set to red.
   */
  public void updateColor(int minuteMark)
  {

  }

  /**
   * Get the timer pane.
   * @return        reference to timerPane.
   */
  public TimerPane getTimerPane()
  {
    return timer;
  }

  /**
   * Button listener.
   */
  private class ButtonControl implements EventHandler<ActionEvent>
  {
    private PolicyPane parentPane;

    public ButtonControl(PolicyPane parentPane)
    {
      this.parentPane = parentPane;
    }

    @Override
    public void handle (ActionEvent e)
    {
      parentPane.swapPane();
    }
  }
}
