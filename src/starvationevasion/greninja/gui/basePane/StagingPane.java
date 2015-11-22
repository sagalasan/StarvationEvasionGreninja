package starvationevasion.greninja.gui.basePane;


import javafx.scene.layout.VBox;
import starvationevasion.common.EnumRegion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import starvationevasion.greninja.gui.GuiBase;
import starvationevasion.greninja.gui.MapHolder;
import starvationevasion.greninja.gui.componentPane.InteractiveMapPane;

import java.util.HashMap;

/**
 * Select region, wait for other players to join.
 * TODO Just testing right now, finalize.
 * @author Justin Thomas(jthomas105@unm.edu)
 */
public class StagingPane extends VBox implements MapHolder
{
  private GuiBase base;
  HashMap<Button, EnumRegion> regionButtons;


  public StagingPane(GuiBase gui)
  {
    base = gui;
  }

  /**
   * Setup Staging Pane.
   */
  public void initPane()
  {
    getChildren().add(new Label("Staging Pane, select a region."));

    InteractiveMapPane map = new InteractiveMapPane();
    map.setContainingPane(this);
    getChildren().add(map);
  }

  /**
   * When interactive map is clicked, set region selected to that region.
   * @param region        region on interactive map that was clicked.
   */
  public void regionClicked(EnumRegion region)
  {
    base.regionSelected(region);
  }
}
