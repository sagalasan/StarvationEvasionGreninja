package starvationevasion.greninja.gui.basePane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import starvationevasion.common.EnumRegion;
import starvationevasion.greninja.clientCommon.ClientConstant;
import starvationevasion.greninja.gui.GuiBase;
import starvationevasion.greninja.gui.MapHolder;
import starvationevasion.greninja.gui.componentPane.*;

/**
 * Created by Jalen on 11/27/2015.
 */

/**
 * makes a voting pane.
 */
public class TestVotingPane extends GamePhasePane implements MapHolder
{
  private GuiBase base;
  private PlayerHandGui playerHandGui;
  BorderPane mainPane = new BorderPane();

  public TestVotingPane(GuiBase base)
  {
    super(base);
    this.base = base;
  }

  /**
   * Instantiates and adds components to pane.
   */
  public void initPane()
  {
    super.initTimerPane(ClientConstant.VOTING_TIME_PANE);
    mainPane.setId("mainPaneVoting");
    //playerHandGui = new PlayerHandGui(base);
    //playerHandGui.setAlignment(Pos.CENTER);
    buildTop();
    buildLeft();
    buildRight();
    buildBottom();
    buildCenter();
    getChildren().add(mainPane);
  }

  /*
   * Contains the phase title and the next phase button.
   */
  private void buildTop()
  {
    BorderPane topPane = new BorderPane();
    topPane.setId("topLayout");
//    topPane.setPadding(new Insets(10, 15, 10, 15));

    // Draw divider
    Label divider = new Label();
    divider.setId("divider");
    divider.setMaxHeight(1);
    divider.setMinHeight(1);
    divider.setMinWidth(Screen.getPrimary().getBounds().getWidth());

//    Label timer = new Label("Timer"); // TODO: placeholder
    TimerPane timer = getTimerPane();

    VBox titleBox = new VBox();
    titleBox.setPadding(new Insets(15, 0, 0, 0));
    titleBox.setAlignment(Pos.BOTTOM_CENTER);
    Label title = new Label("Policy Phase: Draft Policies");
    title.setId("title");
    titleBox.getChildren().add(title);

    VBox buttonBox = new VBox();
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setPadding(new Insets(10, 10, 10, 10));
    Button nextPhaseButton = new Button("Next Phase");
    nextPhaseButton.setOnAction(new ButtonControl(this));
    buttonBox.getChildren().add(nextPhaseButton);

    topPane.setBottom(divider);
    topPane.setLeft(timer);
    topPane.setRight(buttonBox);
    topPane.setCenter(title);
    mainPane.setTop(topPane);
  }

  /*
   * Left pane holds the timer, action buttons, and drafted policies.
   */
  private void buildLeft()
  {
    //BorderPane leftPane = new BorderPane();
    //leftPane.setId("leftLayout");
    //DraftedPolicyCardPane draftedCards = new DraftedPolicyCardPane();
    //draftedCards.setAlignment(Pos.BOTTOM_CENTER);
    //draftedCards.setSpacing(5);

    //VBox visBox = new VBox(5);
    //visBox.setAlignment(Pos.CENTER);
    //ImageView visImg = new ImageView(new Image("file:assets/greninjaAssets/VisSample.png"));
    //visBox.getChildren().add(visImg);

    //VBox draftedCardsBox = new VBox(5);
    //draftedCardsBox.setAlignment(Pos.BOTTOM_CENTER);
    //draftedCardsBox.getChildren().addAll(new Label("Drafted Policies"), draftedCards);

    //leftPane.setCenter(visBox);
    //leftPane.setBottom(draftedCardsBox);
    //leftPane.setPrefWidth(300);
    //mainPane.setLeft(leftPane);
  }

  /*
   * Contains graphs
   */
  private void buildRight()
  {
    BorderPane rightPane = new BorderPane();
    rightPane.setId("rightLayout");
    rightPane.setPrefWidth(300);

    VBox statsBox = new VBox(10);
    statsBox.setAlignment(Pos.TOP_CENTER);
    statsBox.getChildren().add(new Label("Regional Statistics"));
    ChartCreator chartCreator = new ChartCreator();
    statsBox.getChildren().addAll(chartCreator.getChart("Population"),
        chartCreator.getChart("HDI"));

    rightPane.setCenter(statsBox);
    mainPane.setRight(rightPane);
  }

  /*
   * Adds a clickable US map to the center of the pane.
   */
  private void buildCenter()
  {
    VotingView map = new VotingView();
    map.setContainingPane(this);
    mainPane.setCenter(map);
    //todo build a policy voting mockup
   // ClickableMap map = new ClickableMap();
   // map.setContainingPane(this);
   // mainPane.setCenter(map);
  }

  private void buildBottom()
  {
    BorderPane bottomPane = new BorderPane();
    bottomPane.setId("bottomLayout");

    // Draw divider
    Label divider = new Label();
    divider.setId("divider");
    divider.setMaxHeight(1);
    divider.setMinHeight(1);
    divider.setMinWidth(Screen.getPrimary().getBounds().getWidth());

    //TestWithdrawAndDiscardPile drawDiscardPile = new TestWithdrawAndDiscardPile();
    //drawDiscardPile.setSpacing(5);
    //drawDiscardPile.setPadding(new Insets(10, 10, 10, 10));
    //drawDiscardPile.setAlignment(Pos.TOP_CENTER);

    //VBox cardBox = new VBox(5);
    //cardBox.setPadding(new Insets(10, 10, 10, 10));
    //cardBox.setAlignment(Pos.TOP_CENTER);
    //Label cardLabel = new Label("Your Cards");
//    playerHandGui.setAlignment(Pos.CENTER);
//    playerHandGui.setPadding(new Insets(10, 10, 10, 10));
    //cardBox.getChildren().addAll(cardLabel, playerHandGui);

    //VBox toolBarBox = new VBox();
    //toolBarBox.setAlignment(Pos.TOP_CENTER);
    //toolBarBox.getChildren().add(new Label("Toolbar as grid of icons here?"));

    //bottomPane.setTop(divider);
    //bottomPane.setLeft(drawDiscardPile);
    //bottomPane.setCenter(cardBox);
    //bottomPane.setRight(toolBarBox);
    mainPane.setBottom(bottomPane);
  }

  public void endPhase()
  {

    base.endPolicyVotingPhase();
  }

  private class ButtonControl implements EventHandler<ActionEvent>
  {
    private TestVotingPane parentPane;

    public ButtonControl(TestVotingPane parentPane)
    {
      this.parentPane = parentPane;
    }

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
//////////// Unused interface methods ////////////
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
