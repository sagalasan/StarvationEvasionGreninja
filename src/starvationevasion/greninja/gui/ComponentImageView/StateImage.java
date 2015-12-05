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
  private boolean clickable = true;

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



    /**
     * performs actions based on mouse clicked on the image
     */

    addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if(clickable)
        {
          map.chooseState(getThis());
          chosen = true;
          showStateName();
        }
        System.out.println("this is the region of " + regionName);

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

  public void setClickable(boolean b)
  {
    clickable = b;
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
      draftStatusCards[i].setScaleX(.4);
      draftStatusCards[i].setScaleY(.4);
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
    }
    else if (regionName.equals(EnumRegion.HEARTLAND))
    {
      setupDisplayInfo(HEARTLAND, getFitWidth()/4, -getFitHeight()/2, 2 );
    }
    else if (regionName.equals(EnumRegion.NORTHERN_CRESCENT))
    {
      setupDisplayInfo(CRESCENT, getFitWidth()/1.6, -getFitHeight()/3, 2);
    }
    else if (regionName.equals(EnumRegion.SOUTHEAST))
    {
      setupDisplayInfo(SOUTHEAST, getFitWidth()/2.2, getFitHeight()/6, 2);
    }
    else if (regionName.equals(EnumRegion.SOUTHERN_PLAINS))
    {
      setupDisplayInfo(DELTAS, getFitWidth()/6, getFitHeight()/3.4, 2 );
    }
    else if (regionName.equals(EnumRegion.NORTHERN_PLAINS))
    {
      setupDisplayInfo(PLAINS, -getFitWidth()/1.7, -getFitHeight()/2.3, 2);
    }
    else if (regionName.equals(EnumRegion.MOUNTAIN))
    {
      setupDisplayInfo(MOUNTAIN, -getFitWidth()/2.6, getFitHeight()/3.7, 2);
    }

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

