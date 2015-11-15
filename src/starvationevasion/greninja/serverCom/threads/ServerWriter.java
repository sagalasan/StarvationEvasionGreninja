package starvationevasion.greninja.serverCom.threads;

import starvationevasion.greninja.serverCom.ServerConnection;

import java.io.PrintWriter;

/**
 * Created by sagalasan on 11/14/15.
 */
public class ServerWriter extends Thread
{
  private ServerConnection serverConnection;
  private PrintWriter writer;

  public ServerWriter(ServerConnection serverConnection, PrintWriter writer)
  {
    this.serverConnection = serverConnection;
    this.writer = writer;
  }

  @Override
  public void run()
  {
    while(true)
    {

    }
  }
}
