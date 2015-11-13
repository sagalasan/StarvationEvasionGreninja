package starvationevasion.greninja.gameControl;

import starvationevasion.common.EnumRegion;
import starvationevasion.greninja.gui.GuiBase;

/**
 * Main communication hub of client application.  This will be the go-between
 * class for the server message handlers, world state model, game state classes
 * and gui.
 * @author Justin Thomas(jthomas105@unm.edu)
 */
public class GameController
{
  private GuiBase gui;
  private EnumRegion playerRegion;
  //WorldModel
  //GameStateTracker

  public GameController(GuiBase gui)
  {
    this.gui = gui;
  }

  /**
   * Begin single player game.
   * Instantiate local server.
   */
  public void startSinglePlayerGame()
  {
    System.out.println("Start single player game.");
    gui.swapToStagingPane();
  }

  /**
   * Start multiplayer game and connect to server.
   */
  public void startMultiPlayerGame(String serverName)
  {
    //validate and attempt to connect to server.
    //if invalid go back.
    System.out.println("Start multiplayer game.");
    System.out.println("Prompt user for Server Name");
    System.out.println("Try To Connect");
    gui.swapToStagingPane();
  }

  /**
   * Perform region select actions.
   * @param region        region player chose.
   */
  public void regionSelected(EnumRegion region)
  {
    playerRegion = region;
    System.out.println("Inform server of choice.");
    System.out.println("Wait for other players.");
    gui.swapToPolicyPane();//
  }
}
