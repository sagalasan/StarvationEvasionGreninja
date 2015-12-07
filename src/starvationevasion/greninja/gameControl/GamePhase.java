package starvationevasion.greninja.gameControl;

/**
 * Parent class for the game phases.
 * General methods that both phases will use.
 */
public abstract class GamePhase
{
  /**
   * End of phase clean up.
   */
  public abstract void phaseOver();
}
