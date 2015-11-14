package starvationevasion.greninja.util;

/**
 * Timer to control game phases.
 */
public class PhaseTimer
{
  private static final long NANO_PER_SECOND = 1000000000;
  private static final int SECONDS_PER_MINUTE = 60;

  private long startTime;
  private long nanoTimeLimit;

  /**
   * Construct at beginning of phase.  Logs current system time and calculates
   * the time limit.
   * @param timeLimit       Time limit for phase in minutes.
   */
  public PhaseTimer(int timeLimit)
  {
    startTime = System.nanoTime();
    nanoTimeLimit = (long)timeLimit * NANO_PER_SECOND * SECONDS_PER_MINUTE;
  }

  /**
   * Check to see if the phase has exceeded it's time limit.  Method checks
   * current time against the phase start time + the time limit.
   * @return        true if time is not exceeded, false if time is over.
   */
  public boolean phaseNotOver()
  {
    if(System.nanoTime() <= (startTime + nanoTimeLimit))
    {
      return true;
    }
    else
    {
      return false;
    }
  }
}
