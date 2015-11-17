package starvationevasion.greninja.serverCom.threads;

import starvationevasion.greninja.serverCom.ServerConnection;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by sagalasan on 11/14/15.
 */
public class ServerReader extends Thread
{
  private ServerConnection serverConnection;
  private BufferedReader reader;

  public ServerReader(ServerConnection serverConnection, BufferedReader reader)
  {
    this.serverConnection = serverConnection;
    this.reader = reader;
  }

  @Override
  public void run()
  {
    while(true)
    {
      String message;
      try
      {
        message = reader.readLine();
        System.out.println(message);
      }
      catch (IOException ioe)
      {
        ioe.printStackTrace();
        break;
      }

    }
  }
}
