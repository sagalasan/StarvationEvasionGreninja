package starvationevasion.greninja.gui;

/**
 * Interface for panes that need to update components on the javafx thread.
 * Implementing classes should subscribe to the timer when they become active
 * and unsubscribe when they become inactive.
 */
public interface GuiTimerSubscriber
{
  /**
   * Actions to perform on update.
   */
  public void timerTick();
}
