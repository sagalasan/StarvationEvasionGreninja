package starvationevasion.greninja.serverCom.threads;

import starvationevasion.greninja.serverCom.ServerConnection;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by sagalasan on 11/14/15.
 */
public class ServerWriter extends Thread
{
  private ServerConnection serverConnection;
  private PrintWriter writer;

  private boolean needsToRun = false;

  private LinkedList<String> messageQueue;

  public ServerWriter(ServerConnection serverConnection, PrintWriter writer)
  {
    this.serverConnection = serverConnection;
    this.writer = writer;
    messageQueue = new LinkedList<String>();
  }

  public synchronized void addMessageToQueue(String msg)
  {
    messageQueue.add(msg);
    notify();
  }

  @Override
  public void run()
  {
    while(true)
    {
      guardedWrite();
      sendNextMessage();
    }
  }

  private synchronized void sendNextMessage()
  {
    System.out.println(messageQueue.removeFirst());
  }

  private synchronized void guardedWrite()
  {
    while(messageQueue.isEmpty())
    {
      try
      {
        wait();
      }
      catch (InterruptedException ie)
      {
        ie.printStackTrace();
      }
    }
  }
}
