package starvationevasion.greninja.gui;

import starvationevasion.greninja.clientCommon.EnumPhase;
import starvationevasion.common.EnumRegion;
import starvationevasion.greninja.gameControl.GameController;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import starvationevasion.greninja.gui.basePane.*;

/**
 * GUI hub class and main method of client application.
 * All user input and output is routed through this class.
 * @author Justin Thomas jthomas105@unm.edu
 */

public class GuiBase extends Application
{
  private PolicyPane policyPane;
  private VotingPane votingPane;
  private Stage mainStage;
  private Scene baseScene;
  private GameController control;

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
    baseScene.setRoot(votingPane);
  }

  /**
   * Change to policy drafting pane.
   */
  public void swapToPolicyPane()
  {
    System.out.println("Now in Policy Drafting.");
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
    baseScene.setRoot(staging);
  }

  /*
  ==============end to control==================================================
  ******************************************************************************
  ====================FROM CONTROL==============================================
   */

  /**
   * Update timer on appropriate pane
   * @param phase         Enum of current phase
   * @param time          int array of length two representing minutes and seconds
   *                      remaining.
   */
  public void updateTimer(EnumPhase phase, int[] time)
  {
    //update appropriate timer.
    switch(phase)
    {
      case DRAFTING:
        //update drafting phase timer.
        System.out.println(time[0] + " : " + time[1]);
        break;
      case VOTING:
        //update voting phase timer.
        break;
      default:
        System.out.println("No timer to update");
    }
  }

  /*
  ============end from control==================================================
   */

  /**
   * Initialize GUI components.
   */
  private void setupGui()
  {
    //initialize components
    mainStage.setTitle("Starvation Evasion");
    policyPane = new PolicyPane(this);
    policyPane.initPane();
    votingPane = new VotingPane(this);
    votingPane.initPane();
    baseScene = new Scene(votingPane, 300, 500);
    mainStage.setScene(baseScene);
    baseScene.setRoot(new EntryPane(this));
    mainStage.show();

    //maximize screen
    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();
    mainStage.setX(bounds.getMinX());
    mainStage.setY(bounds.getMinY());
    mainStage.setWidth(bounds.getWidth());
    mainStage.setHeight(bounds.getHeight());
  }

  public static void main(String[] args)
  {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage)
  {
    mainStage = primaryStage;
    setupGui();
    control = new GameController(this);
  }
}
