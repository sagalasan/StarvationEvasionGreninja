package starvationevasion.greninja.gui.basePane;


import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import javax.smartcardio.Card;

/**
 * Created by Jalen on 11/18/2015.
 */

/**
 * Created by Jalen on 11/19/2015.
 */
public class CardController extends StackPane {


  private String cardId;
  private ImageView card;
  private double x, y;
  public CardController(Image image, double x, double y, String cardId)
  {
    card = new ImageView(image);
    this.cardId = cardId;
    //super(image);
    this.x = x;
    this.y = y;
    //this.cardId = cardId;
    //card = new ImageView(new Image(getClass().getResourceAsStream(filePath)));
    //card.setScaleX(.12);
    //card.setScaleY(.12);
    card.setLayoutX(x);
    card.setLayoutY(y);
    getChildren().add(card);


    card.setOnMouseDragged(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent mouseEvent) {

        card.setLayoutX(mouseEvent.getSceneX() - getX());
        card.setLayoutY(mouseEvent.getSceneY() - getY());
      }
    });

    card.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {

        System.out.println("CardId is " + getCardId());
        event.consume();
      }
    });
  }


  public double getX()
  {
    return x;
  }
  public double getY()
  {
    return y;
  }
  public String getCardId()
  {
    return cardId;
  }

}
