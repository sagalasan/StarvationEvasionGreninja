package starvationevasion.greninja.gui.basePane;


import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import javax.smartcardio.Card;

/**
 * Created by Jalen on 11/18/2015.
 */

/**
 * Created by Jalen on 11/19/2015.
 */
public class CardImage extends ImageView {


  //private ImageView card;

  public CardImage(Image image)
  {
    super(image);
    //card = new ImageView(image);
    //super(image);

    setId("card-image");
    //isHover()
    setOnMouseMoved(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        //if(isHover())
        //{
          //todo fix the way it moves to front
          //how the card moves to the front
          toFront();
       // }
      }
    });

    addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        System.out.println("id for card is " + getId());
        event.consume();
      }
    });
  }

  public double getWidth()
  {
    return getBoundsInParent().getWidth();
  }
  public double getHeight()
  {
    return getBoundsInParent().getHeight();
  }



}
