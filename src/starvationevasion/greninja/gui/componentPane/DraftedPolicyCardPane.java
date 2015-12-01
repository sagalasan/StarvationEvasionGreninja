package starvationevasion.greninja.gui.componentPane;

import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import starvationevasion.greninja.gui.ComponentImageView.CardImage;

/**
 * Created by Jalen on 11/22/2015.
 */
public class DraftedPolicyCardPane extends HBox
{
  private CardImage[] draftedCards;

  /**
   * just a placeholder constructor that makes cards for now
   * todo This needs to be able to make the cards according to what drafted cards are currently in play
   */
  public DraftedPolicyCardPane()
  {
    draftedCards = new CardImage[2];
    Image image = new Image("file:assets/CardImages/magikarp.png");
    for (int i = 0; i < 2; i++)
    {
      draftedCards[i] = new CardImage(image);
      getChildren().add(draftedCards[i]);
    }
  }

}
