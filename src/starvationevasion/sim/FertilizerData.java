package starvationevasion.sim;


import starvationevasion.common.EnumFood;

import java.util.HashMap;


/**
 * Data structure for fertilizer data
 */
public class FertilizerData
{
  private HashMap<String, HashMap<EnumFood, Integer>> fertilizerData;

  public FertilizerData()
  {
    fertilizerData = new HashMap<>();
  }

  /**
   * Sets fertilizer data based on the region name
   *
   * @param region name of the region to set the fertilizer for
   * @param food type of food the fertilizer is for
   * @param value how much of the fertilizer is used
   */
  public void setFertilizerData(String region, EnumFood food, int value)
  {
    if (!fertilizerData.containsKey(region))
    {
      fertilizerData.put(region, new HashMap<>());
    }
    fertilizerData.get(region).put(food, value);
  }

  /**
   * Gets the fertilizer data for a given name of a region
   *
   * @param region name of a region
   * @param food type of food
   * @return amount of fertalizer for food in region
   */
  public int getFertilizerData(String region, EnumFood food)
  {
    int value = 0;
    if (fertilizerData.containsKey(region))
    {
      HashMap<EnumFood, Integer> regionData = fertilizerData.get(region);
      if (regionData.containsKey(food))
      {
        value = regionData.get(food);
      }
    }
    return value;
  }

  /**
   * Gets the total amount of fertilizer used in a region
   *
   * @param region name of region
   * @return total amount of fertilizer used
   */
  public int getFertilizerData(String region)
  {
    int total = 0;
    HashMap<EnumFood, Integer> regionData = fertilizerData.get(region);

    for (EnumFood food : regionData.keySet())
    {
      total += regionData.get(food);
    }

    return total;
  }

  @Override
  public String toString()
  {
    String string = "";
    for (String region : fertilizerData.keySet())
    {
      string += region + ":\n";
      for (EnumFood food : fertilizerData.get(region).keySet())
      {
        string += "\t" + food + ": " + fertilizerData.get(region).get(food) + "\n";
      }
    }
    return string;
  }
}
