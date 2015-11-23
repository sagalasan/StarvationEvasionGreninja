package starvationevasion.greninja.serverCom;

import starvationevasion.common.messages.AvailableRegions;
import starvationevasion.greninja.gameControl.GameController;
import starvationevasion.greninja.serverCom.threads.ServerReader;
import starvationevasion.greninja.serverCom.threads.ServerWriter;
import starvationevasion.common.messages.*;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Created by sagalasan on 11/14/15.
 */
public class ServerConnection
{
  private static final int DEFAULT_PORT = 8888;

  private String hostName;
  private int port;
  private GameController gameController;

  private boolean isConnectionValid = false;

  private Socket socket;
  private ObjectInputStream objectInputStream;
  private ObjectOutputStream objectOutputStream;

  private ServerReader serverReader;
  private ServerWriter serverWriter;


  /**
   * Initiates a connection to a server
   * @param gameController Reference to the gameController
   */
  public ServerConnection(GameController gameController)
  {
    this.gameController = gameController;
  }

  /**
   * Attempts to start connection to the server on the default port 8888
   * @param hostName name of the host
   * @return true if connection is established false if connection failed
   */
  public boolean startConnection(String hostName)
  {
    return startConnection(hostName, DEFAULT_PORT);
  }

  /**
   * Attempts to start connection to the server
   * @return true if connection is established; false if connection failed
   */
  public boolean startConnection(String hostName, int port)
  {
    this.hostName = hostName;
    this.port = port;

    if(isConnectionValid)
    {
      System.out.println("Connection failed: Connection is already established (hostname: " + hostName);
      return false;
    }

    try
    {
      socket = new Socket(hostName, port);
      objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
      objectOutputStream.flush();
      objectInputStream = new ObjectInputStream(socket.getInputStream());
    }
    catch (IOException ioe)
    {
      ioe.printStackTrace();
      return false;
    }

    serverReader = new ServerReader(this, objectInputStream);
    serverWriter = new ServerWriter(this, objectOutputStream);

    serverReader.start();
    serverWriter.start();

    isConnectionValid = true;
    return true;
  }

  public void endConnection()
  {
    System.out.println("Disconnecting from server");
    try
    {
      socket.close();
    }
    catch (IOException ioe)
    {
      ioe.printStackTrace();
    }

    isConnectionValid = false;
  }

  public Socket getSocket()
  {
    return socket;
  }

  public void sendMessage(Object o)
  {
    serverWriter.addMessageToQueue(o);
  }

  public static boolean checkIfValidClass(String name)
  {
    if(name.equals(AvailableRegions.class.getSimpleName())) return true;
    else if(name.equals(BeginGame.class.getSimpleName())) return true;
    else if(name.equals(Hello.class.getSimpleName())) return true;
    else if(name.equals(Login.class.getSimpleName())) return true;
    else if(name.equals(LoginResponse.class.getSimpleName())) return true;
    else if(name.equals(RegionChoice.class.getSimpleName())) return true;
    else if(name.equals(Response.class.getSimpleName())) return true;
    else return false;
  }

  public static void main(String[] args)
  {
    ServerConnection serverConnection = new ServerConnection(null);
    boolean b = serverConnection.startConnection("localhost");
    System.out.println(b);

    System.out.println("Sleeping for 2000");
    try
    {
      Thread.sleep(2000);
    } catch (InterruptedException e)
    {
      e.printStackTrace();
    }

    System.out.println("Sending messages");

    ArrayList<Hello> hellos = new ArrayList<>();
    for(int i = 0; i < 5; i++)
    {
      serverConnection.sendMessage(new Hello("hello", "43"));
    }
  }

}
