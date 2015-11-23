package starvationevasion.greninja.gui;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
//import starvationevasion.common.EnumPolicy;
import starvationevasion.common.PolicyCard;
import starvationevasion.greninja.clientCommon.EnumPhase;
import starvationevasion.common.EnumRegion;
import starvationevasion.greninja.gameControl.GameController;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import starvationevasion.greninja.gui.basePane.*;
import starvationevasion.greninja.gui.componentPane.TimerPane;
import starvationevasion.greninja.model.State;

import java.util.List;

/**
 * GUI hub class and main method of client application.
 * All user input and output is routed through this class.
 * @author Justin Thomas jthomas105@unm.edu
 */
//TODO put whole gui on timeline.
public class GuiBase extends Application
{
  private PolicyPane policyPane = new PolicyPane(this);
  private VotingPane votingPane = new VotingPane(this);

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
  public void swapToVotingPane()
  {
    System.out.println("Now in Voting.");
    //votingPane.getTimerPane().resetTimer();
    //votingPane = new VotingPane(this);
    //votingPane.initPane();

    paneToRefresh = votingPane;
    baseScene.setRoot(votingPane);
  }

  /**
   * Change to policy drafting pane.
   */
  public void swapToPolicyPane()
  {
    System.out.println("Now in Policy Drafting.");
    //policyPane.getTimerPane().resetTimer();
    //policyPane = new PolicyPane(this);
    //policyPane.initPane();
    paneToRefresh = policyPane;
    baseScene.setRoot(policyPane);
  }

  /**
   * Switch to staging pane.  Player chooses region and can see other players
   * choosing regions.
   */
  public void swapToStagingPane()
  {
    System.out.println("Entered Staging Pane");
    StagingPane staging = new StagingPane(this);
    staging.initPane();
    //initializePanes();
    baseScene.setRoot(staging);
    paneToRefresh = staging;
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
    //policyPane = new PolicyPane(this);
    //policyPane.initPane();
    //votingPane = new VotingPane(this);
    //votingPane.initPane();
    StackPane loading = new StackPane();
    loading.getChildren().add(new Label("Loading."));
    baseScene = new Scene(loading, 300, 500);
    mainStage.setScene(baseScene);
    //==========================================================================
    //this is where the style sheet gets added so that the .css will apply its design to
    //the nodes
    baseScene.getStylesheets().add
        (this.getClass().getResource("styleSheetForGui.css").toExternalForm());
    //=============================================================================
    baseScene.setRoot(new EntryPane(this));
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
