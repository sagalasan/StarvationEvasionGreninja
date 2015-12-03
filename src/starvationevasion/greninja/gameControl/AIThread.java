package starvationevasion.greninja.gameControl;

/**
 * This will be a thread that an AI runs on.  This is basically another instance
 * of the client (hopefully).
 */
public class AIThread extends GameController implements Runnable
{
  private boolean stillPlaying = true;

  public AIThread(String loginName, String loginPW)
  {
    //do login stuff.
  }

  @Override
  public void run()
  {
    //play a game
    while(stillPlaying)
    {
      //sit here and let game controller take care of everything
    }
    //disconnect and stop.
  }
}
