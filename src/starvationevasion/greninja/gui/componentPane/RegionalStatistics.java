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
public class RegionalStatistics extends LineChart<Number, Number>
{
  private static final int POINT_NUMBER = 5;
  private State region;
  private String dataType = null;
  NumberAxis xAxis= null;
  NumberAxis yAxis = null;
  public RegionalStatistics(State region, String dataType)
  { super(new NumberAxis(), new NumberAxis());
    xAxis = (NumberAxis)getXAxis();
    yAxis = (NumberAxis)getYAxis();
    this.region = region;
    this.dataType = dataType;
    initializeChart();
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
    yAxis.setAnimated(true);
    xAxis.setAnimated(true);
  }

  private void initializeChart()
  {
    initializeAxis();
    region.initializeDataForTest();
    setTitle(dataType);
    getData().add(getSeries(dataType));
    setLegendVisible(false);
  }

//  public LineChart<Number, Number> getChart(String dataType)
//  {
//    XYChart.Series<Number, Number> series = getSeries(dataType);
//    chart.getData().add(series);
//
//    chart.setLegendVisible(false);
//    if(dataType == "HDI")
//    {
//      addSeries("SS");
//      chart.getData().remove(series);
//    }
//    return chart;
//  }
//
//  void addSeries(String dataCategory)
//  {
//    chart.getData().add(getSeries("Population"));
//  }

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
