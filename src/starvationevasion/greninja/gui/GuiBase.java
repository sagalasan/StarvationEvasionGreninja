package starvationevasion.greninja.gui;

import starvationevasion.common.EnumRegion;
import starvationevasion.greninja.gameControl.GameController;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * @author Justin Thomas jthomas105@unm.edu
 */
public class GuiBase extends Application
{
  private PolicyPane policyPane;
  private VotingPane votingPane;
  private Stage mainStage;
  private Scene baseScene;
  private GameController control;

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
    staging.initStagingPane();
    baseScene.setRoot(staging);
  }

  /**
   * Initialize GUI components.
   */
  private void setupGui()
  {
    //initialize components
    mainStage.setTitle("Starvation Evasion");
    policyPane = new PolicyPane(this);
    votingPane = new VotingPane(this);
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
