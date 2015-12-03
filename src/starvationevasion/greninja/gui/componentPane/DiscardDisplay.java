package starvationevasion.greninja.gui.componentPane;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Screen;
import starvationevasion.greninja.gui.ComponentImageView.CardImage;

import javax.smartcardio.Card;


/**
 * Created by Jalen on 12/1/2015.
 */
public class DiscardDisplay extends HBox
{

  //going to get all discarded cards
  //cycle through
  //private discarded cards
  private ImageView[] discardedCards;
  public DiscardDisplay(CardImage[] discardedCards)
  {
    CardImage card = discardedCards[0];
    setSpacing(30);

    card.setScaleY(1.5);
    card.setScaleX(1.5);

    setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-background-radius: 0;");
    ImageView rightArrow = new ImageView(new Image("file:assets/greninjaAssets/arrow.png"));
    ImageView leftArrow = new ImageView(new Image("file:assets/greninjaAssets/arrow.png"));
    leftArrow.setRotate(180);


    Button rightButton = new Button();
    rightButton.setBackground(Background.EMPTY);
    rightButton.setGraphic(rightArrow);
    rightArrow.setId("arrow-button");

    Button leftButton = new Button();
    leftButton.setGraphic(leftArrow);
    leftButton.setBackground(Background.EMPTY);
    leftArrow.setId("arrow-button");

    setAlignment(Pos.CENTER);



    getChildren().addAll(leftButton, card, rightButton);

  }
  private CardImage card;

  public DiscardDisplay(CardImage discardedCards)
  {
    setSpacing(30);
    setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-background-radius: 0;");
    card = discardedCards;
    card.setScaleY(1.5);
    card.setScaleX(1.5);

    //card.setId("card-image");
    ImageView rightArrow = new ImageView(new Image("file:assets/greninjaAssets/arrow.png"));
    ImageView leftArrow = new ImageView(new Image("file:assets/greninjaAssets/arrow.png"));
    leftArrow.setRotate(180);

    Button rightButton = new Button();
    rightButton.setBackground(Background.EMPTY);
    rightButton.setGraphic(rightArrow);
    rightArrow.setId("arrow-button");

    Button leftButton = new Button();
    leftButton.setGraphic(leftArrow);
    leftButton.setBackground(Background.EMPTY);
    leftArrow.setId("arrow-button");

    setAlignment(Pos.CENTER);



    getChildren().addAll(leftButton, card, rightButton);

  }
  public CardImage getCard()
  {
    return card;
  }
}
