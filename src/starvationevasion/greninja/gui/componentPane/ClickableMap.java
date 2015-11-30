package starvationevasion.greninja.gui.componentPane;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import starvationevasion.common.EnumRegion;
import starvationevasion.greninja.gui.MapHolder;

/**
 * Created by Jalen on 11/23/2015.
 */
public class ClickableMap extends StackPane implements MapImages
{
  private static final int NUMBER_OF_REGIONS = 7;
  private StateImage[] stateArray;
  private MapHolder containingPane;
  private StateImage california, southernPlainsAndDeltaStates,
      southeast, pacificNorthWestAndMountainStates,
      heartLand, northernCrescent, northernPlains;
  private boolean worldMap = true;
  //private StackPane USMap;

  public void initImages()
  {

    if(worldMap)
    {
      california =
          new StateImage(CALIFORNIA_IMAGE, CALIFORNIA_TITLE_IMAGE, EnumRegion.CALIFORNIA,this, worldMap);

      southernPlainsAndDeltaStates =
          new StateImage(SOUTHERN_PLAINS_AND_DELTA_STATES_IMAGE,
              SOUTHERN_PLAINS_AND_DELTA_STATES_TITLE_IMAGE, EnumRegion.SOUTHERN_PLAINS,this, worldMap);

      pacificNorthWestAndMountainStates =
          new StateImage(PACIFIC_NORTHWEST_AND_MOUTAIN_STATES_IMAGE,
              PACIFIC_NORTHWEST_AND_MOUNTAIN_STATES_TITLE_IMAGE,
              EnumRegion.MOUNTAIN,this, worldMap);

      heartLand = new StateImage(HEARTLAND_IMAGE, HEARTLAND_TITLE_IMAGE,
          EnumRegion.HEARTLAND,this, worldMap);

      northernCrescent = new StateImage(NORTHERN_CRESCENT_IMAGE,
          NORTHERN_CRESCENT_TITLE_IMAGE,
          EnumRegion.NORTHERN_CRESCENT,this, worldMap);

      northernPlains = new StateImage(NORTHERN_PLAINS_IMAGE,
          NORTHERN_PLAINS_TITLE_IMAGE,
          EnumRegion.NORTHERN_PLAINS,this, worldMap);

      southeast = new StateImage(SOUTHEAST_IMAGE,
          SOUTHEAST_TITLE_IMAGE, EnumRegion.SOUTHEAST,this, worldMap);

    }
    else
    {
      california =
          new StateImage(CALIFORNIA_IMAGE_VOTING, CALIFORNIA_TITLE_IMAGE_VOTING,
              EnumRegion.CALIFORNIA,this, worldMap);

      southernPlainsAndDeltaStates =
          new StateImage(SOUTHERN_PLAINS_AND_DELTA_STATES_IMAGE_VOTING,
              SOUTHERN_PLAINS_AND_DELTA_STATES_TITLE_IMAGE_VOTING, EnumRegion.SOUTHERN_PLAINS,this, worldMap);

      pacificNorthWestAndMountainStates =
          new StateImage(PACIFIC_NORTHWEST_AND_MOUTAIN_STATES_IMAGE_VOTING,
              PACIFIC_NORTHWEST_AND_MOUNTAIN_STATES_TITLE_IMAGE_VOTING,
              EnumRegion.MOUNTAIN,this, worldMap);

      heartLand = new StateImage(HEARTLAND_IMAGE_VOTING, HEARTLAND_TITLE_IMAGE_VOTING,
          EnumRegion.HEARTLAND,this, worldMap);

      northernCrescent = new StateImage(NORTHERN_CRESCENT_IMAGE_VOTING,
          NORTHERN_CRESCENT_TITLE_IMAGE_VOTING,
          EnumRegion.NORTHERN_CRESCENT,this, worldMap);

      northernPlains = new StateImage(NORTHERN_PLAINS_IMAGE_VOTING,
          NORTHERN_PLAINS_TITLE_IMAGE_VOTING,
          EnumRegion.NORTHERN_PLAINS,this, worldMap);

      southeast = new StateImage(SOUTHEAST_IMAGE_VOTING,
          SOUTHEAST_TITLE_IMAGE_VOTING, EnumRegion.SOUTHEAST,this, worldMap);
    }

  }

  public ClickableMap(String paneType)
  {
    if (paneType.toLowerCase() == "voting")
    {
      worldMap = false;
      //USMap = new StackPane();
    }
    else
    {

      worldMap = true;

    }
    //this.worldMap = worldMap;
    initImages();
    stateArray = new StateImage[NUMBER_OF_REGIONS];
    stateArray[0] = california;
    stateArray[1] = pacificNorthWestAndMountainStates;
    stateArray[2] = southeast;
    stateArray[3] = southernPlainsAndDeltaStates;
    stateArray[4] = heartLand;
    stateArray[5] = northernCrescent;
    stateArray[6] = northernPlains;

    getChildren().addAll(california, pacificNorthWestAndMountainStates, southeast,
        southernPlainsAndDeltaStates, heartLand, northernCrescent, northernPlains);

    if (worldMap && paneType.toLowerCase().equals("policy"))
    {
      for (StateImage state: stateArray)
      {
        state.updateDisplayInfo();
        getChildren().add(state.getDisplayInfo());
      }
    }


    //california.updateDisplayInfo();
    //getChildren().addAll(california.getDisplayInfo());


  }

/**
 * tried to set images to null.  I dont believe this worked
  public void setMapToNull()
  {
    california = null; southernPlainsAndDeltaStates = null;
        southeast = null; pacificNorthWestAndMountainStates = null;
        heartLand = null; northernCrescent = null; northernPlains = null;

    for (StateImage state : stateArray)
    {
      state = null;
    }

  }
 **/
  /**
   * Set a reference to the pane that contains the map so that actions can be
   * performed when things happen.
   * @param container       Pane that implements MapHolder.
   */
  public void setContainingPane(MapHolder container)
  {
    containingPane = container;
  }

  /**
   *
   * @param state the state to be chosen in the gui
   *              all other states are deselected and become no longer highlighted
   *              except for the one chosen
   */
  public void chooseState(StateImage state)
  {
    state.toFront();
    containingPane.regionClicked(state.getName());
    for (StateImage stateimage: stateArray)
    {
      if (!state.equals(stateimage))
      {
        stateimage.deselect();
      }
    }



  }
}
