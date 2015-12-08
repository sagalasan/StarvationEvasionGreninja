package starvationevasion.greninja.gui.componentPane;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import starvationevasion.common.EnumFood;
import starvationevasion.io.CropCSVLoader;

/**
 * Created by Jalen on 12/7/2015.
 */
public class ToolbarButtonPane extends BorderPane
{
  public ToolbarButtonPane(String type, Image bigImage)
  {
    setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-background-radius: 0;");
    ImageView image = new ImageView(bigImage);
    HBox top = new HBox();
    Label title = new Label(type);
    title.setTextFill(Color.WHITE);
    title.setAlignment(Pos.CENTER);
    title.setStyle( "-fx-font-size:26;"+"-fx-border-color: white;");
    top.getChildren().addAll(image, title);
    setTop(top);

    //getChildren().add(image);
  }

}
