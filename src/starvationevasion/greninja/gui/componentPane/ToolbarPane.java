package starvationevasion.greninja.gui.componentPane;

import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import starvationevasion.greninja.gui.GuiBase;


/**
 * Created by ems on 11/28/15.
 */
public class ToolbarPane extends GridPane
{
  private ImageView[][] productIcons = new ImageView[3][4];
  private GuiBase base;
  private Tooltip tooltip = new Tooltip("icons");
  public ToolbarPane(GuiBase base)
  {
    this.base = base;
    setAlignment(Pos.CENTER);
    setHgap(2);
    setVgap(2);

    // TODO: use all image icons
    Image img = new Image("file:assets/farmProductIcons/FarmProduct_Fruit_64x64.png");

    for (int i = 0; i < 3; i++)
    {
      for (int j = 0; j < 4; j++)
      {
        ImageView icon = new ImageView(img);
        icon.setPreserveRatio(true);
        icon.setFitWidth(50);
        icon.setFitHeight(50);
        icon.setSmooth(true);

        productIcons[i][j] = icon;

        // TODO: add click handling here?

        add(productIcons[i][j], j, i+2);
      }
    }

    //getChildren().add(tooltip);
  }
}
