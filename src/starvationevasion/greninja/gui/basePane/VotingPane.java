package starvationevasion.greninja.gui.basePane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import starvationevasion.greninja.gui.GuiBase;
import starvationevasion.greninja.gui.componentPane.TimerPane;

/**
 * Base pane for the gui during the policy voting phase.
 * TODO change to stack pane, other components will be layered on top.
 * @author Justin Thomas (jthomas105@unm.edu)
 */
public class VotingPane extends VBox
{

  private GuiBase base;
  private TimerPane timer;

  public VotingPane(GuiBase base)
  {
    this.base = base;
  }

  /**
   * Instantiate and add pane components
   */
  public void initPane()
  {
    timer = new TimerPane();
    timer.initPhaseTimer("5:00");
    Button button = new Button();
    button.setText("Next State");
    button.setOnAction(new ButtonControl(this));
    getChildren().add(new Label("VotingPane, choose policies."));
    getChildren().add(timer);
    getChildren().add(button);
  }

  public void endPhase()
  {
    base.endPolicyVotingPhase();
  }

  public TimerPane getTimerPane()
  {
    return timer;
  }

  private class ButtonControl implements EventHandler<ActionEvent>
  {
    private VotingPane parentPane;

    public ButtonControl(VotingPane parentPane)
    {
      this.parentPane = parentPane;
    }

    @Override
    public void handle (ActionEvent e)
    {
      parentPane.endPhase();
    }
  }
}
