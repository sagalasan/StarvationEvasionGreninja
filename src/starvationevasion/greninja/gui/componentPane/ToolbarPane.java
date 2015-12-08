package starvationevasion.greninja.gui.componentPane;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import starvationevasion.common.EnumFood;
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
  private Image[] bigIconImages = new Image[NUMBER_OF_IMAGES];
  private EnumFood[] iconTypes = new EnumFood[NUMBER_OF_IMAGES];
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

    iconTypes[0] = EnumFood.CITRUS;
    iconTypes[1] = EnumFood.DAIRY;
    iconTypes[2] = EnumFood.FEED;
    iconTypes[3] = EnumFood.FISH;
    iconTypes[4] = EnumFood.FRUIT;
    iconTypes[5] = EnumFood.GRAIN;
    iconTypes[6] = EnumFood.MEAT;
    iconTypes[7] = EnumFood.NUT;
    iconTypes[8] = EnumFood.OIL;
    iconTypes[9] = EnumFood.POULTRY;
    iconTypes[10] = EnumFood.SPECIAL;
    iconTypes[11] = EnumFood.VEGGIES;


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

    bigIconImages[0] = CITRUS_256;
    bigIconImages[1] = DAIRY_256;
    bigIconImages[2] = FEED_256;
    bigIconImages[3] = FISH_256;
    bigIconImages[4] = Fruit_256;
    bigIconImages[5] = GRAIN_256;
    bigIconImages[6] = MEAT_256;
    bigIconImages[7] = NUT_256;
    bigIconImages[8] = OIL_256;
    bigIconImages[9] = POULTRY_256;
    bigIconImages[10] = SPECIAL_256;
    bigIconImages[11] = VEGGIES_256;



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

        //creates the overlay
        ToolbarButtonPane toolButtonPane = new ToolbarButtonPane(iconTypes[i*4+j], bigIconImages[i*4+j]);
        Scene overlay = new Scene(toolButtonPane, 250, 250);
        overlay.setFill(Color.TRANSPARENT);
        overlay.setRoot(toolButtonPane);
        Stage toolStage = new Stage();
        toolStage.initStyle(StageStyle.TRANSPARENT);

        //gets the bounds to make stage maximum screen size
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        toolStage.setX(bounds.getMinX());
        toolStage.setY(bounds.getMinY());
        toolStage.setWidth(bounds.getWidth());
        toolStage.setHeight(bounds.getHeight());

        toolStage.setScene(overlay);
        //when button clicked, make overlay, display

        overlay.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            toolStage.hide();
          }
        });
        iconButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            toolStage.show();
            toolStage.toFront();
          }
        });
      }
    }






    //getChildren().add(tooltip);
  }
}
