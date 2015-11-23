package starvationevasion.greninja.gui.componentPane;

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import starvationevasion.greninja.gui.basePane.CardImage;
import starvationevasion.greninja.gui.basePane.PolicyPane;

import java.util.List;

/**
 * Created by Jalen on 11/22/2015.
 */
public class WithdrawAndDiscardPile extends VBox
{

 // public WithdrawAndDiscardPile(List<PolicyPane> Deck, List<PolicyPane> DiscardPile)
  public WithdrawAndDiscardPile()
  {
    //from the list of piles, make piles depending on how many cards are in deck?
    //get withdraw/discard pile info from gui
    Image image = new Image("file:assets/CardImages/magikarp.png");
    CardImage discardPile = new CardImage(image, 0,0);
    CardImage deck = new CardImage(image,0,0);

    discardPile.setRotate(90);
    deck.setRotate(90);
    getChildren().addAll(discardPile, deck);

  }

}
