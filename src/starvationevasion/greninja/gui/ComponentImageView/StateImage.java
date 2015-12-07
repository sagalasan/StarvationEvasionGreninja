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
 * Makes each state image on a ClickableMap
 *
 */
public class StateImage extends ImageView implements EffectsConstantsForDisplayInfo {


  private EnumRegion regionName;
  private boolean chosen = false;
  private Image stateImage;
  private Image stateWithTextImage;
  private Text displayInfo;
  private String styleForDispInfo;
  private ImageView[] draftStatusCards;
  private int numberOfDraftStatusCards = 0;
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


        event.consume();
      }
    });
    setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        showStateName();
        displayInfo.setStyle(styleForDispInfo);

      }
    });
    setOnMouseExited(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        if (!chosen)
        {
          deselect();
          displayInfo.setStyle(null);
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
    return draftStatusCards;
  }

  public Text getDisplayInfo()
  {
    return displayInfo;
  }


  /**
   *
   * @param region the region for which to setup display info
   * @param dispInfoTranslateX the X location the text for display info will be
   * @param dispInforTranslateY the Y location the text for display info will be
   * @param numberOfDraftCards the draft status cards for the particular region
   */
  private void setupDisplayInfo(String region, double dispInfoTranslateX, double dispInforTranslateY, int numberOfDraftCards, int cardsDiscarded)
  {
    styleForDispInfo = region;
    displayInfo = new Text("Cards Discarded: "+cardsDiscarded);
    displayInfo.setTranslateX(dispInfoTranslateX);
    displayInfo.setTranslateY(dispInforTranslateY);
    this.numberOfDraftStatusCards = numberOfDraftCards;
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

  /**
   *
   * @return gets the number of draftstatus cards made
   *          for the display info
   */
  public int getNumberOfDraftStatusCards()
  {
    return numberOfDraftStatusCards;
  }
  public void setNumberOfDraftStatusCards(int number)
  {
    numberOfDraftStatusCards = number;
  }

  /**
   * checks which region this state image is for and creates a
   * displayinfo image displaying cards discarded and the
   * corresponding draft status card number
   */
  public void updateDisplayInfo()
  {
    //int draftedCards = 2; //this will be how many drafted cards currently placed
    displayInfo = new Text("cards discarded: ");
    displayInfo.setFill(Color.WHITE);

    //displayInfo.setStyle();
    if (regionName.equals(EnumRegion.CALIFORNIA))
    {
      setupDisplayInfo(CALIFORNIA,-getFitWidth()/1.7,0, 0, 1);
    }
    else if (regionName.equals(EnumRegion.HEARTLAND))
    {
      setupDisplayInfo(HEARTLAND, getFitWidth()/4, -getFitHeight()/2, 2, 2 );
    }
    else if (regionName.equals(EnumRegion.NORTHERN_CRESCENT))
    {
      setupDisplayInfo(CRESCENT, getFitWidth()/1.6, -getFitHeight()/3, 2, 3);
    }
    else if (regionName.equals(EnumRegion.SOUTHEAST))
    {
      setupDisplayInfo(SOUTHEAST, getFitWidth()/2.2, getFitHeight()/6, 1, 0);
    }
    else if (regionName.equals(EnumRegion.SOUTHERN_PLAINS))
    {
      setupDisplayInfo(DELTAS, getFitWidth()/6, getFitHeight()/3.4, 2 , 1);
    }
    else if (regionName.equals(EnumRegion.NORTHERN_PLAINS))
    {
      setupDisplayInfo(PLAINS, -getFitWidth()/1.7, -getFitHeight()/2.3, 2, 0);
    }
    else if (regionName.equals(EnumRegion.MOUNTAIN))
    {
      setupDisplayInfo(MOUNTAIN, -getFitWidth()/2.6, getFitHeight()/3.7, 1, 3);
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

  public void selectManually()
  {
    chosen = true;
    showStateName();
  }

}

