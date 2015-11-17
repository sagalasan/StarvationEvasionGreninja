package starvationevasion.greninja.gui.basePane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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
    timer = new TimerPane();
    timer.initPhaseTimer("5:00");
    Button button = new Button();
    button.setText("Next State");
    button.setOnAction(new ButtonControl(this));
    getChildren().add(new Label("Policy Pane, draft policies"));

    getChildren().add(timer);

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
      parentPane.endPhase();
    }
  }
}
