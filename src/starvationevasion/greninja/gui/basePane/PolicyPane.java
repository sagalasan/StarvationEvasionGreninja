package starvationevasion.greninja.gui.basePane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import starvationevasion.greninja.gui.GuiBase;
import starvationevasion.greninja.gui.componentPane.PlayerHandGui;

import java.io.File;
import java.io.IOException;

/**
 * Base pane for Policy Drafting Phase.
 * @author Justin Thomas
 */
public class PolicyPane extends GamePhasePane
{

  private GuiBase base;
  private PlayerHandGui playerHandGui;
  public PolicyPane(GuiBase base)
  {
    super(base);
    this.base = base;

  }

  /**
   * Instantiate and add components.
   */
  VBox vBoxPane;
  public void initPane()
  {
    super.initTimerPane();

    Button button = new Button();
    button.setText("Next State");
    button.setOnAction(new ButtonControl(this));
    BorderPane mainBorderPane = new BorderPane();

    vBoxPane = new VBox();
    vBoxPane.getChildren().addAll(getTimerPane(), button);

    mainBorderPane.setTop(new Label("Policy Pane, draft policies"));
    //make a player card hand class
    mainBorderPane.setCenter(vBoxPane);

    //comment this out if you want to get rid of the player hand gui part.
    playerHandGui = new PlayerHandGui(base);
    mainBorderPane.setBottom(playerHandGui);
    getChildren().add(mainBorderPane);

  }

  /**
   * Swap to voting pane
   * TODO will not need
   */
  public void endPhase()
  {
    resetTimer(vBoxPane);
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
