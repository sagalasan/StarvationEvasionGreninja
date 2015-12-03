package starvationevasion.greninja.gui.ComponentImageView;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import starvationevasion.common.EnumRegion;
import starvationevasion.greninja.gui.componentPane.ClickableMap;

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
  private ImageView[] draftStatusCards;

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
    draftStatusCards = new ImageView[2];

    for (int i = 0; i < 2; i++)
    {
      draftStatusCards[i] = new ImageView(BACK_OF_CARD);
    }
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
    {setFitWidth(1000);
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


  public ImageView[] getDraftStatus()
  {
    //updateDisplayInfo();
    return draftStatusCards;
  }

  public Text getDisplayInfo()
  {
    return displayInfo;
  }


  private void setupDisplayInfo(String region, double dispInfoTranslateX, double dispInforTranslateY, int numberOfDraftCards)
  {
    styleForDispInfo = region;
    displayInfo.setTranslateX(dispInfoTranslateX);
    displayInfo.setTranslateY(dispInforTranslateY);
    //draftStatusCards = null;
    draftStatusCards = new ImageView[numberOfDraftCards];
    for (int i = 0; i< numberOfDraftCards; i++)
    {
      draftStatusCards[i] = new ImageView(BACK_OF_CARD);
      draftStatusCards[i].setScaleX(.25);
      draftStatusCards[i].setScaleY(.25);
      draftStatusCards[i].setTranslateX(dispInfoTranslateX + i*draftStatusCards[i].getBoundsInParent().getWidth()/2);
      draftStatusCards[i].setTranslateY(dispInforTranslateY+draftStatusCards[i].getBoundsInParent().getHeight());
      if (i == 1)
      {
        draftStatusCards[i].setRotate(10);
      }

    }
  }

  public void updateDisplayInfo()
  {
    //int draftedCards = 2; //this will be how many drafted cards currently placed
    displayInfo = new Text("cards discarded: ");
    displayInfo.setFill(Color.WHITE);


    //displayInfo.setStyle();
    if (regionName.equals(EnumRegion.CALIFORNIA))
    {
      setupDisplayInfo(CALIFORNIA,-getFitWidth()/1.7,0, 2 );
      //styleForDispInfo = new String(CALIFORNIA);
      //displayInfo.setTranslateX(-getFitWidth()/1.7);


    }
    else if (regionName.equals(EnumRegion.HEARTLAND))
    {
      setupDisplayInfo(HEARTLAND, getFitWidth()/4, -getFitHeight()/1.8, 2 );
      //styleForDispInfo= new String(HEARTLAND);
      //displayInfo.setTranslateY(-getFitHeight()/1.8);
      //displayInfo.setTranslateX(getFitWidth()/4);
    }
    else if (regionName.equals(EnumRegion.NORTHERN_CRESCENT))
    {
      setupDisplayInfo(CRESCENT, getFitWidth()/1.6, -getFitHeight()/3, 2);
      //styleForDispInfo= new String(CRESCENT);
      //displayInfo.setId("crescent-images");
      //displayInfo.setTranslateY(-getFitHeight()/3);
      //displayInfo.setTranslateX(getFitWidth()/1.6);
    }
    else if (regionName.equals(EnumRegion.SOUTHEAST))
    {
      setupDisplayInfo(SOUTHEAST, getFitWidth()/1.8, getFitHeight()/3, 2);
      //styleForDispInfo= new String(SOUTHEAST);
      //displayInfo.setId("southeast-images");
      //displayInfo.setTranslateY(getFitHeight()/3);
      //displayInfo.setTranslateX(getFitWidth()/1.8);
    }
    else if (regionName.equals(EnumRegion.SOUTHERN_PLAINS))
    {
      setupDisplayInfo(DELTAS, 0, getFitHeight()/2.6, 2 );
      //styleForDispInfo= new String(DELTAS);
      //displayInfo.setId("deltas-images");
      //displayInfo.setTranslateY(getFitHeight()/2.6);
      //displayInfo.setTranslateX(getFitWidth()/4);
    }
    else if (regionName.equals(EnumRegion.NORTHERN_PLAINS))
    {
      setupDisplayInfo(PLAINS, -getFitWidth()/6, -getFitHeight()/1.7, 2);
      //styleForDispInfo= new String(PLAINS);
      //displayInfo.setId("plains-images");
      //displayInfo.setTranslateY(-getFitHeight()/1.8);
      //displayInfo.setTranslateX(-getFitWidth()/6);
    }
    else if (regionName.equals(EnumRegion.MOUNTAIN))
    {
      setupDisplayInfo(MOUNTAIN, -getFitWidth()/2.6, getFitHeight()/3.7, 2);
      //styleForDispInfo= new String(MOUNTAIN);
      //displayInfo.setId("mountain-images");
      //displayInfo.setTranslateY(getFitHeight()/3.7);
      //displayInfo.setTranslateX(-getFitWidth()/2.6);
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
    displayInfo.setStyle(null);
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

