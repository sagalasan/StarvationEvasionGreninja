package starvationevasion.greninja.gui.basePane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import starvationevasion.common.EnumRegion;
import starvationevasion.greninja.gui.ComponentImageView.WithdrawAndDiscardPile;
import starvationevasion.greninja.gui.GuiBase;
import starvationevasion.greninja.gui.MapHolder;
import starvationevasion.greninja.gui.componentPane.*;
import starvationevasion.greninja.model.State;

/**
 * Base pane for Policy Drafting Phase.
 * @author Justin Thomas
 */
public class PolicyPane extends GamePhasePane implements MapHolder
{

  private GuiBase base;
  private PlayerHandGui playerHandGui;
  private VBox leftPane;
  private VBox rightPane;
  private ClickableMap map;


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
    super.initTimerPane(5, 0);

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
    DraftedPolicyCardPane draftedCards = new DraftedPolicyCardPane();
    //todo will have to fix position in leftPane
    draftedCards.setAlignment(Pos.BOTTOM_CENTER);
    leftPane.getChildren().addAll(getTimerPane(), button, new Label("visualization"),
        new ImageView(new Image ("file:assets/greninjaAssets/VisSample.png")), new Label("Drafted Cards"),draftedCards);

    mainBorderPane.setLeft(leftPane);

    //todo make a right pane and populate with graphs.  Try and implement the regional stats info
    rightPane = new VBox();
    rightPane.getChildren().add(new Label("Regional Statistics"));
    rightPane.getChildren().add(new RegionalStatistics(State.CALIFORNIA, "Population"));
    //rightPane.getChildren().add(new RegionalStatistics(State.CALIFORNIA, "HDI"));
    rightPane.getChildren().add(new RegionalStatistics(State.CALIFORNIA));
    mainBorderPane.setRight(rightPane);

    //===============================================================
    //Makes an interactive map and assigns this policy pane as its
    //containing pane
    //===============================================================
    //InteractiveMapPane map = new InteractiveMapPane();
    //map.setContainingPane(this);
    //mainBorderPane.setCenter(map);

    /**
     * comment this map if you want to use the iteractive map pane map
     * and uncomment the top comments
     */

    //===============================================================
    //Makes an interactive map
    //===============================================================
    map = new ClickableMap("policy");
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
    playerAndToolbarVBox.getChildren().addAll(bottomPane, new Label("ToolBar"), toolbar);
    mainBorderPane.setBottom(playerAndToolbarVBox);

    getChildren().add(mainBorderPane);
  }

  /**
   * Swap to voting pane
   * TODO will not need
   */
  public void endPhase()
  {
    stopTimer();
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
    rightPane.getChildren().removeAll();
    createRightPane(region);
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

  private void createRightPane(EnumRegion region)
  {
    State state = null;
    switch(region)
    {
      case CALIFORNIA:
        state = State.CALIFORNIA;
        break;
      case HEARTLAND:
        state = State.HEARTLAND;
        break;
      case NORTHERN_PLAINS:
        state = State.NORTHERN_PLAINS;
        break;
      case SOUTHEAST:
        state = State.SOUTHEAST;
        break;
      case NORTHERN_CRESCENT:
        state = State.NORTHERN_CRESCENT;
        break;
      case SOUTHERN_PLAINS:
        state = State.SOUTHERN_PLAINS;
        break;
      case MOUNTAIN:
        state = State.SOUTHEAST;
    }
    rightPane.getChildren().add(new Label("Regional Statistics"));
    rightPane.getChildren().add(new RegionalStatistics(state, "Population"));
    //rightPane.getChildren().add(new RegionalStatistics(State.CALIFORNIA, "HDI"));
    rightPane.getChildren().add(new RegionalStatistics(state));
  }
}
