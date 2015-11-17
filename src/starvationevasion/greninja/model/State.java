package starvationevasion.greninja.model;


import starvationevasion.common.EnumFood;
import starvationevasion.common.EnumRegion;

/**
 * @author Zhu Li
 *
 * A data class that includes most data required for a state.
 */
public class State
{


  public static final int FOOD_NUMBER = 12;

  private EnumRegion region;

  private double population;
  private double averageAge;
  private double undernourishedPopulation;
  private double severelyMalnourishedPopulation;
  private double birthRate;//Total number of live births per 1000 of a population in a year.
  private double netMigrationRate;//immigration - emigration per 1,000 population.
  private double mortalityRate;//Units of deaths per 1,000 individuals per year.
  private double lifeExpectancy;
  private double GMOPercentage;
  private double OrganicPercentage;
  private double ConventionalFarmingPercentage;

  private double[] foodIncome = new double[FOOD_NUMBER];
  private double[] foodCost = new double[FOOD_NUMBER];
  private double[] foodArea = new double[FOOD_NUMBER];
  private double[] foodWeight = new double[FOOD_NUMBER];// In metric tons.
  private double[] foodImport = new double[FOOD_NUMBER];
  private double[] foodExport = new double[FOOD_NUMBER];

  public State(EnumRegion region)
  {
    this.region = region;
  }

  public void setPopulation(double population)
  {
    this.population = population;
  }

  public void setAverageAge(double averageAge)
  {
    this.averageAge = averageAge;
  }

  public void setUndernourishedPopulation(double undernourishedPopulation)
  {
    this.undernourishedPopulation = undernourishedPopulation;
  }

  public void setSeverelyMalnourishedPopulation(double severelyMalnourishedPopulation)
  {
    this.severelyMalnourishedPopulation = severelyMalnourishedPopulation;
  }

  public void setBirthRate(double birthRate)
  {
    this.birthRate = birthRate;
  }

  public void setNetMigrationRate(double netMigrationRate)
  {
    this.netMigrationRate = netMigrationRate;
  }

  public void setMortalityRate(double mortalityRate)
  {
    this.mortalityRate = mortalityRate;
  }

  public void setLifeExpectancy(double lifeExpectancy)
  {
    this.lifeExpectancy = lifeExpectancy;
  }

  public void setOrganicPercentage(double organicPercentage)
  {
    OrganicPercentage = organicPercentage;
  }

  public void setGMOPercentage(double GMOPercentage)
  {
    this.GMOPercentage = GMOPercentage;
  }

  public void setConventionalFarmingPercentage(double conventionalFarmingPercentage)
  {
    ConventionalFarmingPercentage = conventionalFarmingPercentage;
  }

  public double getPopulation()
  {

    return population;
  }

  public double getAverageAge()
  {
    return averageAge;
  }

  public double getUndernourishedPopulation()
  {
    return undernourishedPopulation;
  }

  public double getSeverelyMalnourishedPopulation()
  {
    return severelyMalnourishedPopulation;
  }

  public double getBirthRate()
  {
    return birthRate;
  }

  public double getNetMigrationRate()
  {
    return netMigrationRate;
  }

  public double getMortalityRate()
  {
    return mortalityRate;
  }

  public double getLifeExpectancy()
  {
    return lifeExpectancy;
  }

  public double getGMOPercentage()
  {
    return GMOPercentage;
  }

  public double getOrganicPercentage()
  {
    return OrganicPercentage;
  }

  public double getConventionalFarmingPercentage()
  {
    return ConventionalFarmingPercentage;
  }

  public double getFoodIncome(EnumFood foodType)
  {
    return foodIncome[foodType.ordinal()];
  }

  public void setFoodIncome(EnumFood foodType, double income)
  {
    foodIncome[foodType.ordinal()] = income;
  }

  public double getFoodCost(EnumFood foodType)
  {
    return foodCost[foodType.ordinal()];
  }

  public void setFoodCost(EnumFood foodType, double cost)
  {
    foodCost[foodType.ordinal()] = cost;
  }

  public double getFoodWeight(EnumFood foodType)
  {
    return foodWeight[foodType.ordinal()];
  }

  public void setFoodWeight(EnumFood foodType, double weight)
  {
    foodWeight[foodType.ordinal()] = weight;
  }

  public double getFoodArea(EnumFood foodType)
  {
    return foodArea[foodType.ordinal()];
  }

  public void setFoodArea(EnumFood foodType, double area)
  {
    foodArea[foodType.ordinal()] = area;
  }

  public double getFoodImport(EnumFood foodType)
  {
    return foodImport[foodType.ordinal()];
  }

  public void setFoodImport(EnumFood foodType, double importValue )
  {
    foodImport[foodType.ordinal()] = importValue;
  }

  public double getFoodExport(EnumFood foodType)
  {
    return foodExport[foodType.ordinal()];
  }

  public void setFoodExport(EnumFood foodType, double exportValue)
  {
    foodExport[foodType.ordinal()] = exportValue;
  }

}
