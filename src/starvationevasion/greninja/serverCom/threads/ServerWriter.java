package starvationevasion.greninja.serverCom.threads;

import starvationevasion.greninja.serverCom.ServerConnection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

/**
 * Created by sagalasan on 11/14/15.
 */
public class ServerWriter extends Thread
{
  private ServerConnection serverConnection;

  private ObjectOutputStream objectOutputStream;

  private LinkedList<Object> messageQueue;

  public ServerWriter(ServerConnection serverConnection, ObjectOutputStream objectOutputStream)
  {
    this.serverConnection = serverConnection;
    this.objectOutputStream = objectOutputStream;
    messageQueue = new LinkedList<>();
  }

  public synchronized void addMessageToQueue(Object o)
  {
    if(o == null)
    {
      return;
    }
    messageQueue.add(o);
    notify();
  }

  @Override
  public void run()
  {
    while(true)
    {
      System.out.println("Waiting for next message");
      guardedWrite();

      try
      {
        sendNextMessage();
      }
      catch (IOException ioe)
      {
        System.out.println("Message failed to send");
        ioe.printStackTrace();
      }
    }
  }

  private synchronized void sendNextMessage() throws IOException
  {
    Object o = messageQueue.removeFirst();
    String name = getClassName(o);
    if(ServerConnection.checkIfValidClass(name))
    {
      objectOutputStream.writeObject(o);
    }
    else
    {
      throw new IllegalArgumentException(name + ": This is not a starvationevasion.common.messages class");
    }
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

  private String getClassName(Object o)
  {
    String name = o.getClass().getSimpleName();
    return name;
  }
}
