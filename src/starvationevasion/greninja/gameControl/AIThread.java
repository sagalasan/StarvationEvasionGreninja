package starvationevasion.greninja.gameControl;

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
    super();
    this.loginName = loginName;
    this.loginPW = loginPW;
    this.messageQueue = new ArrayDeque<>();
  }

  @Override
  public void run()
  {
    //play a game
    while(stillPlaying)
    {
      while(messageQueue.isEmpty())
      {
        //sit here and let game controller take care of everything
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
      super.handleMessageIn(messageQueue.pop());
    }
    //disconnect and stop.
  }

  @Override
  public void handleMessageIn(Serializable message)
  {
    messageQueue.addLast(message);
    notify();
  }
}
