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
public class VotingPane extends GamePhasePane
{

  private GuiBase base;

  public VotingPane(GuiBase base)
  {
    super(base);
    this.base = base;
  }

  /**
   * Instantiate and add pane components
   */
  @Override
  public void initPane()
  {
    super.initPane();
    Button button = new Button();
    button.setText("Next State");
    button.setOnAction(new ButtonControl(this));
    getChildren().add(new Label("VotingPane, choose policies."));
    getChildren().add(getTimerPane());
    getChildren().add(button);
  }

  /**
   * TODO will not need.
   */
  public void endPhase()
  {
    base.endPolicyVotingPhase();
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
