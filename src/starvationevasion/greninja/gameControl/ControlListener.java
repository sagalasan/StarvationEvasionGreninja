package starvationevasion.greninja.gameControl;

import starvationevasion.common.EnumPolicy;
import starvationevasion.common.EnumRegion;
import starvationevasion.common.messages.AvailableRegions;
import starvationevasion.greninja.clientCommon.EnumPhase;
import starvationevasion.greninja.gui.componentPane.TimerPane;
import starvationevasion.greninja.model.PlayerInterface;
import starvationevasion.greninja.model.State;

/**
 * This is an interface to be implemented by the GUI or AI's equivalent.
 * @author Justin Thomas
 */
public interface ControlListener
{
  /**
   * Get timerpane for ai returns null as of now.  Will be cleaned out.
   * @param phase the current phase.
   * @return
   */
  TimerPane getTimerPane(EnumPhase phase);

  /**
   * Inform view of player region, not entirely necessary.
   * @param state
   * @param pRegion
   */
  void initPlayerRegionInfo(State state, EnumRegion pRegion);

  /**
   * Update availableRegions message.
   * @param availableRegions AvailableRegions method.
   */
  void updateAvailableRegions(AvailableRegions availableRegions, PlayerInterface player);

  /**
   * Inform AI that staging phase has started.  Does not need to do anything,
   * server will inform AI of region assignment.
   */
  void swapToStagingPane();

  /**
   * Inform AI that it is policy drafting phase.  When control calls the AI's
   * "GUI" it will prompt AIDecisions to do stuff.
   */
  void swapToPolicyPane();

  /**
   * Inform AI that it is voting phase.  When control calls the AI's "GUI" it
   * will prompt AIDecisions to do stuff.
   */
  void swapToVotingPane();

  /**
   * Send string chat message to control to pass on to server coms.
   * @param message       String message.
   * @param destination   destination regions
   */
  void sendChatMessage(String message, EnumRegion[] destination);

  /**
   * Send card info chat message to control to pass on to server coms.
   * @param card        card message.
   * @param destination destination regions
   */
  void sendChatMessage(EnumPolicy card, EnumRegion[] destination);

  /**
   * Inform view that there has been a game state update.  If it is drafting phase
   * and cards have not been drafted, do the drafting.
   */
  void gameStateUpdate();
}
