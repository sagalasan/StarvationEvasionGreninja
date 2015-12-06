package starvationevasion.greninja.gameControl;

import javafx.application.Platform;
import starvationevasion.common.EnumRegion;
import starvationevasion.common.messages.Hello;
import starvationevasion.common.messages.Login;
import starvationevasion.common.messages.LoginResponse;
import starvationevasion.common.messages.RegionChoice;
import starvationevasion.greninja.clientCommon.ClientConstant;
import starvationevasion.greninja.gui.GuiBase;

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
  private String salt;
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
    for(int i = 0; i < 100; i++)
    {
      print("Attempt " + i);
      if(!getServerLine().isConnected())
      {
        try
        {
          Thread.sleep(500);
        }
        catch (InterruptedException e)
        {
          e.printStackTrace();
        }
        getServerLine().startConnection(ClientConstant.LOCAL_HOST, ClientConstant.TEST_PORT);
      }
      else break;
    }
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
    Hello hello = message;
    salt = hello.loginNonce;
    print("Received hello! -> salt: " + salt);
    sendLoginInfo(loginName, loginPW, salt);
  }

  @Override
  public synchronized void handleLoginResponse(LoginResponse response)
  {
    print("Login Response Received: " + response.responseType);
    LoginResponse.ResponseType type = response.responseType;
    //guiView = (GuiBase) view;
    switch (type)
    {
      case ACCESS_DENIED:
        try
        {
          Thread.sleep(100);
        } catch (InterruptedException e)
        {
          e.printStackTrace();
        }
        sendLoginInfo(loginName, loginPW, salt);
        break;
      case ASSIGNED_REGION:
        EnumRegion region = response.assignedRegion;
        regionSelected(region);
        //playerRegion = region;
        //player.setPlayerRegion(region);
        sendMessageOut(new RegionChoice(region));
        //skip staging pane?
        break;
      case REJOIN:
        //??
        break;
      case CHOOSE_REGION:
        //go to staging like normal;
        break;
      default:
        break;
    }
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
