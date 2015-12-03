package starvationevasion.greninja.gui.componentPane;

import javafx.scene.control.CheckBox;
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
    ToolBar toolBar = new ToolBar(box1, box2, box3, box4, box5, box6,
      box7, box8, box9, box10, box11, box12);
    getChildren().add(toolBar);
    regionalStatistics = new RegionalStatistics(region);
    getChildren().add(regionalStatistics);
    box1.selectedProperty().setValue(true);
//    box1.selectedProperty().addListener((observable, oldValue, newValue) -> {
//      if(oldValue == false)
//      {
//        regionalStatistics.addDataToChart(EnumFood.CITRUS);
//      }
//      else
//      {
//        regionalStatistics.removeDataFromChart(EnumFood.CITRUS);
//      }
//    });
//    box2.selectedProperty().addListener((observable, oldValue, newValue) -> {
//      if(oldValue == false)
//      {
//        regionalStatistics.addDataToChart(EnumFood.FRUIT);
//      }
//      else
//      {
//        regionalStatistics.removeDataFromChart(EnumFood.FRUIT);
//      }
//    });
  }

  private class ProductCheckBox extends CheckBox
  {
    ProductCheckBox(String name)
    {
      super(name);
      EnumFood foodName = getEnumValue(name);
      selectedProperty().addListener((observable, oldValue, newValue) -> {
        if(!oldValue)
        {
          regionalStatistics.addDataToChart(foodName);
        }
        else
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

