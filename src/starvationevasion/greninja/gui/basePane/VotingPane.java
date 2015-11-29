package starvationevasion.greninja.gui.basePane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import starvationevasion.greninja.clientCommon.ClientConstant;
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
  private VBox vBoxPane;
  public void initPane()
  {
    super.initTimerPane(2, 0);
    Button button = new Button();
    button.setText("Next State");
    button.setOnAction(new ButtonControl(this));
    BorderPane mainBorderPane = new BorderPane();

    mainBorderPane.setTop(new Label("VotingPane, choose policies."));
    vBoxPane = new VBox();
    vBoxPane.getChildren().add(getTimerPane());
    vBoxPane.getChildren().add(button);
    mainBorderPane.setCenter(vBoxPane);
    getChildren().add(mainBorderPane);
    //getChildren().add(new Label("VotingPane, choose policies."));
    //getChildren().add(getTimerPane());
    //getChildren().add(button);

  }

  /**
   * TODO will not need.
   */
  public void endPhase()
  {
    stopTimer();
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
