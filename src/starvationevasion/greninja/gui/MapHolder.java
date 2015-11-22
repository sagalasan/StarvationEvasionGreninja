package starvationevasion.greninja.gui;

import starvationevasion.common.EnumRegion;

/**
 * Interface for panes that hold an InteractiveMapPane.  Panes that hold an
 * InteractiveMapPane should implement this interface and register themselves
 * with the InteractiveMapPane via the setContainingPane(MapHolder holder) method.
 */
public interface MapHolder
{
  /**
   * Perform some action when an Interactive map region is clicked.
   * @param region        region on interactive map that was clicked.
   */
  public void regionClicked(EnumRegion region);

  /**
   * Method to use when the map's mouse listener gets a mouse entered event.
   * @param region        EnumRegion entered.
   */
  public void regionEntered(EnumRegion region);

  /**
   * Method to use when the map's mouse listener gets a mouse exited event.
   * @param region      EnumRegion exited.
   */
  public void regionExited(EnumRegion region);
}
