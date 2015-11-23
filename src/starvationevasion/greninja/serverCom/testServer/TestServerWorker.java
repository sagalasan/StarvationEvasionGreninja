package starvationevasion.greninja.serverCom.testServer;

import starvationevasion.greninja.serverCom.threads.ServerWriter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by sagalasan on 11/22/15.
 */
public class TestServerWorker extends Thread
{
  private Socket client;
  private ObjectInputStream objectInputStream;
  private ObjectOutputStream objectOutputStream;

  public TestServerWorker(Socket client)
  {
    this.client = client;
    try
    {
      objectOutputStream = new ObjectOutputStream(client.getOutputStream());
      objectOutputStream.flush();
      objectInputStream = new ObjectInputStream(client.getInputStream());
    }
    catch (IOException ioe)
    {
      ioe.printStackTrace();
    }
  }

  @Override
  public void run()
  {
    while(true)
    {
      Object o = new Object();
      try
      {
        o = objectInputStream.readObject();
      } catch (IOException ioe)
      {
        ioe.printStackTrace();
      } catch (ClassNotFoundException cnfe)
      {
        cnfe.printStackTrace();
      }
      System.out.println(o.getClass().getName());
    }
  }
}
