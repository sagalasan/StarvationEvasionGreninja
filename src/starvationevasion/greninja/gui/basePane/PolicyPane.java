package starvationevasion.greninja.gui.basePane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import starvationevasion.greninja.gui.GuiBase;

/**
 * Base pane for Policy Drafting Phase.
 * @author Justin Thomas
 */
public class PolicyPane extends GamePhasePane
{

  private GuiBase base;

  public PolicyPane(GuiBase base)
  {
    super(base);
    this.base = base;
  }

  /**
   * Instantiate and add components.
   */
  @Override
  public void initPane()
  {
    super.initPane();
    Button button = new Button();
    button.setText("Next State");
    button.setOnAction(new ButtonControl(this));
    getChildren().add(new Label("Policy Pane, draft policies"));

    getChildren().add(getTimerPane());

    getChildren().add(button);
  }

  /**
   * Swap to voting pane
   * TODO will not need
   */
  public void endPhase()
  {
    base.endPolicyDraftingPhase();
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
      parentPane.endPhase();
    }
  }

}
