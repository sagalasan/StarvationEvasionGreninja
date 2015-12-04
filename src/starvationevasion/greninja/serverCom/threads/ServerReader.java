package starvationevasion.greninja.serverCom.threads;

import starvationevasion.greninja.serverCom.ServerConnection;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by sagalasan on 11/14/15.
 */
public class ServerReader extends Thread
{
  private ServerConnection serverConnection;
  private ObjectInputStream objectInputStream;

  public ServerReader(ServerConnection serverConnection, ObjectInputStream objectInputStream)
  {
    this.serverConnection = serverConnection;
    this.objectInputStream = objectInputStream;
  }

  @Override
  public void run()
  {
    while(true)
    {
      Object message;
      try
      {
        message = objectInputStream.readObject();
        serverConnection.receiveMessage(message);
        //System.out.println(message);
      }
      catch (IOException ioe)
      {
        ioe.printStackTrace();
        break;
      }
      catch (ClassNotFoundException cnfe)
      {
        cnfe.printStackTrace();
      }

    }
  }
}
