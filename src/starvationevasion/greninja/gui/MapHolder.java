package starvationevasion.greninja.gui;

import starvationevasion.common.EnumRegion;

/**
 * Interface for panes that hold an interactive map.
 */
public interface MapHolder
{
  /**
   * Perform some action when an Interactive map region is clicked.
   * @param region        region on interactive map that was clicked.
   */
  public void regionClicked(EnumRegion region);
}
