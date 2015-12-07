package starvationevasion.greninja.gui.componentPane;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import starvationevasion.common.EnumRegion;
import starvationevasion.greninja.gui.ComponentImageView.MapImages;
import starvationevasion.greninja.gui.ComponentImageView.StateImage;
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
        //StackPane allInfo = new StackPane();

        state.updateDisplayInfo();
        getChildren().addAll(state.getDisplayInfo());

        //the 2 will have to be a value gotten from the stateimage
        for (int i = 0; i < state.getNumberOfDraftStatusCards(); i++)
        {
          getChildren().add(state.getDraftStatus()[i]);
        }
      }
    }

  }

  /**
   *
   * @param regionName sets the state specified to be opaque or not
   *              this can be used to say that a state has been selected
   * @param greyOut whether or not the state will be greyed out.  True if you want
   *                it to be greyed, false if you want it to be full opacity
   */
  public void greyOut(EnumRegion regionName, boolean greyOut)
  {

    //state.setOpacity(.5);
    for (StateImage state: stateArray)
    {
      if (!state.getName().equals(regionName))
      {
        if (greyOut == true)
        {
          state.setClickable(false);
          state.setOpacity(.5);
        }
        else
        {
          state.setClickable(true);
          state.setOpacity(1);
          chooseState(state);
          state.selectManually();
        }
      }
    }
  }


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
