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


  private String cardId;
  //private ImageView card;
  private double x, y;
  private double mouseX, mouseY;

  public CardImage(Image image, double x, double y)
  {
    super(image);
    //card = new ImageView(image);
    //super(image);
    this.x = x;
    this.y = y;

    setId("card-image");
    //isHover()
    setOnMouseMoved(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        if(isHover())
        {
          //how the card moves to the front
          toFront();
        }
      }
    });

    /**
      card.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {
        mouseX = mouseEvent.getX();
        mouseY = mouseEvent.getY();
        System.out.println("mousex is "+mouseX);
        System.out.println("mousey is "+mouseY);
      }
    });
**/
/**
 * //could possibly use to allow players to reorder the cards seen
    card.setOnMouseDragged(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent mouseEvent)
      {
        card.setLayoutX(mouseEvent.getSceneX()- mouseX);

        card.setLayoutY(mouseEvent.getSceneY() - mouseY);
      }
    });
**/
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
