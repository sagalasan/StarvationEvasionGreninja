package starvationevasion.greninja.gui.componentPane;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.SVGPath;
import starvationevasion.common.EnumRegion;

import java.util.HashMap;

/**
 * Region polygons for click detection on interactive map.  The polygons can be
 * taken as a Group laid out as a map of the US, or called individually via
 * the EnumRegion name.
 * also will experiment with svg.
 * TODO Make click listener.
 * TODO setup highlight/glow method for selected.
 * TODO setup with image?
 * TODO make smaller?
 */
public class RegionPaths
{
  private HashMap<EnumRegion, SVGPath> usRegions;
  private Group regionGroup;
  private SVGPath California, Mountain, NorthPlains, SouthPlains,
                        Heartland, Crescent, SouthEast;
  public RegionPaths()
  {
    buildRegionPolygons();
    usRegions = new HashMap<>();
    populateHashMap();
    //setTransparent();
  }

  public SVGPath getRegionPolygonByName(EnumRegion region)
  {
    return usRegions.get(region);
  }

  /**
   * Get group map of regions.
   * @return        Group of region polygons.
   */
  public Group getUSRegionMap()
  {
    return regionGroup;
  }

  /**
   * Create Region Group map.
   * @return Group with all SVG paths.
   */
  private Group createRegionMap()
  {
    Group g = new Group();
    for(SVGPath polygon : usRegions.values())
    {
      g.getChildren().add(polygon);
    }
    return g;//
  }

  /**
   * Instantiate region polygons.
   */
  private void buildRegionPolygons()
  {
    California = new SVGPath();
    California.setContent("m 322.14002,755.2327 -12.00591,26.26295 -27.76369,-5.25258" +
        " -45.92373,-48.4362 -14.85623,-53.61411 6.75333,-64.53182 46.52293,12.7563" +
        " -12.75629,40.51997 z");

    Mountain = new SVGPath();
    Mountain.setContent("m 229.84453,605.15873 24.7622,-105.05179" +
        " 220.49614,35.26739 -6.00296,275.91088 -108.69102,1.72598" +
        " -47.16069,-29.15181 14.14442,-29.37704 -60.77997,-91.54513" +
        " 15.57048,-42.77109 z");

    NorthPlains = new SVGPath();
    NorthPlains.setContent("m 474.46511,727.46902 6.00296,-193.59543" +
        " 100.54957,3.75185 45.02219,16.50813 -1.50074,63.55621" +
        " -68.28366,0.63775 26.26295,110.64223 z");

    SouthPlains = new SVGPath();
    SouthPlains.setContent("m 474.46511,731.22087 -0.75037,84.04143" +
        " -43.52145,0 37.5185,49.52441 18.75924,-17.2585 42.02071,59.27924" +
        " 24.76221,8.25402 0,-30.76514 45.77257,-30.76517 68.28366,3.00148" +
        " -4.50222,-27.76368 -27.76369,-1.50075 18.00888,-91.73241 z");

    Crescent = new SVGPath();
    Crescent.setContent("m 629.04131,618.66538 2.58896,-64.41918" +
        " 248.03461,-36.13038 22.51105,33.76664 -74.2866,136.56733" +
        " -112.55548,-0.75038 -3.75185,-45.02219 z");

    Heartland = new SVGPath();
    Heartland.setContent("m 586.49546,727.24379 -24.98744,-103.32582" +
        " 66.78292,-1.50073 80.28958,24.7622 1.16289,42.99632 -11.66806,19.05915" +
        " -21.76073,3.22671 -19.84747,18.00888 z");

    SouthEast = new SVGPath();
    SouthEast.setContent("m 639.54649,823.51637 18.75925,-89.29402" +
        " 21.01036,-18.00888 21.01035,-2.25111 14.25703,-21.76073" +
        " 108.05326,-0.75036 16.50813,39.7696 -63.78143,87.79328" +
        " 36.76813,75.03701 -8.25407,14.25702 -20.25999,-9.75481" +
        " -41.27034,-60.02961 -74.28662,-2.2511 -3.75185,-12.75629 z");

    /*
    California = new Polygon();
    California.getPoints().addAll(new Double[]{8.00,100.00, 46.00,111.00, 46.00,111.00,
        46.00,111.00, 34.00,145.00, 34.00,145.00,
        34.00,145.00, 87.00,225.00, 87.00,225.00,
        87.00,225.00, 78.00,246.00, 78.00,246.00,
        78.00,246.00, 54.00,241.00, 54.00,241.00,
        54.00,241.00,16.00,200.00, 16.00,200.00,
        16.00,200.00, 4.00,149.00, 4.00,149.00});

    Mountain = new Polygon();
    Mountain.getPoints().addAll(new Double[]{33.00,5.00, 220.00,36.00, 220.00,36.00,
        220.00,36.00, 216.00,273.00, 216.00,273.00,
        216.00,273.00, 123.00,275.00, 123.00,275.00,
        123.00,275.00, 86.00,252.00, 86.00,252.00,
        86.00,252.00, 97.00,226.00, 97.00,226.00,
        97.00,226.00, 44.00,145.00, 44.00,145.00,
        44.00,145.00, 57.00,105.00, 57.00,105.00,
        57.00,105.00, 10.00,91.00, 10.00,91.00});

    NorthPlains = new Polygon();
    NorthPlains.getPoints().addAll(new Double[]{230.00,33.00, 315.00,35.00, 315.00,35.00,
        315.00,35.00, 355.00,51.00, 355.00,51.00,
        355.00,51.00, 353.00,101.00, 353.00,101.00,
        353.00,101.00, 294.00,104.00, 294.00,104.00,
        294.00,104.00, 316.00,200.00, 316.00,200.00,
        316.00,200.00, 225.00,200.00, 225.00,200.00});

    SouthPlains = new Polygon();
    SouthPlains.getPoints().addAll(new Double[]{226.00,208.00, 376.00,212.00, 376.00,212.00,
        376.00,212.00, 360.00,291.00, 360.00,291.00,
        360.00,291.00, 388.00,294.00, 388.00,294.00,
        388.00,294.00, 390.00,311.00, 390.00,311.00,
        390.00,311.00, 331.00,309.00, 331.00,309.00,
        331.00,309.00, 289.00,338.00, 289.00,338.00,
        289.00,338.00, 290.00,364.00, 290.00,364.00,
        290.00,364.00, 271.00,357.00, 271.00,357.00,
        271.00,357.00, 233.00,305.00, 233.00,305.00,
        233.00,305.00, 217.00,318.00, 217.00,318.00,
        217.00,318.00, 188.00,281.00, 188.00,281.00,
        188.00,281.00, 224.00,281.00, 224.00,281.00});

    Heartland = new Polygon();
    Heartland.getPoints().addAll(new Double[]{303.00,113.00, 355.00,112.00, 355.00,112.00,
        355.00,112.00, 425.00,133.00, 425.00,133.00,
        425.00,133.00, 429.00,167.00, 429.00,167.00,
        429.00,167.00, 417.00,183.00, 417.00,183.00,
        417.00,183.00, 398.00,184.00, 398.00,184.00,
        398.00,184.00, 382.00,201.00, 382.00,201.00,
        382.00,201.00, 324.00,199.00, 324.00,199.00});

    Crescent = new Polygon();
    Crescent.getPoints().addAll(new Double[]{363.00,52.00, 577.00,19.00, 577.00,19.00,
        577.00,19.00, 594.00,46.00, 594.00,46.00,
        594.00,46.00, 532.00,164.00, 532.00,164.00,
        532.00,164.00, 436.00,165.00, 436.00,165.00,
        436.00,165.00, 433.00,127.00, 433.00,127.00,
        433.00,127.00, 361.00,104.00, 361.00,104.00});

    SouthEast = new Polygon();
    SouthEast.getPoints().addAll(new Double[]{435.00,173.00, 526.00,174.00, 526.00,174.00,
        526.00,174.00, 539.00,204.00, 539.00,204.00,
        539.00,204.00, 484.00,282.00, 484.00,282.00,
        484.00,282.00, 515.00,348.00, 515.00,348.00,
        515.00,348.00, 509.00,358.00, 509.00,358.00,
        509.00,358.00, 492.00,349.00, 492.00,349.00,
        492.00,349.00, 461.00,298.00, 461.00,298.00,
        461.00,298.00, 394.00,295.00, 394.00,295.00,
        394.00,295.00, 390.00,284.00, 390.00,284.00,
        390.00,284.00, 371.00,285.00, 371.00,285.00,
        371.00,285.00, 386.00,211.00, 386.00,211.00,
        386.00,211.00, 403.00,193.00, 403.00,193.00,
        403.00,193.00, 425.00,193.00, 425.00,193.00});
        */
  }

  /**
   * Scale map by specified amount
   * @param scale       double to scale by.
   */
  public void scaleMap(double scale)
  {
      regionGroup.setScaleX(scale);
      regionGroup.setScaleY(scale);
      regionGroup.setScaleZ(scale);
  }

  /**
   * Scale individual region
   * @param region          EnumRegion to scale.
   * @param scale           double to scale by.
   */
  public void scaleRegion(EnumRegion region, double scale)
  {
    SVGPath regionPath;
    regionPath = usRegions.get(region);
    regionPath.setScaleX(scale);
    regionPath.setScaleY(scale);
    regionPath.setScaleZ(scale);
  }

  /**
   * Populate hashmap of EnumRegions to region polygons.
   */
  private void populateHashMap()
  {
    usRegions.put(EnumRegion.CALIFORNIA, California);
    usRegions.put(EnumRegion.MOUNTAIN, Mountain);
    usRegions.put(EnumRegion.NORTHERN_PLAINS, NorthPlains);
    usRegions.put(EnumRegion.SOUTHERN_PLAINS, SouthPlains);
    usRegions.put(EnumRegion.HEARTLAND, Heartland);
    usRegions.put(EnumRegion.SOUTHEAST, SouthEast);
    usRegions.put(EnumRegion.NORTHERN_CRESCENT, Crescent);
    regionGroup = new Group();
    regionGroup = createRegionMap();
  }

  /**
   * Set fill to transparent.
   */
  private void setTransparent()
  {
    for(SVGPath polygon : usRegions.values())
    {
      polygon.setFill(Color.TRANSPARENT);
    }
  }
}
