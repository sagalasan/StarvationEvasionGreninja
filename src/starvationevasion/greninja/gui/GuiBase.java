package starvationevasion.greninja.gui;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import starvationevasion.common.EnumPolicy;
import starvationevasion.common.PolicyCard;
import starvationevasion.common.messages.*;
import starvationevasion.greninja.clientCommon.EnumPhase;
import starvationevasion.common.EnumRegion;
import starvationevasion.greninja.gameControl.AIThread;
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
import starvationevasion.vis.ClientTest.CustomLayout;
import starvationevasion.vis.controller.EarthViewer;

import java.util.ArrayList;
import java.util.List;

/**
 * GUI hub class and main method of client application.
 * All user input and output is routed through this class.
 * @author Justin Thomas jthomas105@unm.edu
 */
public class GuiBase extends Application implements ControlListener
{
  // ERIN'S ADDED VARIABLES (to be deleted later)
  private TestPolicyPane testPolicyPane;// = new TestPolicyPane(this);
  private TestVotingPane testVotingPane;// = new TestVotingPane(this);


  private EntryPane entryPane = new EntryPane(this);
  private ServerConnectionPane serverConnectionPane = new ServerConnectionPane(this);
  private LoginPane loginPane = new LoginPane(this);
  //private PolicyPane policyPane = new PolicyPane(this);
  //private VotingPane votingPane = new VotingPane(this);
  private StagingPane stagingPane;

  private GuiTimerSubscriber paneToRefresh;
  private EnumRegion playerRegion, viewedRegion;
  private State playerRegionInfo, viewedRegionInfo; //references to region observed
  private Stage mainStage;
  private Scene baseScene;
  private GameController control;
  private GuiTimer guiTimer;
  private ArrayList<AIThread> aiThreads;

  private ArrayList<PolicyCard> playerHand;

  private ServerThread serverThread;


  /**
  public void initializePanes()
  {
    //changed this
    //testPolicyPane = new TestPolicyPane(this);
    //testVotingPane = new TestVotingPane(this);

    //stagingPane.initPane();
  }
**/
  /**
   * Starts a game with no server or ais
   */
  public void beginTestingGame()
  {
    //startVisualizer();//this will also have to be on begin single player
    control.startTestingGame();
  }

  /**
   * Starts a single player game.
   */
  public void beginSinglePlayer()
  {
    startAIThreads();

    control.startSinglePlayerGame("neo", "thel");
  }

  /**
   * Start a multiplayer game.
   * TODO get server name from entrypane
   */
  /*
  public void beginMultiPlayer()
  {
    control.startMultiPlayerGame("ServerName");
  }*/

  public void multiPlayerSelected()
  {
    control.multiPlayerSelected();
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
  public void loginInfoSent(String name, String password, String salt)
  {
    control.sendLoginInfo(name, password, salt);//
  }

  public void attemptToConnectServer(String hostname, int port)
  {
    control.attemptConnection(hostname, port);
  }

  /**
   * Region selected method, takes a region arg.
   */
  public void regionSelected(EnumRegion region)
  {
    //inform control that region is selected so it can tell the server.
    control.regionSelected(region);
  }

  public void swapToEntryPane()
  {
    System.out.println("Swapping to EntryPane");
    baseScene.setRoot(entryPane);
  }

  public void swapToServerConnectionPane()
  {
    System.out.println("Swapping to ServerConnectionPane");
    baseScene.setRoot(serverConnectionPane);
  }

  public void swapToLoginPane(String salt)
  {
    System.out.println("Swapping to LoginPane");
    serverConnectionPane.setHelloReceived(true);
    baseScene.setRoot(loginPane);
    loginPane.setSalt(salt);
  }

  public void sendLoginFailed(LoginResponse response)
  {
    loginPane.loginFailed(response);
  }

  public void serverConnectionPaneCancelled()
  {
    control.serverConnectionPaneCancelled();
  }

  /**
   * Change to voting pane.
   */
  @Override
  public void swapToVotingPane()
  {
    testVotingPane = new TestVotingPane(this);
    System.out.println("Now in Voting.");

    paneToRefresh = testVotingPane;
    baseScene.setRoot(testVotingPane);
    testVotingPane.resetTimer();
    testVotingPane.startTimer();
    //set current images to null, init images for other pane
  }

  /**
   * Change to policy drafting pane.
   */
  @Override
  public void swapToPolicyPane()
  {
    testPolicyPane = new TestPolicyPane(this);
    System.out.println("Now in Policy Drafting.");
    initPlayerRegionInfo(playerRegionInfo, playerRegion);
    paneToRefresh = testPolicyPane;
    testPolicyPane.resetTimer();
    testPolicyPane.startTimer();
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

    baseScene.setRoot(stagingPane);
    paneToRefresh = stagingPane;

  }

  public void lockStagingPane(EnumRegion region)
  {
    stagingPane.lock(region);
  }

  public void displayProposeDialog(PolicyCard policyCard)
  {
    ProposalDialog proposalDialog = new ProposalDialog(this, policyCard);
    testPolicyPane.addDarkenOverlay();
    testPolicyPane.getChildren().add(proposalDialog);
    proposalDialog.toFront();
  }

  public void removeProposalDialog(ProposalDialog dialog)
  {
    testPolicyPane.getChildren().remove(dialog);
    testPolicyPane.removeDarkenOverlay();
  }

  public void endPolicyDraftingPhase()
  {
    control.endPolicyDraftingPhase();
  }

  public void endPolicyVotingPhase()
  {
    control.endPolicyVotingPhase();
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
        timer = testPolicyPane.getTimerPane();
        break;
      case VOTING:
        //update voting phase timer.
        timer = testVotingPane.getTimerPane();
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

  /**
   * Send string chat message to control to pass on to server coms.
   * @param message       String message.
   * @param destination   destination regions
   */
  public void sendChatMessage(String message, EnumRegion[] destination)
  {
    ClientChatMessage messageOut = new ClientChatMessage(message, destination);
    control.sendMessageOut(messageOut);
  }

  /**
   * Send card info chat message to control to pass on to server coms.
   * @param card       card message.
   * @param destination   destination regions
   */
  public void sendChatMessage(EnumPolicy card, EnumRegion[] destination)
  {
    ClientChatMessage messageOut = new ClientChatMessage(card, destination);
    control.sendMessageOut(messageOut);
  }

  /**
   * Set the reference to player hand.  If this hasn't happened yet player hand
   * should be null.
   * @param playerHand
   */
  public void setPlayerHand(ArrayList<PolicyCard> playerHand)
  {
    this.playerHand = playerHand;
  }
  /*
  ==============================================================
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
    stagingPane = new StagingPane(this);
    //initializePanes(); //changed this

    StackPane loading = new StackPane();
    loading.getChildren().add(new Label("Loading."));
    baseScene = new Scene(loading, 500,500);
    mainStage.setScene(baseScene);
    //==========================================================================
    //this is where the style sheet gets added so that the .css will apply its design to
    //the nodes
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
    //mainStage.setResizable(true);

    guiTimer = new GuiTimer(this);
    guiTimer.start();
  }

  /**
   * This method should be called when the player selects single player.  Will
   * instantiate and start up 6 ais.
   */
  public void startAIThreads()
  {
    //serverThread = new ServerThread("config/easy_password_file.tsv");
    //serverThread.start();
    aiThreads = new ArrayList<>();
    aiThreads.add(new AIThread("user1", "password"));
    aiThreads.add(new AIThread("user2", "password"));
    aiThreads.add(new AIThread("user3", "password"));
    aiThreads.add(new AIThread("user4", "password"));
    aiThreads.add(new AIThread("user5", "password"));
    aiThreads.add(new AIThread("user6", "password"));
    for(AIThread thread : aiThreads)
    {
      new Thread(thread).start();
    }
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
    control = new GameController(this);
    mainStage = primaryStage;
    setupGui();

  }
  public List<PolicyCard> getPlayerHand()
  {
    return control.getPlayer().getPlayerHand();
  }

  /**
   * Override stop method because extra threads keep running after window is exited.
   */
  @Override
  public void stop()
  {
    try
    {
      control.sendMessageOut(new Goodbye("poop"));
      if(aiThreads != null)
      {
        for(AIThread ai : aiThreads)
        {
          ai.sendMessageOut(new Goodbye("me too."));
        }
      }
    }
    catch (RuntimeException re)
    {
      re.printStackTrace();
    }

    serverThread.stopServer();
    Platform.exit();
    System.exit(0);
  }

  public GameController getGameController()
  {
    return control;
  }
  public void updateDraftedCardsAndPlayerHand()
  {
    //testPolicyPane will have to recreate hands
    testPolicyPane.updateDraftedCards();
    testPolicyPane.updatePlayerHand();

  }

  //private EarthViewer earthViewer;

  //private void startVisualizer()
  //{
    //earthViewer = new EarthViewer(70, 250);


    //Start rotate will put the earthViewer object in an automatic and continuous rotation (this is for the mini view)
    //earthViewer.startRotate();
 // }
  //public EarthViewer getEarthViewer()
  //{

   // return earthViewer;
  //}
}
