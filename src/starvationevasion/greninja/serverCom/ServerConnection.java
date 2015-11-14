package starvationevasion.greninja.serverCom;

import starvationevasion.greninja.gameControl.GameController;

/**
 * Created by sagalasan on 11/14/15.
 */
public class ServerConnection
{
  private static final int DEFAULT_PORT = 8888;

  private String hostName;
  private int portNumber;
  private GameController gameController;

  /**
   * Initiates a connection to a server
   * @param gameController Reference to the gameController
   * @param hostName name of the host
   * @param portNumber port to connect to
   */
  public ServerConnection(GameController gameController, String hostName, int portNumber)
  {
    this.gameController = gameController;
    this.hostName = hostName;
    this.portNumber = portNumber;
  }

  /**
   * Initiates a connection to a server with a default port: 8888
   * @param gameController Reference to the gameController
   * @param hostName name of the host
   */
  public ServerConnection(GameController gameController, String hostName)
  {
    this(gameController, hostName, DEFAULT_PORT);
  }

  
}
