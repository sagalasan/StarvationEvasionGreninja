package starvationevasion.greninja.gui.componentPane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import starvationevasion.common.EnumRegion;
import starvationevasion.greninja.gui.MapHolder;

/**
 * Created by Jalen on 11/27/2015.
 */

/**
 * Pretty much the same as the clickable map except with different images made for the map
 */
public class VotingView extends StackPane implements MapImages
{

  private static final int NUMBER_OF_REGIONS = 7;
  private StateImage[] stateArray;
  private MapHolder containingPane;

  private StateImage california =
      new StateImage(CALIFORNIA_IMAGE_VOTING, CALIFORNIA_TITLE_IMAGE_VOTING,
          EnumRegion.CALIFORNIA,this, true);

  private StateImage southernPlainsAndDeltaStates =
      new StateImage(SOUTHERN_PLAINS_AND_DELTA_STATES_IMAGE_VOTING,
          SOUTHERN_PLAINS_AND_DELTA_STATES_TITLE_IMAGE_VOTING, EnumRegion.SOUTHERN_PLAINS,this, true);

  private StateImage pacificNorthWestAndMountainStates =
      new StateImage(PACIFIC_NORTHWEST_AND_MOUTAIN_STATES_IMAGE_VOTING,
          PACIFIC_NORTHWEST_AND_MOUNTAIN_STATES_TITLE_IMAGE_VOTING,
          EnumRegion.MOUNTAIN,this, true);

  private StateImage heartLand = new StateImage(HEARTLAND_IMAGE_VOTING, HEARTLAND_TITLE_IMAGE_VOTING,
      EnumRegion.HEARTLAND,this, true);

  private StateImage northernCrescent = new StateImage(NORTHERN_CRESCENT_IMAGE_VOTING,
      NORTHERN_CRESCENT_TITLE_IMAGE_VOTING,
      EnumRegion.NORTHERN_CRESCENT,this, true);

  private StateImage northernPlains = new StateImage(NORTHERN_PLAINS_IMAGE_VOTING,
      NORTHERN_PLAINS_TITLE_IMAGE_VOTING,
      EnumRegion.NORTHERN_PLAINS,this, true);

  private StateImage southeast = new StateImage(SOUTHEAST_IMAGE_VOTING,
      SOUTHEAST_TITLE_IMAGE_VOTING, EnumRegion.SOUTHEAST,this, true);

  public VotingView()
  {
    stateArray = new StateImage[NUMBER_OF_REGIONS];
    getChildren().addAll(california, pacificNorthWestAndMountainStates, southeast,
        southernPlainsAndDeltaStates, heartLand, northernCrescent, northernPlains);

    stateArray[0] = california;
    stateArray[1] = pacificNorthWestAndMountainStates;
    stateArray[2] = southeast;
    stateArray[3] = southernPlainsAndDeltaStates;
    stateArray[4] = heartLand;
    stateArray[5] = northernCrescent;
    stateArray[6] = northernPlains;



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
