package starvationevasion.greninja.gui.componentPane;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
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
    ProductCheckBox box1 = new ProductCheckBox("CITRUS");
    ProductCheckBox box2 = new ProductCheckBox("FRUIT");
    ProductCheckBox box3 = new ProductCheckBox("NUT");
    ProductCheckBox box4 = new ProductCheckBox("GRAIN");
    ProductCheckBox box5 = new ProductCheckBox("OIL");
    ProductCheckBox box6 = new ProductCheckBox("VEGGIES");
    ProductCheckBox box7 = new ProductCheckBox("SPECIAL");
    ProductCheckBox box8 = new ProductCheckBox("FEED");
    ProductCheckBox box9 = new ProductCheckBox("FISH");
    ProductCheckBox box10 = new ProductCheckBox("MEAT");
    ProductCheckBox box11 = new ProductCheckBox("POULTRY");
    ProductCheckBox box12 = new ProductCheckBox("DIARY");
    GridPane checkBoxPane = new GridPane();
    checkBoxPane.setVgap(3);
    checkBoxPane.setHgap(2);
    checkBoxPane.add(box1, 1, 1);
    checkBoxPane.add(box2, 2, 1);
    checkBoxPane.add(box3, 3, 1);
    checkBoxPane.add(box4, 1, 2);
    checkBoxPane.add(box5, 2, 2);
    checkBoxPane.add(box6, 3, 2);
    checkBoxPane.add(box7, 1, 3);
    checkBoxPane.add(box8, 2, 3);
    checkBoxPane.add(box9, 3, 3);
    checkBoxPane.add(box10, 1, 4);
    checkBoxPane.add(box11, 2, 4);
    checkBoxPane.add(box12, 3, 4);
    getChildren().add(checkBoxPane);
    checkBoxPane.setAlignment(Pos.CENTER);
    regionalStatistics = new RegionalStatistics(region);
    getChildren().add(regionalStatistics);
    box1.selectedProperty().setValue(true);
    box1.changeSelectedStatus(true);
  }

  private class ProductCheckBox extends CheckBox
  {
    private boolean isSelected = false;
    void changeSelectedStatus(boolean isSelected)
    {
      this.isSelected = isSelected;
    }

    ProductCheckBox(String name)
    {
      super(name);

      EnumFood foodName = getEnumValue(name);
      selectedProperty().addListener((observable, oldValue, newValue) -> {
          if (!oldValue && newValue)
          {
            regionalStatistics.addDataToChart(foodName);
          }
          else if(oldValue &&!newValue)
          {
            regionalStatistics.removeDataFromChart(foodName);
          }
      });
    }

    private EnumFood getEnumValue(String name)
    {
      switch (name)
      {
        case "FRUIT":
          return EnumFood.FRUIT;
        case "CITRUS":
          return EnumFood.CITRUS;
        case "NUT":
          return EnumFood.NUT;
        case "GRAIN":
          return EnumFood.GRAIN;
        case "OIL":
          return EnumFood.OIL;
        case "VEGGIES":
          return EnumFood.VEGGIES;
        case "SPECIAL":
          return EnumFood.SPECIAL;
        case "FEED":
          return EnumFood.FEED;
        case "FISH":
          return EnumFood.FISH;
        case "MEAT":
          return EnumFood.MEAT;
        case "POULTRY":
          return EnumFood.POULTRY;
        case "DIARY":
          return EnumFood.DAIRY;
      }
      throw new RuntimeException("Wrong food name!");
    }


  }
}

