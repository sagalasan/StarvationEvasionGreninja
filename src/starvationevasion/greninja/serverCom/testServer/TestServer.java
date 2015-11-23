package starvationevasion.greninja.serverCom.testServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Created by sagalasan on 11/22/15.
 */
public class TestServer
{
  private int port;
  private ServerSocket serverSocket;
  private LinkedList<TestServerWorker> workers = new LinkedList<>();

  public TestServer(int port)
  {
    this.port = port;
    try
    {
      serverSocket = new ServerSocket(port);
    }
    catch (IOException ioe)
    {
      ioe.printStackTrace();
    }

    waitForConnection();
  }

  private void waitForConnection()
  {
    while(true)
    {
      try
      {
        System.out.println("Waiting for connection");
        Socket client = serverSocket.accept();
        System.out.println("Connected");
        TestServerWorker worker = new TestServerWorker(client);
        workers.add(worker);
      }
      catch (IOException ioe)
      {
        ioe.printStackTrace();
      }
    }
  }

  public static void main(String[] args)
  {
    TestServer server = new TestServer(8888);
  }

}
