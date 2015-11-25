package starvationevasion.greninja.gui.componentPane;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.StringConverter;
import starvationevasion.common.EnumRegion;
import starvationevasion.greninja.model.State;

/**
 * This class is used to create line chart.
 * @author Zhu Li
 */
public class ChartCreator
{
  private static final int POINT_NUMBER = 5;
  private State region = new State(EnumRegion.CALIFORNIA);

  public ChartCreator()
  {
    region.initializeDataForTest();
  }

  public LineChart<Number, Number> getChart(String dataType)
  {
    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
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
    LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
    chart.setTitle(dataType);
    chart.getData().add(getSeries(dataType));
    yAxis.setAnimated(false);
    chart.setLegendVisible(false);
    return chart;
  }

  private XYChart.Series<Number, Number> getSeries(String dataCategory)
  {
    //For test
    XYChart.Series<Number, Number> series = new XYChart.Series<>();
    int currentTurn = 4;
    int firstPointTurn = currentTurn - POINT_NUMBER + 1;
    int startYear = 1980;
    //for test
    switch(dataCategory)
    {
      case "Population":

        for(int i = firstPointTurn; i <= currentTurn; i++ )
        {
          series.getData().add(new XYChart.Data<Number, Number>(startYear + 3 * i, region.getPopulation(i)));
        }
        break;
      case "HDI":
        for(int i = firstPointTurn; i <= currentTurn; i++ )
        {
          series.getData().add(new XYChart.Data<Number, Number>(startYear + 3 * i, region.getHDI(i)));
        }
    }
    return series;
  }
}
