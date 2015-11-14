package starvationevasion.greninja.util;

import starvationevasion.greninja.gameControl.GamePhase;

/**
 * Timer to control game phases.
 */
public class PhaseTimer extends Thread
{
  private static final long NANO_PER_SECOND = 1000000000;
  private static final int SECONDS_PER_MINUTE = 60;

  private long startTime;
  private long endTime;
  private GamePhase phase;

  /**
   * Construct at beginning of phase.  Logs current system time and calculates
   * the time limit.
   * @param timeLimit       Time limit for phase in minutes.
   */
  public PhaseTimer(int timeLimit, GamePhase phase)
  {
    startTime = System.nanoTime();
    long nanoTimeLimit = (long)timeLimit * NANO_PER_SECOND * SECONDS_PER_MINUTE;
    endTime = startTime + nanoTimeLimit;
    this.phase = phase;
  }

  /**
   * Check to see if the phase has exceeded it's time limit.  Method checks
   * current time against the phase start time + the time limit.
   * @return        true if time is not exceeded, false if time is over.
   */
  public boolean phaseNotOver()
  {
    if(System.nanoTime() <= (endTime))
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  /**
   * Check if phase time limit is not reached every second.
   */
  public void run()
  {
    while(phaseNotOver())
    {
      try
      {
        sleep(1000);
      }
      catch(InterruptedException e)
      {
        System.out.println("Timer Interrupted.");
        e.printStackTrace();
      }
    }
    if(phase != null)
    {
      phase.phaseOver();
    }
  }
}
