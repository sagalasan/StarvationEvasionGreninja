package starvationevasion.greninja.gui.basePane;


import javafx.scene.layout.VBox;
import starvationevasion.common.EnumRegion;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import starvationevasion.greninja.gui.GuiBase;
import starvationevasion.greninja.gui.componentPane.InteractiveMapPane;

import java.util.HashMap;

/**
 * Select region, wait for other players to join.
 * TODO Just testing right now, finalize.
 * @author Justin Thomas(jthomas105@unm.edu)
 */
public class StagingPane extends VBox
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
    HBox buttons = new HBox();
    getChildren().add(new Label("Staging Pane, select a region."));
    ButtonControl buttonListener = new ButtonControl(this);
    regionButtons = new HashMap<>();
    EnumRegion region;
    Button b;
    for(int i = 0; i < 7; ++i)
    {
      region = EnumRegion.values()[i];
      b = new Button(region.toString());
      b.setOnAction(buttonListener);
      buttons.getChildren().add(b);
      regionButtons.put(b, region);
    }
    getChildren().add(buttons);
    getChildren().add(new InteractiveMapPane());
  }

  /**
   * Pass selected region to gui
   * @param e        Button pressed.
   */
  public void selectRegion(ActionEvent e)
  {
    Button b = (Button)e.getSource();
    base.regionSelected(regionButtons.get(b));
  }

  private class ButtonControl implements EventHandler<ActionEvent>
  {
    private StagingPane parentPane;

    public ButtonControl(StagingPane parentPane)
    {
      this.parentPane = parentPane;
    }

    @Override
    public void handle (ActionEvent e)
    {
      parentPane.selectRegion(e);
    }
  }
}
