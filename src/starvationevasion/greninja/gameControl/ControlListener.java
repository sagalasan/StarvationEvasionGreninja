package starvationevasion.greninja.gameControl;

import starvationevasion.common.EnumRegion;
import starvationevasion.common.messages.AvailableRegions;
import starvationevasion.greninja.clientCommon.EnumPhase;
import starvationevasion.greninja.gui.componentPane.TimerPane;
import starvationevasion.greninja.model.PlayerInterface;
import starvationevasion.greninja.model.State;

/**
 * This is an interface to be implemented by the GUI or AI's equivalent.
 */
public interface ControlListener
{
  public TimerPane getTimerPane(EnumPhase phase);
  public void initPlayerRegionInfo(State state, EnumRegion pRegion);
  public void updateAvailableRegions(AvailableRegions availableRegions, PlayerInterface player);
  public void swapToStagingPane();
  public void swapToPolicyPane();
  public void swapToVotingPane();
}
