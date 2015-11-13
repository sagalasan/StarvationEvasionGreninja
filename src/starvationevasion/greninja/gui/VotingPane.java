package starvationevasion.greninja.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * @author Justin Thomas (jthomas105@unm.edu)
 */
public class VotingPane extends VBox
{

  GuiBase base;

  public VotingPane(GuiBase base)
  {
    this.base = base;
    Button button = new Button();
    button.setText("Next State");
    button.setOnAction(new ButtonControl(this.base, this));
    getChildren().add(new Label("VotingPane, choose policies."));
    getChildren().add(button);
  }

  public void swapPane()
  {
    base.swapToPolicyPane();
  }

  private class ButtonControl implements EventHandler<ActionEvent>
  {
    private GuiBase base;
    private VotingPane parentPane;

    public ButtonControl(GuiBase gui, VotingPane parentPane)
    {
      this.base = gui;
      this.parentPane = parentPane;
    }

    @Override
    public void handle (ActionEvent e)
    {
      parentPane.swapPane();
    }
  }
}
