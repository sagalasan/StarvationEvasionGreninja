package starvationevasion.greninja.gui.basePane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import starvationevasion.common.EnumRegion;
import starvationevasion.greninja.gui.GuiBase;
import starvationevasion.greninja.gui.MapHolder;
import starvationevasion.greninja.gui.componentPane.InteractiveMapPane;
import starvationevasion.greninja.gui.componentPane.PlayerHandGui;
import starvationevasion.greninja.gui.componentPane.ToolBarWithIcons;
import starvationevasion.greninja.gui.componentPane.WithdrawAndDiscardPile;

import java.io.File;
import java.io.IOException;

/**
 * Base pane for Policy Drafting Phase.
 * @author Justin Thomas
 */
public class PolicyPane extends GamePhasePane implements MapHolder
{

  private GuiBase base;
  private PlayerHandGui playerHandGui;
  private VBox leftPane;

  public PolicyPane(GuiBase base)
  {
    super(base);
    this.base = base;

  }

  /**
   * Instantiate and add components.
   */

  public void initPane()
  {
    super.initTimerPane();

    Button button = new Button();
    button.setText("Next State");
    button.setAlignment(Pos.CENTER_LEFT);
    button.setOnAction(new ButtonControl(this));
    BorderPane mainBorderPane = new BorderPane();

    //sets the title
    mainBorderPane.setTop(new Label("Policy Pane, draft policies"));

    //===============================================================
    //leftPane holds the timer, action buttons, and drafted policys
    //===============================================================
    leftPane = new VBox();
    leftPane.getChildren().addAll(getTimerPane(), button);
    mainBorderPane.setLeft(leftPane);

    //===============================================================
    //Makes an interactive map and assigns this policy pane as its
    //containing pane
    //===============================================================
    InteractiveMapPane map = new InteractiveMapPane();
    map.setContainingPane(this);
    mainBorderPane.setCenter(map);

    //===============================================================
    //creates the toolbar, playerhand, and withdraw/discard piles
    //===============================================================
    VBox playerAndToolbarVBox = new VBox();
    playerHandGui = new PlayerHandGui(base);
    playerHandGui.setAlignment(Pos.CENTER);
    WithdrawAndDiscardPile withdrawAndDiscardPile = new WithdrawAndDiscardPile();
    withdrawAndDiscardPile.setAlignment(Pos.BOTTOM_LEFT);
    StackPane bottomPane = new StackPane();
    ToolBarWithIcons toolbar = new ToolBarWithIcons();
    bottomPane.getChildren().addAll(withdrawAndDiscardPile, playerHandGui);
    playerAndToolbarVBox.getChildren().addAll(bottomPane, toolbar);
    mainBorderPane.setBottom(playerAndToolbarVBox);



    getChildren().add(mainBorderPane);
  }

  /**
   * Swap to voting pane
   * TODO will not need
   */
  public void endPhase()
  {
    resetTimer(leftPane);
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



  /**
   * When interactive map is clicked, set region selected to that region.
   * @param region        region on interactive map that was clicked.
   */
  public void regionClicked(EnumRegion region)
  {
    System.out.println("selected Region is "+region.toString());
  }

  /**
   * When mouse enters a region on the interactive map, update the region selected
   * string.
   * @param region        EnumRegion entered.
   */
  public void regionEntered(EnumRegion region)
  {

    //selectedRegion = region.toString();
  }

  /**
   * When mouse exits region, clear region selected string.
   * @param region        region exited.  Not used.
   */
  public void regionExited(EnumRegion region)
  {
   // selectedRegion = "None.";
  }

}
