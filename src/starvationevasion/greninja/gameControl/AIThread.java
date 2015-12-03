package starvationevasion.greninja.gameControl;

/**
 * This will be a thread that an AI runs on.  This is basically another instance
 * of the client (hopefully).
 */
public class AIThread extends Thread
{
  private GameController control;
  private boolean stillPlaying = true;

  public AIThread(String loginName, String loginPW)
  {
    control = new GameController();
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
