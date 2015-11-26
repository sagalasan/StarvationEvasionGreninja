package starvationevasion.greninja.gui.componentPane;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import starvationevasion.common.EnumRegion;
import starvationevasion.greninja.gui.MapHolder;

import java.util.Map;

/**
 * Created by Jalen on 11/23/2015.
 */
public class StateImage extends ImageView {


  private String regionName;

  public StateImage(final Image image, final Image titleImage, final String regionName)
  {
    super(image);
    this.regionName = regionName;
    //card = new ImageView(image);
    //super(image);
    setFitHeight(350);
    setFitWidth(500);
    setId("state-image");
    //this.titleImage = titleImage;



    /**
     * performs actions based on mouse clicked on the image
     */
    addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        System.out.println("this is the region of " + regionName);
        event.consume();
      }
    });
    setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        toFront();
        setImage(titleImage);
      }
    });
    setOnMouseExited(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        setImage(image);
      }
    });
  }


}

