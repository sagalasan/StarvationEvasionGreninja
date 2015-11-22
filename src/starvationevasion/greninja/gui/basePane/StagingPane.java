package starvationevasion.greninja.gui.basePane;


import javafx.scene.layout.VBox;
import starvationevasion.common.EnumRegion;
import javafx.scene.control.Label;
import starvationevasion.greninja.gui.GuiBase;
import starvationevasion.greninja.gui.MapHolder;
import starvationevasion.greninja.gui.componentPane.InteractiveMapPane;

/**
 * Select region, wait for other players to join.
 * TODO Just testing right now, finalize.
 * @author Justin Thomas(jthomas105@unm.edu)
 */
public class StagingPane extends VBox implements MapHolder
{
  private GuiBase base;
  private String selectedRegion;
  private Label regionSelectedLabel;


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
    selectedRegion = " ";
    regionSelectedLabel = new Label(selectedRegion);
    getChildren().add(regionSelectedLabel);
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

  /**
   * When mouse enters a region on the interactive map, update the region selected
   * string.
   * @param region        EnumRegion entered.
   */
  public void regionEntered(EnumRegion region)
  {
    selectedRegion = region.toString();
  }

  /**
   * When mouse exits region, clear region selected string.
   * @param region        region exited.  Not used.
   */
  public void regionExited(EnumRegion region)
  {
    selectedRegion = " ";
  }
}
