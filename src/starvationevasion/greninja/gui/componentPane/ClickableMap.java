package starvationevasion.greninja.gui.componentPane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Created by Jalen on 11/23/2015.
 */
public class ClickableMap extends StackPane
{

  StateImage california =
      new StateImage(new Image("file:assets/greninjaAssets/california.png"),
          new Image("file:assets/greninjaAssets/californiaTitle.png"), "California");

  StateImage southernPlainsAndDeltaStates =
      new StateImage(new Image("file:assets/greninjaAssets/southernPlainsAndDeltaStates.png"),
          new Image("file:assets/greninjaAssets/southernPlainsAndDeltaStatesTitle.png"), "Southern Plains and Delta States");

  StateImage pacificNorthWestAndMountainStates =
      new StateImage(new Image("file:assets/greninjaAssets/pacificNorthwestAndMountainStates.png"),
          new Image("file:assets/greninjaAssets/pacificNorthwestAndMountainStatesTitle.png"),
          "Pacific Northwest and Mountain States");

  StateImage heartLand = new StateImage(new Image("file:assets/greninjaAssets/heartLand.png"),
      new Image("file:assets/greninjaAssets/heartLandTitle.png"), "HeartLand");

  StateImage northernCrescent = new StateImage(new Image("file:assets/greninjaAssets/northernCrescent.png"),
      new Image("file:assets/greninjaAssets/northernCrescentTitle.png"),
      "Northern Crescent");

  StateImage northernPlains = new StateImage(new Image("file:assets/greninjaAssets/northernPlains.png"),
      new Image("file:assets/greninjaAssets/northernPlainsTitle.png"),
      "Northern Plains");

  StateImage southeast = new StateImage(new Image("file:assets/greninjaAssets/southeast.png"),
      new Image("file:assets/greninjaAssets/southeastTitle.png"),
      "The Southeast");

  public ClickableMap()
  {
    getChildren().addAll(california, pacificNorthWestAndMountainStates, southeast,
        southernPlainsAndDeltaStates, heartLand, northernCrescent, northernPlains);
  }
}
