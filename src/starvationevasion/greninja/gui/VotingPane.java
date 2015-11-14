package starvationevasion.greninja.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Base pane for the gui during the policy voting phase.
 * TODO change to stack pane, other components will be layered on top.
 * @author Justin Thomas (jthomas105@unm.edu)
 */
public class VotingPane extends VBox
{

  GuiBase base;

  public VotingPane(GuiBase base)
  {
    this.base = base;
  }

  /**
   * Instantiate and add pane components
   */
  public void initPane()
  {
    Button button = new Button();
    button.setText("Next State");
    button.setOnAction(new ButtonControl(this));
    getChildren().add(new Label("VotingPane, choose policies."));
    getChildren().add(button);
  }

  public void swapPane()
  {
    base.swapToPolicyPane();
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
      parentPane.swapPane();
    }
  }
}
