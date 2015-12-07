package starvationevasion.greninja.gui.basePane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import starvationevasion.common.EnumRegion;
import starvationevasion.greninja.gui.GuiBase;
import starvationevasion.greninja.gui.MapHolder;
import starvationevasion.greninja.gui.componentPane.*;
import starvationevasion.greninja.model.State;

//display numbers besides state names
public class PolicyPane extends GamePhasePane implements MapHolder
{
  private GuiBase base;
  private PlayerHandGui playerHandGui;
  private VBox cardBox;
  private ToolbarPane toolbar;
  private BorderPane mainPane = new BorderPane();

  // TODO: make this not hard-coded later
  private EnumRegion playerRegion;
  private int year = 2015;
  private double HDI = 200.0;
  private double regionPopulation = 1;
  private double worldPopulation = 2;

  private VBox draftedCardsBox;
  private DraftedPolicyCardPane draftedCards;
  VBox titleBox;
  private VBox statsBox;

  public PolicyPane(GuiBase base)
  {
    super(base);
    this.base = base;
    super.initTimerPane(5, 0);
    mainPane.setId("mainPolicyPane");
    base.getGameController().setDraftedCards();
    toolbar = new ToolbarPane(base);

    // Get the information to be displayed in the GUI
    // getInfo();

    // Build each sector of the GUI
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

    // Draw divider
    Label divider = new Label();
    divider.setId("divider");
    divider.setMaxHeight(1);
    divider.setMinHeight(1);
    divider.setMinWidth(Screen.getPrimary().getBounds().getWidth());

    // Set up timer display
    TimerDisplay timerDisplay = new TimerDisplay(base, getTimerPane());
    timerDisplay.setPadding(new Insets(10, 10, 10, 10));

    // Create and add title and game state info
    titleBox = new VBox(5);
    titleBox.setAlignment(Pos.TOP_CENTER);
    updateTopDisplay(EnumRegion.CALIFORNIA);

//    VBox titleBox = new VBox(5);
//    titleBox.setAlignment(Pos.TOP_CENTER);
//    Label title = new Label("Policy Phase: Draft Policies (" + year + ")");
//    HBox gameInfo = gameStateInfo();
//    title.setId("title");
//    titleBox.getChildren().addAll(title, gameInfo);

    // TODO: remove this button?
    // Create and add next state button
    VBox buttonBox = new VBox();
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setPadding(new Insets(10, 10, 10, 10));
    Button nextPhaseButton = new Button("Next Phase");
    nextPhaseButton.setOnAction(new ButtonControl(this));
    buttonBox.getChildren().add(nextPhaseButton);

    topPane.setBottom(divider);
    topPane.setLeft(timerDisplay);
    topPane.setRight(buttonBox);
    topPane.setCenter(titleBox);
    //topPane.setPrefHeight(100);
    mainPane.setTop(topPane);
  }

  /*
   * Left pane holds the timer, action buttons, and drafted policies.
   */

  private void buildLeft()
  {
    VBox leftPane = new VBox();
    leftPane.setId("leftLayout");
    leftPane.setPrefWidth(300);

    VBox visBox = new VBox(5);
    visBox.setAlignment(Pos.TOP_CENTER);
    //WorldViewer earthViewLayout = new WorldViewer();
    //visBox.getChildren().add(earthViewLayout);


    draftedCardsBox = new VBox(5);
    draftedCardsBox.setAlignment(Pos.BOTTOM_CENTER);
    Label draftTitle = new Label("Drafted Policies");

    draftedCards = new DraftedPolicyCardPane(base);
    setPrefWidth(draftedCards.getMaxWidth());
    draftedCardsBox.getChildren().addAll(draftTitle, draftedCards);

    leftPane.getChildren().addAll(visBox, draftedCardsBox);//.setTop(visBox);
    mainPane.setLeft(leftPane);
  }



  /*
   * Builds the right pane, which contains the graphs for each region.
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
  private void buildCenter()
  {
    ClickableMap map = new ClickableMap("policy");
    map.setContainingPane(this);
    mainPane.setCenter(map);
  }

  /*
   * Builds the bottom pane, which contains the player hand, draw/discard deck, and farm product toolbar.
   */
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

    DiscardPile drawDiscardPile = new DiscardPile(base);

    drawDiscardPile.setSpacing(5);
    drawDiscardPile.setPadding(new Insets(10, 10, 10, 10));
    drawDiscardPile.setAlignment(Pos.TOP_CENTER);


    cardBox = new VBox(10);
    cardBox.setPadding(new Insets(10, 10, 10, 10));
    cardBox.setAlignment(Pos.TOP_CENTER);
    Label cardLabel = new Label("Your Cards");
    playerHandGui = new PlayerHandGui(base);

    cardBox.getChildren().addAll(cardLabel, playerHandGui);

    VBox toolBarBox = new VBox(3);
    toolBarBox.setPadding(new Insets(10, 10, 10, 10));
    toolBarBox.setAlignment(Pos.TOP_CENTER);
    Label productLabel = new Label("Product Types");
    toolBarBox.getChildren().addAll(productLabel, toolbar);

    bottomPane.setTop(divider);
    bottomPane.setLeft(drawDiscardPile);
    bottomPane.setCenter(cardBox);
    bottomPane.setRight(toolBarBox);
    mainPane.setBottom(bottomPane);
  }

  private HBox gameStateInfo()
  {
    HBox populationBox = new HBox(10);
    populationBox.setAlignment(Pos.TOP_CENTER);

    HBox worldPopBox = new HBox(2);
    Label worldPopLabel = new Label("World Pop: ");
    worldPopLabel.setId("stateInfoLabel");
    String worldPopString = Double.toString(worldPopulation);
    Label worldPop = new Label(worldPopString);
    worldPop.setId("stateInfo");
    worldPopBox.getChildren().addAll(worldPopLabel, worldPop);

    HBox regionPopBox = new HBox(2);
    Label regionPopLabel = new Label("Region Pop: ");
    regionPopLabel.setId("stateInfoLabel");
    String regionPopString = Double.toString(regionPopulation);
    Label regionPop = new Label(regionPopString);
    regionPop.setId("stateInfo");
    regionPopBox.getChildren().addAll(regionPopLabel, regionPop);

    HBox hdiBox = new HBox(2);
    Label hdiLabel = new Label("Human Dev. Index: ");
    hdiLabel.setId("stateInfoLabel");
    String hdiString = Double.toString(HDI);
    Label hdiInfo = new Label(hdiString);
    hdiInfo.setId("stateInfo");
    hdiBox.getChildren().addAll(hdiLabel, hdiInfo);

    populationBox.getChildren().addAll(regionPopBox, worldPopBox, hdiBox);
    return populationBox;
  }

  public void endPhase()
  {
    stopTimer();
    base.endPolicyDraftingPhase();
  }

  private class ButtonControl implements EventHandler<ActionEvent>
  {
    private PolicyPane parentPane;

    public ButtonControl(PolicyPane parentPane)
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
    titleBox.getChildren().clear();
    statsBox.getChildren().clear();
    updateTopDisplay(region);
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

  private State getSelectedRegion(EnumRegion region)
  {
    State state = null;
    switch (region)
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

    if(state == null) throw new NullPointerException("Variable state should not be null.");
    return state;
  }

  private void updateTopDisplay(EnumRegion region)
  {
//    // Draw divider
//    Label divider = new Label();
//    divider.setId("divider");
//    divider.setMaxHeight(1);
//    divider.setMinHeight(1);
//    divider.setMinWidth(Screen.getPrimary().getBounds().getWidth());
//
//    // Set up timer display
//    TimerDisplay timerDisplay = new TimerDisplay(base, getTimerPane());
//    timerDisplay.setPadding(new Insets(10, 10, 10, 10));
//
//    // Create and add title and game state info
//    VBox titleBox = new VBox(5);
//    titleBox.setAlignment(Pos.TOP_CENTER);
//    Label title = new Label("Policy Phase: Draft Policies (" + year + ")");
//    HBox gameInfo = gameStateInfo();
//    title.setId("title");
//    titleBox.getChildren().addAll(title, gameInfo);
//
//    // TODO: remove this button?
//    // Create and add next state button
//    VBox buttonBox = new VBox();
//    buttonBox.setAlignment(Pos.CENTER);
//    buttonBox.setPadding(new Insets(10, 10, 10, 10));
//    Button nextPhaseButton = new Button("Next Phase");
//    nextPhaseButton.setOnAction(new ButtonControl(this));
//    buttonBox.getChildren().add(nextPhaseButton);
//
//    topPane.setBottom(divider);
//    topPane.setLeft(timerDisplay);
//    topPane.setRight(buttonBox);
//    topPane.setCenter(titleBox);
//    //topPane.setPrefHeight(100);
//    mainPane.setTop(topPane);


    State state = getSelectedRegion(region);
    regionPopulation = state.getPopulation();
    HDI = state.getHDI();

    Label title = new Label("Policy Phase: Draft Policies (" + year + ")");
    HBox gameInfo = gameStateInfo();
    title.setId("title");
    titleBox.getChildren().addAll(title, gameInfo);
  }

  private void createRightPane(EnumRegion region)
  {
    State state =  getSelectedRegion(region);
    //creates a tabpane and tabs that display the graphs
    TabPane stats = new TabPane();
    Tab farmProduct = new Tab("Production");
    Tab population = new Tab("Population");
    Tab HDI = new Tab("HDI");
    Tab revenueBalance = new Tab("Balance");

    farmProduct.setClosable(false);
    population.setClosable(false);
    HDI.setClosable(false);
    revenueBalance.setClosable(false);
    // Label regionStatsLabel = new Label(state.toString() + " Statistics");

    VBox pop = new VBox();
    pop.getChildren().addAll(new RegionalStatistics(state, "Population"));

    VBox hdiVbox = new VBox();
    hdiVbox.getChildren().addAll(new RegionalStatistics(state, "HDI"));

    VBox farmPro = new VBox();
    farmPro.getChildren().addAll(new FarmProductChartPane(state));

    VBox revenueBalanceBox = new VBox();
    revenueBalanceBox.getChildren().add(new RegionalStatistics(state, "RevenueBalance"));

    farmProduct.setContent(farmPro);
    population.setContent(pop);
    HDI.setContent(hdiVbox);
    revenueBalance.setContent(revenueBalanceBox);


    stats.getTabs().addAll(population, farmProduct,HDI, revenueBalance);

    statsBox.getChildren().addAll(new Label(state.toString() + " Statistics"), stats);
  }
  public void updateDraftedCards()
  {
    //int tempIndexOfDraftedCards = draftedCardsBox.getChildren().indexOf(draftedCards);
    draftedCards.updateCards();
    //draftedCardsBox.getChildren().set(tempIndexOfDraftedCards, draftedCards);
  }
  public void updatePlayerHand()
  {
    //int tempIndexOfPlayerHand = cardBox.getChildren().indexOf(playerHandGui);
    playerHandGui.updatePlayerHand();// = new PlayerHandGui(base);
    //cardBox.getChildren().set(tempIndexOfPlayerHand, playerHandGui);
  }
}
