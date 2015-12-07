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


  private int currentIndex = 0;
  private CardImage card;

  /**
   *
   * @param discardedCards the cards in the discard pile
   *
   */
  public DiscardDisplay(final CardImage[] discardedCards)
  {

    card = discardedCards[discardedCards.length-1];
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

    leftButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (!(currentIndex - 1 < 0))
        {
          currentIndex--;
          card = discardedCards[currentIndex%discardedCards.length];
          card.setScaleY(1.5);
          card.setScaleX(1.5);
          getChildren().set(1, card); //1 is the index of the card in the children
        }

        //System.out.println("left arrow clicked");
      }
    });
    rightButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (!(currentIndex + 1 >= discardedCards.length))
        {
          currentIndex++;
          card = discardedCards[currentIndex%discardedCards.length];
          card.setScaleY(1.5);
          card.setScaleX(1.5);
          getChildren().set(1, card);
        }


        //System.out.println("right arrow clicked");
      }
    });
    setAlignment(Pos.CENTER);


    getChildren().addAll(leftButton, card.getScrollCard(), rightButton);

  }
  public void setCard(int index)
  {
    //card = discardedCards[index%discardedCards.length]
  }


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
