package starvationevasion.greninja.gui.componentPane;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import starvationevasion.common.EnumFood;
import starvationevasion.greninja.model.State;


/**
 * @author Zhu Li
 */
public class FarmProductChartPane extends VBox
{
  RegionalStatistics regionalStatistics;
  public FarmProductChartPane(State region)
  {
//    ToggleButton button1 = new ToggleButton("CITRUS");
//    ToggleButton button2 = new ToggleButton("FRUIT");
    CheckBox box1  = new CheckBox("CITRUS");
    CheckBox box2 = new CheckBox("FRUIT");
    ToolBar toolBar = new ToolBar(box1, box2);
    getChildren().add(toolBar);
    regionalStatistics = new RegionalStatistics(region);
    getChildren().add(regionalStatistics);
    box1.selectedProperty().setValue(true);
    box1.selectedProperty().addListener((observable, oldValue, newValue) -> {
      if(oldValue == false)
      {
        regionalStatistics.addDataToChart(EnumFood.CITRUS);
      }
      else
      {
        regionalStatistics.removeDataFromChart(EnumFood.CITRUS);
      }
    });
    box2.selectedProperty().addListener((observable, oldValue, newValue) -> {
      if(oldValue == false)
      {
        regionalStatistics.addDataToChart(EnumFood.FRUIT);
      }
      else
      {
        regionalStatistics.removeDataFromChart(EnumFood.FRUIT);
      }
    });
  }
}