package starvationevasion.greninja.util;

import starvationevasion.greninja.gameControl.GamePhase;
import starvationevasion.greninja.gui.componentPane.TimerPane;

/**
 * Timer to control game phases.  Game Timer is based on system clock.  The
 * javafx timers timers all appear to be tied to the framerate, which may cause
 * time mismatches.
 */
public class PhaseTimer extends Thread
{
  private static final long NANO_PER_SECOND = 1000000000;
  private static final int SECONDS_PER_MINUTE = 60;

  private long startTime;
  private long endTime;
  private GamePhase phase;
  private TimerPane timerVisualization;
  private int[] timeRemaining;

  /**
   * Construct at beginning of phase.  Logs current system time and calculates
   * the time limit.
   * @param timeLimit       Time limit for phase in minutes.
   */
  public PhaseTimer(int timeLimit, GamePhase phase, TimerPane visibleTimer)
  {
    startTime = System.nanoTime();
    long nanoTimeLimit = (long)timeLimit * NANO_PER_SECOND * SECONDS_PER_MINUTE;
    endTime = startTime + nanoTimeLimit;
    this.phase = phase;
    timeRemaining = new int[2];
    timeRemaining[0] = timeLimit;
    timeRemaining[1] = 0;
    timerVisualization = visibleTimer;
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
  @Override
  public void run()
  {
    while(phaseNotOver() && phase != null)
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
      updateRemainingTime();
      if(timerVisualization != null)//if not ai game.
      {
        timerVisualization.updateTimeRemaining(makeTimeString());
      }
    }
    if(phase != null)
    {
      phase.phaseOver();
    }
  }

  /**
   * Return array with current time remaining.
   * @return        int array of size two representing minutes and seconds left.
   */
  private void updateRemainingTime()
  {
    if(timeRemaining[1] <= 0)
    {
      timeRemaining[1] = 59;
      timeRemaining[0]--;

    }
    else
    {
      timeRemaining[1]--;
    }
  }

  /**
   * Convert timeRemaining into string.
   * @return        string representation of time. "m:ss"
   */
  private String makeTimeString()
  {
    if(timeRemaining[0] < 2)//check and update color.
    {
      timerVisualization.setTimerColor(timeRemaining[0]);
    }
    StringBuilder timeString = new StringBuilder();
    timeString.append(timeRemaining[0]);
    timeString.append(':');

    if (timeRemaining[1] < 10)
    {
      timeString.append(0);
    }
    timeString.append(timeRemaining[1]);
    return timeString.toString();
  }
}
