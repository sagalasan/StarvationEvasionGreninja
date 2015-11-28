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


  private EnumRegion regionName;
  private boolean chosen = false;
  private Image stateImage;
  private Image stateWithTextImage;
  public StateImage(final Image image, final Image titleImage,
                    final EnumRegion regionName, final ClickableMap map, boolean worldMap)
  {
    super(image);
    this.regionName = regionName;
    setFitHeight(350);
    if (worldMap)
    {
      setFitWidth(500);
    }
    else
    {
      setFitWidth(1000);
    }

    setId("state-image");
    stateImage = image;
    stateWithTextImage = titleImage;


    /**
     * performs actions based on mouse clicked on the image
     */

    addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        System.out.println("this is the region of " + regionName);
        map.chooseState(getThis());
        chosen = true;
        showStateName();
        event.consume();
      }
    });
    setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        showStateName();

      }
    });
    setOnMouseExited(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        if (!chosen)
        {
          deselect();
          mouseEvent.consume();
        }

      }
    });
  }

  private StateImage getThis()
  {
    return this;
  }

  /**
   * deselects the clicked on image in the gui by setting
   * its picture to the original state image and setting chosen to false
   */
  public void deselect()
  {
    chosen = false;
    setImage(stateImage);
  }

  /**
   * shows the state image with state name on the text
   */
  public void showStateName()
  {
    toFront();
    setImage(stateWithTextImage);
  }

  /**
   * Get the enum region belonging to this state.
   * @return        EnumRegion belonging to this state.
   */
  public EnumRegion getName()
  {
    return regionName;
  }



}

