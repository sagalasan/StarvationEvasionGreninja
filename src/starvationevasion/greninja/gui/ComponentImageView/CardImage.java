package starvationevasion.greninja.gui.ComponentImageView;


import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import starvationevasion.greninja.gui.componentPane.PlayerHandGui;

import javax.smartcardio.Card;

/**
 * Created by Jalen on 11/19/2015.
 */
public class CardImage extends StackPane
{


  //private ImageView card;

  public CardImage(Image image)
  {

    ImageView card = new ImageView(image);
    //super(image);
    //card = new ImageView(image);
    //super(image);

    setId("card-image");
    //isHover()



    setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        card.toFront();
      }
    });
    addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        System.out.println("id for card is " + getId());
        event.consume();
      }
    });
    getChildren().add(card);
  }

  /**
  public double getWidth()
  {
    return getBoundsInParent().getWidth();
  }
  public double getHeight()
  {
    return getBoundsInParent().getHeight();
  }
   **/



}
