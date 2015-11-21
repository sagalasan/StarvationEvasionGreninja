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

  public void initPane()
  {
    super.initTimerPane();
    Button button = new Button();
    button.setText("Next State");
    button.setOnAction(new ButtonControl(this));
    getChildren().add(new Label("VotingPane, choose policies."));
    getChildren().add(getTimerPane());
    getChildren().add(button);
  }
  //removes the old timer, makes a new one, and adds it to the children
  private void resetTimer()
  {
    //getChildren().remove(getTimerPane());

    int timerIndex = getChildren().indexOf(getTimerPane());
    super.initTimerPane();
    getChildren().set(timerIndex, getTimerPane());


    //getChildren().add(getTimerPane());

  }

  /**
   * TODO will not need.
   */
  public void endPhase()
  {
    resetTimer();
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
