package starvationevasion.greninja.gui.componentPane;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import starvationevasion.common.EnumRegion;

import java.util.HashMap;

/**
 * Region polygons for click detection on interactive map.  Will make it so that
 * they can be formatted as a whole map, or displayed individually.
 * also will experament with svg.
 * TODO Make clickable.
 * TODO setup highlight/glow method for selected.
 * TODO setup with image?
 * TODO make smaller?
 */
public class RegionPaths
{
  private HashMap<EnumRegion, Polygon> usRegions;
  private Polygon California, Mountain, NorthPlains, SouthPlains,
                        Heartland, Crescent, SouthEast;
  public RegionPaths()
  {
    buildRegionPolygons();
    usRegions = new HashMap<>();
    populateHashMap();
    //setTransparent();
  }

  public Polygon getRegionPolygonByName(EnumRegion region)
  {
    return usRegions.get(region);
  }

  /**
   * Get group map of regions.
   * @return        Group of region polygons.
   */
  public Group getUSRegionMap()
  {
    Group g = new Group();
    for(Polygon polygon : usRegions.values())
    {
      g.getChildren().add(polygon);
    }
    return g;
  }

  /**
   * Instantiate region polygons.
   * TODO make non visible.
   */
  private void buildRegionPolygons()
  {
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
  }

  /**
   * Set fill to transparent.
   */
  private void setTransparent()
  {
    for(Polygon polygon : usRegions.values())
    {
      polygon.setFill(Color.TRANSPARENT);
    }
  }
}
