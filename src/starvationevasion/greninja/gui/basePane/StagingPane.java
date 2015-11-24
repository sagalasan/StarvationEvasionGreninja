package starvationevasion.greninja.gui.basePane;


import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import starvationevasion.common.EnumRegion;
import javafx.scene.control.Label;
import starvationevasion.common.messages.AvailableRegions;
import starvationevasion.greninja.gui.GuiBase;
import starvationevasion.greninja.gui.GuiTimerSubscriber;
import starvationevasion.greninja.gui.MapHolder;
import starvationevasion.greninja.gui.componentPane.InteractiveMapPane;

import javax.smartcardio.Card;
import java.util.Map;
import java.util.Set;

/**
 * Select region, wait for other players to join.
 * TODO Just testing right now, finalize.
 * @author Justin Thomas(jthomas105@unm.edu)
 */
public class StagingPane extends VBox implements MapHolder, GuiTimerSubscriber
{
  private GuiBase base;
  private String selectedRegion;
  private Label regionSelectedLabel;
  private InteractiveMapPane map;
  private AvailableRegions availableRegions;
  //private CardController card;


  public StagingPane(GuiBase gui)
  {
    base = gui;
    availableRegions = null;
  }

  /**
   * Setup Staging Pane.
   */
  public void initPane()
  {
    getChildren().add(new Label("Staging Pane, select a region."));
    selectedRegion = "None.";
    regionSelectedLabel = new Label(selectedRegion);
    getChildren().add(regionSelectedLabel);
    map = new InteractiveMapPane();
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
    selectedRegion = "None.";
  }

  public void setAvailableRegions(AvailableRegions availableRegions)
  {
    this.availableRegions = availableRegions;
  }
  /**
   * Implements GuiTimerSubscriber.  Update text on region label.
   */
  public void timerTick()
  {
    regionSelectedLabel.setText(selectedRegion);
    if(availableRegions != null)
    {
      map.updateAvailableRegions(availableRegions);
    }
  }
}
