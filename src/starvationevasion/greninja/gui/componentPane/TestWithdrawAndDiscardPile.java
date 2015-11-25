package starvationevasion.greninja.gui.componentPane;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import starvationevasion.greninja.gui.basePane.CardImage;

public class TestWithdrawAndDiscardPile extends HBox
{
  public TestWithdrawAndDiscardPile()
  {
    setId("withdraw-and-discard-pile");
    addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        System.out.println("id for card is " + getId());
        event.consume();
      }
    });

    //from the list of piles, make piles depending on how many cards are in deck?
    //get withdraw/discard pile info from gui
    Image image = new Image("file:assets/CardImages/magikarp.png");
    CardImage discardPile = new CardImage(image);
    CardImage deck = new CardImage(image);

    getChildren().addAll(discardPile, deck);
  }
}
