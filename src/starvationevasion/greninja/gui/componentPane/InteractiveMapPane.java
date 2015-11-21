package starvationevasion.greninja.gui.componentPane;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Interactive map pane builds and maintains an interactive map.
 * TODO sort out what needs to be saved.
 */
public class InteractiveMapPane extends StackPane
{

  Group map;

  public InteractiveMapPane()
  {
    buildUSMap();
  }

  /**
   * Build map pane as a whole us map.
   */
  private void buildUSMap()
  {
    RegionPaths pathTest = new RegionPaths();
    map = pathTest.getUSRegionMap(); //get svgs in map format
    //load bitmap
    Image img = new Image("file:assets/greninjaAssets/usMap.png");
    ImageView imgView = new ImageView();
    imgView.setImage(img); //set image to display
    imgView.setFitWidth(400);
    imgView.setPreserveRatio(true); //resize for niceness
    //scale svgs to bitmap size
    pathTest.scaleMap(imgView.getFitWidth()/map.getBoundsInParent().getWidth());
    getChildren().add(imgView);//add to pane.
    getChildren().add(map);
  }
}
