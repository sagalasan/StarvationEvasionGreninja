package starvationevasion.greninja.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Base pane for Policy Drafting Phase.
 * TODO Change to stack pane.  Other gui elements will be layered on top.
 * @author Justin Thomas
 */
public class PolicyPane extends VBox
{

  GuiBase base;
  public PolicyPane(GuiBase base)
  {
    this.base = base;
    Button button = new Button();
    button.setText("Next State");
    button.setOnAction(new ButtonControl(this.base, this));
    getChildren().add(new Label("Policy Pane, draft policies"));
    getChildren().add(button);
  }

  public void swapPane()
  {
    base.swapToVotingPane();
  }

  private class ButtonControl implements EventHandler<ActionEvent>
  {
    private GuiBase base;
    private PolicyPane parentPane;

    public ButtonControl(GuiBase gui, PolicyPane parentPane)
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
