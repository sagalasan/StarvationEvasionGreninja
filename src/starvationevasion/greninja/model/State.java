package starvationevasion.greninja.model;


import starvationevasion.common.EnumFood;
import starvationevasion.common.EnumRegion;

import java.util.Random;

/**
 * @author Zhu Li
 *
 * A data class that includes most data required for a state.
 */
public enum State
{
  CALIFORNIA, HEARTLAND, NORTHERN_PLAINS, SOUTHEAST, NORTHERN_CRESCENT,
  SOUTHERN_PLAINS, MOUNTAIN;

  public static final int FOOD_NUMBER = 12;

  private static final int TOTAL_TURN_NUMBER = 5;
  private int turnNumber = 0;
  private int startTurn;//TODO: This will be the index of data in the starting year.
  //private EnumRegion region;

  private double[] population = new double[TOTAL_TURN_NUMBER];
  private double[] averageAge = new double[TOTAL_TURN_NUMBER];
  private double[] undernourishedPopulation = new double[TOTAL_TURN_NUMBER];
  private double[] severelyMalnourishedPopulation = new double[TOTAL_TURN_NUMBER];
  private double[] birthRate = new double[TOTAL_TURN_NUMBER];//Total number of live births per 1000 of a population in a year.
  private double[] netMigrationRate = new double[TOTAL_TURN_NUMBER];//immigration - emigration per 1,000 population.
  private double[] mortalityRate = new double[TOTAL_TURN_NUMBER];//Units of deaths per 1,000 individuals per year.
  private double[] lifeExpectancy = new double[TOTAL_TURN_NUMBER];
  private double[] GMOPercentage = new double[TOTAL_TURN_NUMBER];
  private double[] OrganicPercentage = new double[TOTAL_TURN_NUMBER];
  private double[] ConventionalFarmingPercentage = new double[TOTAL_TURN_NUMBER];

  private double[][] foodIncome = new double[TOTAL_TURN_NUMBER][FOOD_NUMBER];
  private double[][] foodCost = new double[TOTAL_TURN_NUMBER][FOOD_NUMBER];
  private double[][] foodArea = new double[TOTAL_TURN_NUMBER][FOOD_NUMBER];
  private double[][] foodWeight = new double[TOTAL_TURN_NUMBER][FOOD_NUMBER];// In metric tons.
  private double[][] foodImport = new double[TOTAL_TURN_NUMBER][FOOD_NUMBER];
  private double[][] foodExport = new double[TOTAL_TURN_NUMBER][FOOD_NUMBER];

  private double[] HDI = new double[TOTAL_TURN_NUMBER];
//  public State(EnumRegion region)
//  {
//    this.region = region;
//  }


  public void update()
  {
    turnNumber++;
    //TODO: set data of all attributes for this turn. This could be done when we could get interpreted data from server
  }

  public void initializeDataForTest()
  {
    Random random = new Random();
    for(int i = 0; i < 5; i++)
    {
      population[i] = 10 + i * random.nextGaussian();
      HDI[i] = 0.8 + random.nextGaussian() * i / 10;
    }
  }


  public void setHDI(double HDI)
  {
    this.HDI[turnNumber] = HDI;
  }

  public double getHDI(int turnNumber)
  {
    return HDI[turnNumber];
  }

  public void setPopulation(double population)
  {
    this.population[turnNumber] = population;
  }

  public void setAverageAge(double averageAge)
  {
    this.averageAge[turnNumber] = averageAge;
  }

  public void setUndernourishedPopulation(double undernourishedPopulation)
  {
    this.undernourishedPopulation[turnNumber] = undernourishedPopulation;
  }

  public void setSeverelyMalnourishedPopulation(double severelyMalnourishedPopulation)
  {
    this.severelyMalnourishedPopulation[turnNumber] = severelyMalnourishedPopulation;
  }

  public void setBirthRate(double birthRate)
  {
    this.birthRate[turnNumber] = birthRate;
  }

  public void setNetMigrationRate(double netMigrationRate)
  {
    this.netMigrationRate[turnNumber] = netMigrationRate;
  }

  public void setMortalityRate(double mortalityRate)
  {
    this.mortalityRate[turnNumber] = mortalityRate;
  }

  public void setLifeExpectancy(double lifeExpectancy)
  {
    this.lifeExpectancy[turnNumber] = lifeExpectancy;
  }

  public void setOrganicPercentage(double organicPercentage)
  {
    OrganicPercentage[turnNumber] = organicPercentage;
  }

  public void setGMOPercentage(double GMOPercentage)
  {
    this.GMOPercentage[turnNumber] = GMOPercentage;
  }

  public void setConventionalFarmingPercentage(double conventionalFarmingPercentage)
  {
    ConventionalFarmingPercentage[turnNumber] = conventionalFarmingPercentage;
  }

  public double getPopulation(int turnNumber)
  {
    return population[turnNumber];
  }

  public double getAverageAge(int turnNumber)
  {
    return averageAge[turnNumber];
  }

  public double getUndernourishedPopulation(int turnNumber)
  {
    return undernourishedPopulation[turnNumber];
  }

  public double getSeverelyMalnourishedPopulation(int turnNumber)
  {
    return severelyMalnourishedPopulation[turnNumber];
  }

  public double getBirthRate(int turnNumber)
  {
    return birthRate[turnNumber];
  }

  public double getNetMigrationRate(int turnNumber)
  {
    return netMigrationRate[turnNumber];
  }

  public double getMortalityRate(int turnNumber)
  {
    return mortalityRate[turnNumber];
  }

  public double getLifeExpectancy(int turnNumber)
  {
    return lifeExpectancy[turnNumber];
  }

  public double getGMOPercentage(int turnNumber)
  {
    return GMOPercentage[turnNumber];
  }

  public double getOrganicPercentage(int turnNumber)
  {
    return OrganicPercentage[turnNumber];
  }

  public double getConventionalFarmingPercentage(int turnNumber)
  {
    return ConventionalFarmingPercentage[turnNumber];
  }

  public double getFoodIncome(EnumFood foodType, int turnNumber)
  {
    return foodIncome[turnNumber][foodType.ordinal()];
  }

  public void setFoodIncome(EnumFood foodType, double income, int turnNumber)
  {
    foodIncome[turnNumber][foodType.ordinal()] = income;
  }

  public double getFoodCost(EnumFood foodType, int turnNumber)
  {
    return foodCost[turnNumber][foodType.ordinal()];
  }

  public void setFoodCost(EnumFood foodType, double cost, int turnNumber)
  {
    foodCost[turnNumber][foodType.ordinal()] = cost;
  }

  public double getFoodWeight(EnumFood foodType, int turnNumber)
  {
    return foodWeight[turnNumber][foodType.ordinal()];
  }

  public void setFoodWeight(EnumFood foodType, double weight, int turnNumber)
  {
    foodWeight[turnNumber][foodType.ordinal()] = weight;
  }

  public double getFoodArea(EnumFood foodType, int turnNumber)
  {
    return foodArea[turnNumber][foodType.ordinal()];
  }

  public void setFoodArea(EnumFood foodType, double area, int turnNumber)
  {
    foodArea[turnNumber][foodType.ordinal()] = area;
  }

  public double getFoodImport(EnumFood foodType, int turnNumber)
  {
    return foodImport[turnNumber][foodType.ordinal()];
  }

  public void setFoodImport(EnumFood foodType, double importValue, int turnNumber)
  {
    foodImport[turnNumber][foodType.ordinal()] = importValue;
  }

  public double getFoodExport(EnumFood foodType, int turnNumber)
  {
    return foodExport[turnNumber][foodType.ordinal()];
  }

  public void setFoodExport(EnumFood foodType, double exportValue, int turnNumber)
  {
    foodExport[turnNumber][foodType.ordinal()] = exportValue;
  }

}
