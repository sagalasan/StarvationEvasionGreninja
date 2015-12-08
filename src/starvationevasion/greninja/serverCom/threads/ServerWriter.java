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

  /**
   * Constructor that user serverConnection and objectOutputStream
   * @param serverConnection reference to the ServerConnection class
   * @param objectOutputStream The objectOuputStream used to communicate with server
   */
  public ServerWriter(ServerConnection serverConnection, ObjectOutputStream objectOutputStream)
  {
    this.serverConnection = serverConnection;
    this.objectOutputStream = objectOutputStream;
    messageQueue = new LinkedList<>();
  }

  /**
   * Adds message to queue so it can be sent out
   * @param o
   */
  public synchronized void addMessageToQueue(Object o)
  {
    if(o == null)
    {
      return;
    }
    messageQueue.add(o);
    notify();
  }

  /**
   * Overrides run() so it will send messages
   */
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
    Object object = messageQueue.removeFirst();
    if(object == null)
    {
      System.out.println("Message is null");
      return;
    }
    if(true)//ServerConnection.checkIfValidClass(object))
    {
      objectOutputStream.writeObject(object);
    }
    else
    {
      throw new IllegalArgumentException(object + ": This is not a starvationevasion.common.messages class");
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
