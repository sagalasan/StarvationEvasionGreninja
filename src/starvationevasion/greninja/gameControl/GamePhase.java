package starvationevasion.greninja.gameControl;

/**
 * Parent class for the game phases.
 * General methods that both phases will use.
 */
public abstract class GamePhase
{
  public abstract void phaseOver();
  public abstract void updateViewTimer(int[] time);
}
