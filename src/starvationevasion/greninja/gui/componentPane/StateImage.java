package starvationevasion.greninja.gui.componentPane;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import starvationevasion.common.EnumRegion;
import starvationevasion.greninja.gui.MapHolder;

import java.util.Map;

/**
 * Created by Jalen on 11/23/2015.
 */
public class StateImage extends ImageView implements EffectsConstantsForDisplayInfo {


  private EnumRegion regionName;
  private boolean chosen = false;
  private Image stateImage;
  private Image stateWithTextImage;
  private Text displayInfo;
  private String styleForDispInfo;
  //todo display draft status somehow
  //just try and print on area a label or something
  //probably, going to make a set of images that will display at a location
  //the location will be dependent on where the map is located
  //since each of these images has the same length/width, going to have find someway to pick a coord on the map
  //and scale it dependent on scale size
  public StateImage(final Image image, final Image titleImage,
                    final EnumRegion regionName, final ClickableMap map, boolean worldMap)
  {
    super(image);
    this.regionName = regionName;
    displayInfo = new Text("cards discarded: ");

    displayInfo.setId("display-info");
    //todo
    //get what stage this state is voting on
    //draw image for that state
    //if has played policy card, then have a one beside the name
    //if 2 cards picked, then have a two beside the name
    //if 0, then put a 0
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


    //todo
    //at all times, the cards played/discarded are shown

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

       // displayInfo.toFront();
        event.consume();
      }
    });
    setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        showStateName();

        displayInfo.setStyle(styleForDispInfo);


        //displayInfo.toFront();

      }
    });
    setOnMouseExited(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        if (!chosen)
        {
          deselect();
          displayInfo.setStyle(null);
          //displayInfo.toFront();
          mouseEvent.consume();
        }

      }
    });
  }



  public Text getDisplayInfo()
  {
    return displayInfo;
  }

  public void updateDisplayInfo()
  {
    displayInfo = new Text("cards discarded: ");
    displayInfo.setFill(Color.WHITE);
    //displayInfo.setStyle();
    if (regionName.equals(EnumRegion.CALIFORNIA))
    {
      styleForDispInfo = new String(CALIFORNIA);
      //displayInfo.setId("california-images");
      displayInfo.setTranslateX(-getFitWidth()/1.7);
      //displayInfo.setTranslateY(getFitHeight()/5);

    }
    else if (regionName.equals(EnumRegion.HEARTLAND))
    {
      styleForDispInfo= new String(HEARTLAND);
      //displayInfo.setId("heartland-images");
      displayInfo.setTranslateY(-getFitHeight()/1.8);
      displayInfo.setTranslateX(getFitWidth()/4);
    }
    else if (regionName.equals(EnumRegion.NORTHERN_CRESCENT))
    {
      styleForDispInfo= new String(CRESCENT);
      //displayInfo.setId("crescent-images");
      displayInfo.setTranslateY(-getFitHeight()/3);
      displayInfo.setTranslateX(getFitWidth()/1.6);
    }
    else if (regionName.equals(EnumRegion.SOUTHEAST))
    {
      styleForDispInfo= new String(SOUTHEAST);
      //displayInfo.setId("southeast-images");
      displayInfo.setTranslateY(getFitHeight()/3);
      displayInfo.setTranslateX(getFitWidth()/1.8);
    }
    else if (regionName.equals(EnumRegion.SOUTHERN_PLAINS))
    {
      styleForDispInfo= new String(DELTAS);
      //displayInfo.setId("deltas-images");
      displayInfo.setTranslateY(getFitHeight()/2.6);
      //displayInfo.setTranslateX(getFitWidth()/4);
    }
    else if (regionName.equals(EnumRegion.NORTHERN_PLAINS))
    {
      styleForDispInfo= new String(PLAINS);
      //displayInfo.setId("plains-images");
      displayInfo.setTranslateY(-getFitHeight()/1.8);
      displayInfo.setTranslateX(-getFitWidth()/6);
    }
    else if (regionName.equals(EnumRegion.MOUNTAIN))
    {
      styleForDispInfo= new String(MOUNTAIN);
      //displayInfo.setId("mountain-images");
      displayInfo.setTranslateY(getFitHeight()/3.7);
      displayInfo.setTranslateX(-getFitWidth()/2.6);
    }
    //have to also add image of how many cards are being played



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
    displayInfo.toFront();
  }

  /**
   * shows the state image with state name on the text
   */
  public void showStateName()
  {
    toFront();
    setImage(stateWithTextImage);
    displayInfo.toFront();
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

