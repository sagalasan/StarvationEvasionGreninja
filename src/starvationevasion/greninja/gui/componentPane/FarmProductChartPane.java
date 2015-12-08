package starvationevasion.greninja.gui.componentPane;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import starvationevasion.common.EnumFood;
import starvationevasion.greninja.model.State;

/**
 * @author Zhu Li
 */
public class FarmProductChartPane extends VBox
{
  private RegionalStatistics regionalStatistics;
  private ProductCheckBox[] boxes = new ProductCheckBox[12];
  private boolean isDollars = true;

  /**
   *
   * @param region The State class of this region.
   */
  public FarmProductChartPane(State region)
  {
    for (int i = 0; i < 12; i++)
    {
      boxes[i] = new ProductCheckBox(EnumFood.values()[i]);
    }
    GridPane checkBoxPane = new GridPane();
    checkBoxPane.setVgap(3);
    checkBoxPane.setHgap(2);
    checkBoxPane.add(boxes[0], 1, 1);
    checkBoxPane.add(boxes[1], 2, 1);
    checkBoxPane.add(boxes[2], 1, 2);
    checkBoxPane.add(boxes[3], 2, 2);
    checkBoxPane.add(boxes[4], 1, 3);
    checkBoxPane.add(boxes[5], 2, 3);
    checkBoxPane.add(boxes[6], 1, 4);
    checkBoxPane.add(boxes[7], 2, 4);
    checkBoxPane.add(boxes[8], 1, 5);
    checkBoxPane.add(boxes[9], 2, 5);
    checkBoxPane.add(boxes[10], 1, 6);
    checkBoxPane.add(boxes[11], 2, 6);
    getChildren().add(checkBoxPane);
    checkBoxPane.setAlignment(Pos.CENTER);

    regionalStatistics = new RegionalStatistics(region);
    getChildren().add(regionalStatistics);

    boxes[0].selectedProperty().setValue(true);

    HBox choseMode = new HBox();
    Button dollars = new Button("Dollars");
    Button tons = new Button("Tons");
    dollars.setId("dollars");
    tons.setId("tons");
    dollars.setStyle("-fx-background-color: #0066FF");
    tons.setStyle("-fx-background-color: #33333");
    choseMode.getChildren().addAll(dollars, tons);
    dollars.setOnMouseClicked(event -> {
      if(!isDollars)
      {
        dollars.setStyle("-fx-background-color: #0066FF");
        tons.setStyle("-fx-background-color: #33333");
        isDollars = true;
        regionalStatistics.yAxisLabelChange(true);
        for(int i = 0; i < 12; i++)
        {
          if(boxes[i].selectedProperty().getValue()) regionalStatistics.removeDataFromChart(EnumFood.values()[i]);
        }
        regionalStatistics.setMode(false);
        for(int i = 0; i < 12; i++)
        {
          if(boxes[i].selectedProperty().getValue()) regionalStatistics.addDataToChart(EnumFood.values()[i]);
        }
      }
    });
    tons.setOnMouseClicked(event -> {
      if(isDollars)
      {
        isDollars = false;
        regionalStatistics.yAxisLabelChange(false);
        dollars.setStyle("-fx-background-color: #33333");
        tons.setStyle("-fx-background-color: #0066FF");
        for(int i = 0; i < 12; i++)

        {
          if(boxes[i].selectedProperty().getValue()) regionalStatistics.removeDataFromChart(EnumFood.values()[i]);
        }
        regionalStatistics.setMode(true);
        for(int i = 0; i < 12; i++)
        {
          if(boxes[i].selectedProperty().getValue()) regionalStatistics.addDataToChart(EnumFood.values()[i]);
        }
      }
    });
    getChildren().add(choseMode);
    choseMode.setAlignment(Pos.CENTER);
  }

  private class ProductCheckBox extends CheckBox
  {

    ProductCheckBox(EnumFood name)
    {
      super(name.toString());
      selectedProperty().addListener((observable, oldValue, newValue) -> {
          if (!oldValue && newValue)
          {
            regionalStatistics.addDataToChart(name);
          }
          else if(oldValue &&!newValue)
          {
            regionalStatistics.removeDataFromChart(name);
          }
      });
    }

  }
}

