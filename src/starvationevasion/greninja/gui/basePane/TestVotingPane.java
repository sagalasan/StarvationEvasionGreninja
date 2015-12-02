package starvationevasion.greninja.gui.basePane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import starvationevasion.common.EnumRegion;
import starvationevasion.greninja.gui.ComponentImageView.CardImage;
import starvationevasion.greninja.gui.ComponentImageView.VotingCards;
import starvationevasion.greninja.gui.GuiBase;
import starvationevasion.greninja.gui.MapHolder;
import starvationevasion.greninja.gui.componentPane.*;
import starvationevasion.greninja.model.State;

/**
 * Created by Jalen on 11/27/2015.
 */

/**
 * makes a voting pane.
 */
public class TestVotingPane extends GamePhasePane implements MapHolder
{
  private final static int NUMBER_OF_TOTAL_DRAFTED_CARDS = 14;
  private GuiBase base;
  private PlayerHandGui playerHandGui;
  private BorderPane mainPane = new BorderPane();
  private CardImage[] stateCards;

  private VBox statsBox;
  //private String[] regionNames
  //each region needs a name
  public TestVotingPane(GuiBase base)
  {
    super(base);
    this.base = base;
    stateCards = new CardImage[NUMBER_OF_TOTAL_DRAFTED_CARDS];


  }

  /**
   * Instantiates and adds components to pane.
   */
  public void initPane()
  {
    super.initTimerPane(2, 0);
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
    timer.setAlignment(Pos.CENTER);

    VBox titleBox = new VBox();
    titleBox.setPadding(new Insets(15, 0, 0, 0));
    titleBox.setAlignment(Pos.BOTTOM_CENTER);
    Label title = new Label("Voting Phase: Vote On Policies");
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
    topPane.setCenter(titleBox);
    mainPane.setTop(topPane);
  }

  /*
   * Left pane holds the timer, action buttons, and drafted policies.
   */
  private void buildLeft()
  {

  }
  /*
   * Contains graphs
   */
  private void buildRight()
  {
    BorderPane rightPane = new BorderPane();
    rightPane.setId("rightLayout");
    rightPane.setPrefWidth(300);

    statsBox = new VBox(10);
    statsBox.setAlignment(Pos.TOP_CENTER);
    createRightPane(EnumRegion.CALIFORNIA);

    rightPane.setCenter(statsBox);
    mainPane.setRight(rightPane);
  }

  /*
   * Adds a clickable US map to the center of the pane.
   */
  private BorderPane center;
  private void buildCenter()
  {
    //true for policyPane, false for VotingPane
    center = new BorderPane();
    ClickableMap map = new ClickableMap("voting");
    map.setContainingPane(this);
    //VBox draftedCardsAndRegions = new VBox();
    HBox draftedCards = new HBox();
    HBox otherDraftedCards = new HBox();
    otherDraftedCards.setSpacing(70);
    otherDraftedCards.setAlignment(Pos.CENTER);
    draftedCards.setSpacing(20);
    draftedCards.setAlignment(Pos.CENTER);


    VotingCards voteCard = new VotingCards(new Image("file:assets/CardImages/magikarp.png"), true);

    for (int i = 0; i < 7; i++)
    {

      draftedCards.getChildren().add(new VotingCards(new Image("file:assets/CardImages/magikarp.png"), true));

    }
    //draftedCards.setId("voting-image");
    //draftedCardsAndRegions.getChildren().addAll(map, draftedCards);

    center.setTop(map);
    //VBox bothRowsOfCards = new VBox();
    //bothRowsOfCards.setSpacing(5);

    center.setCenter(draftedCards);
    //mainPane.setCenter(draftedCardsAndRegions);
    mainPane.setCenter(center);
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
    stopTimer();
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
    statsBox.getChildren().clear();
    createRightPane(region);
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

    if(state == null) throw new NullPointerException("Variable state should not be null!!");
    //creates a tabpane and tabs that display the graphs
    TabPane stats = new TabPane();
    Tab farmProduct = new Tab("Production Charts");
    Tab population = new Tab("Population");
    Tab HDI = new Tab("HDI");

    farmProduct.setClosable(false);
    population.setClosable(false);
    HDI.setClosable(false);

    // Label regionStatsLabel = new Label(state.toString() + " Statistics");

    VBox pop = new VBox();
    pop.getChildren().addAll(new RegionalStatistics(state, "Population"));

    VBox hdiVbox = new VBox();
    hdiVbox.getChildren().addAll(new RegionalStatistics(state, "HDI"));

    VBox farmPro = new VBox();
    farmPro.getChildren().addAll(new FarmProductChartPane(state));

    farmProduct.setContent(farmPro);
    population.setContent(pop);
    HDI.setContent(hdiVbox);

    stats.getTabs().addAll(population, farmProduct,HDI);

    statsBox.getChildren().addAll(new Label(state.toString() + " Statistics"), stats);
//    statsBox.getChildren().add(new Label(state.toString() + " Statistics"));
//    statsBox.getChildren().add(new RegionalStatistics(state, "Population"));
//    statsBox.getChildren().add(new RegionalStatistics(state, "HDI"));
//    statsBox.getChildren().add(new FarmProductChartPane(state));
  }
}
