package starvationevasion.greninja.gui;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import starvationevasion.common.PolicyCard;
import starvationevasion.common.messages.AvailableRegions;
import starvationevasion.common.messages.Login;
import starvationevasion.greninja.clientCommon.EnumPhase;
import starvationevasion.common.EnumRegion;
import starvationevasion.greninja.gameControl.ControlListener;
import starvationevasion.greninja.gameControl.GameController;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import starvationevasion.greninja.gui.basePane.*;
import starvationevasion.greninja.gui.componentPane.TimerPane;
import starvationevasion.greninja.model.PlayerInterface;
import starvationevasion.greninja.model.State;

import java.util.List;

/**
 * GUI hub class and main method of client application.
 * All user input and output is routed through this class.
 * @author Justin Thomas jthomas105@unm.edu
 */
public class GuiBase extends Application implements ControlListener
{
  // ERIN'S ADDED VARIABLES (to be deleted later)
  private TestPolicyPane testPolicyPane = new TestPolicyPane(this);
  private TestVotingPane testVotingPane = new TestVotingPane(this);


  private EntryPane entryPane = new EntryPane(this);
  private PolicyPane policyPane = new PolicyPane(this);
  private VotingPane votingPane = new VotingPane(this);
  private StagingPane stagingPane;

  private GuiTimerSubscriber paneToRefresh;
  private EnumRegion playerRegion, viewedRegion;
  private State playerRegionInfo, viewedRegionInfo; //references to region observed
  private Stage mainStage;
  private Scene baseScene;
  private GameController control;
  private GuiTimer guiTimer;

  public void initializePanes()
  {
    votingPane.initPane();
    policyPane.initPane();

    // TODO: get rid of this test later
    testPolicyPane.initPane();
    testVotingPane.initPane();

  }
  /*
  ===========================TO CONTROL=========================================
   */

  /**
   * Starts a single player game.
   */
  public void beginSinglePlayer()
  {
    control.startSinglePlayerGame();
  }

  /**
   * Start a multiplayer game.
   * TODO get server name from entrypane
   */
  public void beginMultiPlayer()
  {
    control.startMultiPlayerGame("ServerName");
  }

  /**
   * Exit game function
   */
  public void exitGame()
  {
    System.out.println("Disconnect from server and Quit.");
  }

  /**
   * Tells control that player has entered login info.
   */
  public void loginInfoSent(String name, String password)
  {
    control.sendLoginInfo(name, password);//
  }

  /**
   * Region selected method, takes a region arg.
   */
  public void regionSelected(EnumRegion region)
  {
    //inform control that region is selected so it can tell the server.
    control.regionSelected(region);
  }

  /**
   * Change to voting pane.
   */
  @Override
  public void swapToVotingPane()
  {
    System.out.println("Now in Voting.");
    //votingPane.getTimerPane().resetTimer();
    //votingPane = new VotingPane(this);
    //votingPane.initPane();

    paneToRefresh = votingPane;
    //baseScene.setRoot(votingPane);
    //testPolicyPane.getMap().setMapToNull();
    //testVotingPane.getMap().initImages();
    baseScene.setRoot(testVotingPane);
    //set current images to null, init images for other pane

  }

  /**
   * Change to policy drafting pane.
   */
  @Override
  public void swapToPolicyPane()
  {
    System.out.println("Now in Policy Drafting.");
    initPlayerRegionInfo(playerRegionInfo, playerRegion);

    //testVotingPane.getMap().setMapToNull();
    //testPolicyPane.getMap().initImages();
    // TODO: get rid of test pane later
//    baseScene.setRoot(policyPane);

    baseScene.setRoot(testPolicyPane);
  }

  /**
   * Switch to staging pane.  Player chooses region and can see other players
   * choosing regions.
   */
  @Override
  public void swapToStagingPane()
  {
    System.out.println("Entered Staging Pane");
    stagingPane = new StagingPane(this);
    stagingPane.initPane();
    //initializePanes();
    baseScene.setRoot(stagingPane);
    paneToRefresh = stagingPane;

  }

  public void endPolicyDraftingPhase()
  {
    control.endPolicyDraftingPhase();
  }

  public void endPolicyVotingPhase()
  {
    control.endPolicyVotingPhase();
  }

  /*
  ==============end to control==================================================
  ******************************************************************************
  ====================FROM CONTROL==============================================
   */

  /**
   * Display login form and collect login info.  When multiplayer game is
   * selected, control calls this method and passes in a size 2 string array.
   * This method collects a username and password from the user and puts it into
   * the array.
   */
  public void loginForm()
  {
    //display login info.
    entryPane.showLoginDialog();
  }

  public void serverConnectForm()
  {
    entryPane.createServerSelectDialog();
  }

  /**
   * Ten second countdown to game start.  Called when control recieves ready to
   * begin message.
   */
  public void countdownToStart()
  {
    //countdown to game start
  }

  /**
   * Recieve available regions from control and pass on to Staging pane.
   * @param availableRegions        AvailableRegions method.
   */
  @Override
  public void updateAvailableRegions(AvailableRegions availableRegions, PlayerInterface player)
  {
    stagingPane.setAvailableRegions(availableRegions, player);
  }
  /**
   * Get timer pane of phase
   * @param phase       phase pane to get timer from.
   * @return
   */
  public TimerPane getTimerPane(EnumPhase phase)
  {
    //update appropriate timer.
    TimerPane timer;
    switch(phase)
    {
      case DRAFTING:
        //update drafting phase timer.
        timer = policyPane.getTimerPane();
        break;
      case VOTING:
        //update voting phase timer.
        timer = votingPane.getTimerPane();
        break;
      default:
        timer = null;
    }
    return timer;
  }

  /**
   * Set reference to player region info.  To be called once when game starts.
   * @param state       State object of player region.
   */
  public void initPlayerRegionInfo(State state, EnumRegion pRegion)
  {
    playerRegion = pRegion;
    playerRegionInfo = state;
    setViewedRegionInfo(state);
    viewedRegion = playerRegion;
  }

  /**
   * Set reference to viewed region info
   * @param state       State object of region currently selected on interactive
   *                    map.
   */
  public void setViewedRegionInfo(State state)
  {
    viewedRegionInfo = state;
  }

  /*
  ============end from control==================================================
   ****************************************************************************
   * ====================GUI MANAGEMENT========================================
   */

  /**
   * Handles timer events.  Called from GuiTimer.handle.
   */
  public void timerTick()
  {
    //do timer stuff.
    if(paneToRefresh != null)
    {
      paneToRefresh.timerTick();
    }
  }

  /**
   * Initialize GUI components.
   */
  private void setupGui()
  {
    //initialize components
    mainStage.setTitle("Starvation Evasion");

    initializePanes();

    StackPane loading = new StackPane();
    loading.getChildren().add(new Label("Loading."));
    baseScene = new Scene(loading, 300, 500);
    mainStage.setScene(baseScene);
    //==========================================================================
    //this is where the style sheet gets added so that the .css will apply its design to
    //the nodes

    // TODO: get rid of the testing statement later
//    baseScene.getStylesheets().add
//        (this.getClass().getResource("styleSheetForGui.css").toExternalForm());
    baseScene.getStylesheets().add
        (this.getClass().getResource("styles.css").toExternalForm());
    //=============================================================================
    baseScene.setRoot(entryPane);
    mainStage.show();

    //maximize screen
    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();
    mainStage.setX(bounds.getMinX());
    mainStage.setY(bounds.getMinY());
    mainStage.setWidth(bounds.getWidth());
    mainStage.setHeight(bounds.getHeight());
    guiTimer = new GuiTimer(this);
    guiTimer.start();
  }

  public static void main(String[] args)
  {
    if(args.length != 0 && args[0].equals("ai"))
    {
      System.out.println("Javafx intercepted.");
    }
    else
    {
      launch(args);
    }
  }

  @Override
  public void start(Stage primaryStage)
  {
    mainStage = primaryStage;
    setupGui();
    control = new GameController(this);
  }
  public List<PolicyCard> getPlayerHand()
  {
    return control.getPlayerHand();
  }

  /**
   * Override stop method to keep
   */
  @Override
  public void stop()
  {
    Platform.exit();
    System.exit(0);
  }
}
