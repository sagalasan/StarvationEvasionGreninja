package starvationevasion.greninja.gui.componentPane;

import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import starvationevasion.common.EnumFood;
import starvationevasion.greninja.model.State;
import java.util.HashMap;

/**
 * This class is used to create line chart.
 * @author Zhu Li
 */
public class RegionalStatistics extends LineChart<Number, Number>
{
  private static final int POINT_NUMBER = 5;

  //TODO: update how data points are displayed. e.g., do we get data of different turns or years?
  private int currentTurn = 4;
  private int firstPointTurn = currentTurn - POINT_NUMBER + 1;
  private  int startYear = 1980;

  private State region;
  private String dataType = null;
  private NumberAxis xAxis= null;
  private NumberAxis yAxis = null;
  private HashMap<String, XYChart.Series<Number, Number>> data = null;

  public RegionalStatistics(State region, String dataType)
  { super(new NumberAxis(), new NumberAxis());
    xAxis = (NumberAxis)getXAxis();
    yAxis = (NumberAxis)getYAxis();
    this.region = region;
    this.dataType = dataType;
    initializeChart();
  }

  public RegionalStatistics(State region)
  {
    super(new NumberAxis(), new NumberAxis());
    xAxis = (NumberAxis)getXAxis();
    yAxis = (NumberAxis)getYAxis();
    this.region = region;
    initializeFarmProductsChart();
    setMinHeight(520);
  }

  protected  void addDataToChart(EnumFood food)
  {
    getData().add(data.get(food.toString()));

  }

  protected void removeDataFromChart(EnumFood food)
  {
    getData().remove(data.get(food.toString()));
  }

  private void initializeAxis()
  {
    xAxis.setLabel("Year");
    yAxis.setLabel("Value");
    xAxis.setForceZeroInRange(false);
    yAxis.setForceZeroInRange(false);
    xAxis.setTickLabelFormatter(new StringConverter<Number>()
    {
      @Override
      public String toString(Number object)
      {
        return ((Integer)object.intValue()).toString();
      }

      @Override
      public Number fromString(String string)
      {
        return Integer.parseInt(string);
      }
    });
    yAxis.setAnimated(false);
    xAxis.setAnimated(false);
  }

  private void initializeChart()
  {
    initializeAxis();
    region.initializeDataForTest();
    setTitle(dataType);
    getData().add(getSeries(dataType));
    setAnimated(false);
  }

  private void initializeFarmProductsChart()
  {
    initializeAxis();
    data = new HashMap<>();
    region.initializeDataForTest();
    setTitle("Farm Products");
    for(EnumFood food : EnumFood.values())
    {
      data.put(food.toString(),getSeries(food));
    }
  }

  private XYChart.Series<Number, Number> getSeries(EnumFood food)
  {
    XYChart.Series<Number, Number> series = new XYChart.Series<>();
    series.setName(food.toString());
    for(int i = firstPointTurn; i < currentTurn; i++)
    { XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(startYear + 3 * i, region.getFoodIncome(food, i));
      dataPoint.setNode(new DataPoint((Double)dataPoint.getYValue()));
      series.getData().add(dataPoint);

    }
    return series;
  }

  private XYChart.Series<Number, Number> getSeries(String dataCategory)
  {
    XYChart.Series<Number, Number> series = new XYChart.Series<>();
    series.setName(dataCategory);

    switch(dataCategory)
    {
      case "Population":

        for(int i = firstPointTurn; i <= currentTurn; i++ )
        { XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(startYear + 3 * i, region.getPopulation(i));
          dataPoint.setNode(new DataPoint((Double)dataPoint.getYValue()));
          series.getData().add(dataPoint);
        }
        break;
      case "HDI":
        for(int i = firstPointTurn; i <= currentTurn; i++ )
        { XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(startYear + 3 * i, region.getHDI(i));
          dataPoint.setNode(new DataPoint((Double)dataPoint.getYValue()));
          series.getData().add(dataPoint);
        }

      case "RevenueBalance":
        for(int i = firstPointTurn; i <= currentTurn; i++)
        {
          XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(startYear + 3 * i, region.getRevenueBalance(i));
          dataPoint.setNode(new DataPoint((Double)dataPoint.getYValue()));
          series.getData().add(dataPoint);
        }
    }
    return series;
  }
  private class DataPoint extends StackPane
  {
    DataPoint(double productionValue)
    {
      setPrefSize(6, 6);
      String rawValue = "" + productionValue;
      int end = 5 > rawValue.length() ? rawValue.length() : 5;
      String shortValue = rawValue.substring(0, end);
      final Label label = new Label(shortValue);
      label.setStyle("-fx-text-fill: white; -fx-font-size: 8pt;");
      label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
      setOnMouseEntered(mouseEvent -> {
        getChildren().setAll(label);
        setCursor(Cursor.NONE);
        toFront();
      });
      setOnMouseExited(mouseEvent -> {
        getChildren().clear();
        setCursor(Cursor.CROSSHAIR);
      });
    }


  }
}

