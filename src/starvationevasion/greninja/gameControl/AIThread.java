package starvationevasion.greninja.gameControl;

import starvationevasion.common.messages.Hello;
import starvationevasion.common.messages.Login;
import starvationevasion.greninja.clientCommon.ClientConstant;

import java.io.Serializable;
import java.util.ArrayDeque;

/**
 * This will be a thread that an AI runs on.  This is basically another instance
 * of the client (hopefully).
 */
public class AIThread extends GameController implements Runnable
{
  private boolean stillPlaying = true;
  private String loginName;
  private String loginPW;
  private ArrayDeque<Serializable> messageQueue;

  public AIThread(String loginName, String loginPW)
  {
    //do login stuff.
    super(loginName);
    this.loginName = loginName;
    this.loginPW = loginPW;
    this.messageQueue = new ArrayDeque<>();
  }

  @Override
  public void run()
  {
    //connect!
    getServerLine().startConnection(ClientConstant.LOCAL_HOST, ClientConstant.TEST_PORT);
    //play a game
    while(stillPlaying)
    {
      checkPoint();
      super.handleMessageIn(messageQueue.removeFirst());
    }
    //disconnect and stop.
  }

  /**
   * Intercept hello received message and send Login to server.
   * @param message
   */
  @Override
  public synchronized void helloReceived(Hello message)
  {
    print("Received hello!");
    Hello hello = (Hello) message;
    String salt = hello.loginNonce;
    sendLoginInfo(loginName, loginPW, salt);
  }

  /**
   * Intercept handle message in.  This will add the message to the message queue
   * and notify that run method that there are messages to do stuff with.
   * @param message       Serializable object.
   */
  @Override
  public synchronized void handleMessageIn(Serializable message)
  {
    messageQueue.addLast(message);
    notify();
  }

  private synchronized void checkPoint()
  {
    while(messageQueue.isEmpty())
    {
      try
      {
        wait();
      }
      catch (InterruptedException e)
      {
        System.out.println("AI Thread Interrupted");
        e.printStackTrace();
      }
    }
  }
}
