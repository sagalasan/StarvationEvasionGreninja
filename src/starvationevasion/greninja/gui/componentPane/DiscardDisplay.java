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
   *  Makes a clickable discard pile with the last card discarded shown face up.  Clicking on the card displays an overlay
   */
  public DiscardDisplay(final CardImage[] discardedCards)
  {
    VBox cardDiscard = new VBox();
    card = discardedCards[discardedCards.length-1];
    setSpacing(30);

    card.setScaleY(1.5);
    card.setScaleX(1.5);

    setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-background-radius: 0;");
    ImageView rightArrow = new ImageView(new Image("greninjaAssets/arrow.png"));
    ImageView leftArrow = new ImageView(new Image("greninjaAssets/arrow.png"));
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
        currentIndex--;
        card = discardedCards[Math.abs(currentIndex)%discardedCards.length];
        card.setScaleY(1.5);
        card.setScaleX(1.5);
        cardDiscard.getChildren().set(0, card); //0 is the index of the card in the children
      }
    });
    rightButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {

        currentIndex++;
        card = discardedCards[Math.abs(currentIndex)%discardedCards.length];
        card.setScaleY(1.5);
        card.setScaleX(1.5);
        cardDiscard.getChildren().set(0, card);

      }
    });
    setAlignment(Pos.CENTER);
    cardDiscard.setAlignment(Pos.CENTER);

    cardDiscard.getChildren().add(card);
    getChildren().addAll(leftButton, cardDiscard, rightButton);

  }

  public CardImage getCard()
  {
    return card;
  }
}
