package starvationevasion.greninja.gui.basePane;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import starvationevasion.greninja.gui.GuiBase;

/**
 * Base pane for Policy Drafting Phase.
 * TODO Change to stack pane.  Other gui elements will be layered on top.
 * @author Justin Thomas
 */
public class PolicyPane extends VBox
{

  private GuiBase base;
  private Label testTime;
  private String timeRemaining;

  public PolicyPane(GuiBase base)
  {
    this.base = base;
    timeRemaining = "5:00";
  }

  /**
   * Instantiate and add components.
   */
  public void initPane()
  {
    //TODO put into a timer pane.
    testTime = new Label("5:00");
    Timeline timerCountdown = new Timeline(new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent event)
      {
        testTime.setText(timeRemaining);
      }
    }));
    timerCountdown.setCycleCount(Timeline.INDEFINITE);
    timerCountdown.play();
    //TODO endtodo

    Button button = new Button();
    button.setText("Next State");
    button.setOnAction(new ButtonControl(this));
    getChildren().add(new Label("Policy Pane, draft policies"));

    //TODO goes into timer pane
    getChildren().add(testTime);

    getChildren().add(button);
  }

  public void swapPane()
  {
    base.swapToVotingPane();
  }

  /**
   * Update timer.
   * @param time        String representing time remaining "m:ss"
   */
  public void updateTimer(String time)
  {
    timeRemaining = time;
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
