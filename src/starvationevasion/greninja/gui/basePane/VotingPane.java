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
import starvationevasion.common.EnumPolicy;
import starvationevasion.common.EnumRegion;
import starvationevasion.common.PolicyCard;
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
public class VotingPane extends GamePhasePane implements MapHolder
{
  private final static int NUMBER_OF_TOTAL_DRAFTED_CARDS = 14;
  private GuiBase base;
  private BorderPane mainPane = new BorderPane();
  private CardImage[] stateCards;

  private VBox statsBox;
  public VotingPane(GuiBase base)
  {
    super(base);
    this.base = base;
    stateCards = new CardImage[NUMBER_OF_TOTAL_DRAFTED_CARDS];
    super.initTimerPane(2, 0);
    mainPane.setId("mainPaneVoting");
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
    center = new BorderPane();
    ClickableMap map = new ClickableMap("voting");
    map.setContainingPane(this);
    HBox draftedCards = new HBox();
    draftedCards.setSpacing(20);
    draftedCards.setAlignment(Pos.CENTER);

    PolicyCard policyCard = PolicyCard.create(EnumRegion.CALIFORNIA, EnumPolicy.Covert_Intelligence);
    /**
     * todo uncomment this and test once ai/server/players are instantiated and working
     */
    /** uncomment to get cards from ai/player
    Image image = new Image("file:assets/CardImages/magikarp.png");
    ArrayList<PolicyCard> policyCards = base.getAllVotingCards();
    VBox[] votingCards = new VBox[7];//7 is the number of regions
    //for (int j = 0; j<7;j++)
    //{
    for (PolicyCard policy: policyCards)
    {
      if (policy.getOwner().equals(EnumRegion.CALIFORNIA))
      {
        votingCards[0].getChildren().add(new VotingCards(image, true, policy,base ));
      }
      else if (policy.getOwner().equals(EnumRegion.MOUNTAIN))
      {
        votingCards[1].getChildren().add(new VotingCards(image, true, policy,base ));
      }
      else if (policy.getOwner().equals(EnumRegion.HEARTLAND))
      {
        votingCards[2].getChildren().add(new VotingCards(image, true, policy,base ));
      }
      else if (policy.getOwner().equals(EnumRegion.NORTHERN_PLAINS))
      {
        votingCards[3].getChildren().add(new VotingCards(image, true, policy,base ));
      }
      else if (policy.getOwner().equals(EnumRegion.NORTHERN_CRESCENT))
      {
        votingCards[4].getChildren().add(new VotingCards(image, true, policy,base ));
      }
      else if (policy.getOwner().equals(EnumRegion.SOUTHERN_PLAINS))
      {
        votingCards[5].getChildren().add(new VotingCards(image, true, policy,base ));
      }
      else if (policy.getOwner().equals(EnumRegion.SOUTHEAST))
      {
        votingCards[6].getChildren().add(new VotingCards(image, true, policy,base ));
      }
    }
    HBox allCards = new HBox();
    allCards.setSpacing(20);
    allCards.setAlignment(Pos.CENTER);
    for (VBox votingCard: votingCards)
    {
      allCards.getChildren().add(draftedCards);
    }
     center.setCenter(allCards);
**/
    //place holder just to display some voting cards
    for (int i = 0; i < 7; i++)
    {
      draftedCards.getChildren().add(new VotingCards(true, policyCard, base));

    }

    center.setTop(map);

    center.setCenter(draftedCards);
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

    mainPane.setBottom(bottomPane);
  }

  public void endPhase()
  {
    stopTimer();
    base.endPolicyVotingPhase();
  }

  private class ButtonControl implements EventHandler<ActionEvent>
  {
    private VotingPane parentPane;

    public ButtonControl(VotingPane parentPane)
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
}
