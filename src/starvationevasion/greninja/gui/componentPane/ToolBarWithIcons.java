package starvationevasion.greninja.gui.componentPane;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import javax.tools.Tool;

/**
 * Created by Jalen on 11/22/2015.
 */
public class ToolBarWithIcons extends HBox
{
  public ToolBarWithIcons()
  {
    final ImageView fruit = new ImageView(new Image("file:assets/farmProductIcons/FarmProduct_Fruit_64x64.png"));
    final ImageView grain = new ImageView(new Image("file:assets/farmProductIcons/FarmProduct_Grains_64x64.png"));

    getChildren().add(fruit);
    getChildren().add(grain);
  }




}
