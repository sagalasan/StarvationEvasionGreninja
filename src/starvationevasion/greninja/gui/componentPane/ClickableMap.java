package starvationevasion.greninja.gui.componentPane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Created by Jalen on 11/23/2015.
 */
public class ClickableMap extends StackPane
{
  private static final int NUMBER_OF_REGIONS = 7;
  private StateImage[] stateArray;

  private StateImage california =
      new StateImage(new Image("file:assets/greninjaAssets/california.png"),
          new Image("file:assets/greninjaAssets/californiaTitle.png"), "California",this);

  private StateImage southernPlainsAndDeltaStates =
      new StateImage(new Image("file:assets/greninjaAssets/southernPlainsAndDeltaStates.png"),
          new Image("file:assets/greninjaAssets/southernPlainsAndDeltaStatesTitle.png"), "Southern Plains and Delta States",this);

  private StateImage pacificNorthWestAndMountainStates =
      new StateImage(new Image("file:assets/greninjaAssets/pacificNorthwestAndMountainStates.png"),
          new Image("file:assets/greninjaAssets/pacificNorthwestAndMountainStatesTitle.png"),
          "Pacific Northwest and Mountain States",this);

  private StateImage heartLand = new StateImage(new Image("file:assets/greninjaAssets/heartLand.png"),
      new Image("file:assets/greninjaAssets/heartLandTitle.png"), "HeartLand",this);

  private StateImage northernCrescent = new StateImage(new Image("file:assets/greninjaAssets/northernCrescent.png"),
      new Image("file:assets/greninjaAssets/northernCrescentTitle.png"),
      "Northern Crescent",this);

  private StateImage northernPlains = new StateImage(new Image("file:assets/greninjaAssets/northernPlains.png"),
      new Image("file:assets/greninjaAssets/northernPlainsTitle.png"),
      "Northern Plains",this);

  private StateImage southeast = new StateImage(new Image("file:assets/greninjaAssets/southeast.png"),
      new Image("file:assets/greninjaAssets/southeastTitle.png"),
      "The Southeast",this);



  public ClickableMap()
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
   *
   * @param state the state to be chosen in the gui
   *              all other states are deselected and become no longer highlighted
   *              except for the one chosen
   */
  public void chooseState(StateImage state)
  {
    for (StateImage stateimage: stateArray)
    {
      if (!state.equals(stateimage))
      {
        stateimage.deselect();
      }
    }



  }
}
