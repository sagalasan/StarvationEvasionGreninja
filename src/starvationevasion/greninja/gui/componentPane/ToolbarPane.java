package starvationevasion.greninja.gui.componentPane;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import starvationevasion.greninja.gui.ComponentImageView.IconImages;
import starvationevasion.greninja.gui.GuiBase;


/**
 * Created by ems on 11/28/15.
 */
public class ToolbarPane extends GridPane implements IconImages
{
  private Button[][] productIcons = new Button[3][4];
  private GuiBase base;
  private Tooltip tooltip = new Tooltip("icons");

  private Image[] iconImages = new Image[NUMBER_OF_IMAGES];

  //todo toolbar needs to be context sensitive
  public ToolbarPane(GuiBase base)
  {
    this.base = base;
    setAlignment(Pos.CENTER);
    setHgap(2);
    setVgap(2);

    //Image img = new Image("file:assets/farmProductIcons/FarmProduct_Fruit_64x64.png");
    String[] iconNames = new String[NUMBER_OF_IMAGES];

    iconNames[0] = "Citrus";
    iconNames[1] = "Dairy";
    iconNames[2] = "Feed";
    iconNames[3] = "Fish";
    iconNames[4] = "Fruit";
    iconNames[5] = "Grain";
    iconNames[6] = "Meat";
    iconNames[7] = "Nut";
    iconNames[8] = "Oil";
    iconNames[9] = "Poultry";
    iconNames[10] = "Special";
    iconNames[11] = "Veggies";

    iconImages[0] = CITRUS_64;
    iconImages[1] = DAIRY_64;
    iconImages[2] = FEED_64;
    iconImages[3] = FISH_64;
    iconImages[4] = Fruit_64;
    iconImages[5] = GRAIN_64;
    iconImages[6] = MEAT_64;
    iconImages[7] = NUT_64;
    iconImages[8] = OIL_64;
    iconImages[9] = POULTRY_64;
    iconImages[10] = SPECIAL_64;
    iconImages[11] = VEGGIES_64;


    for (int i = 0; i < 3; i++)
    {
      for (int j = 0; j < 4; j++)
      {
        Button iconButton = new Button();
        iconButton.setId("iconButton");
        Tooltip tooltip = new Tooltip(iconNames[i*4+j]);

        ImageView icon = new ImageView(iconImages[i*4+j]);
        icon.setPreserveRatio(true);
        icon.setFitWidth(30);
        icon.setFitHeight(30);
        icon.setSmooth(true);
        iconButton.setGraphic(icon);

        iconButton.setTooltip(tooltip);

        productIcons[i][j] = iconButton;

        // TODO: add click handling here?
        //todo each button when click needs to be context sensitive
        add(productIcons[i][j], j, i+2);
      }
    }

    //getChildren().add(tooltip);
  }
}
