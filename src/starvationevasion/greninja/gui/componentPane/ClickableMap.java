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
      new StateImage(new Image("file:assets/greninjaAssets/california.png"), "California");

  StateImage southernPlainsAndDeltaStates =
      new StateImage(new Image("file:assets/greninjaAssets/southernPlainsAndDeltaStates.png"), "Southern Plains and Delta States");

  StateImage pacificNorthWestAndMountainStates =
      new StateImage(new Image("file:assets/greninjaAssets/pacificNorthwestAndMountainStates.png"),
          "Pacific Northwest and Mountain States");

  StateImage heartLand = new StateImage(new Image("file:assets/greninjaAssets/heartLand.png"), "HeartLand");

  StateImage northernCrescent = new StateImage(new Image("file:assets/greninjaAssets/northernCrescent.png"),
      "Northern Crescent");

  StateImage northernPlains = new StateImage(new Image("file:assets/greninjaAssets/northernPlains.png"),
      "Northern Plains");

  StateImage southeast = new StateImage(new Image("file:assets/greninjaAssets/southeast.png"),
      "The Southeast");

  public ClickableMap()
  {
    getChildren().addAll(california, pacificNorthWestAndMountainStates, southeast,
        southernPlainsAndDeltaStates, heartLand, northernCrescent, northernPlains);
  }
}
