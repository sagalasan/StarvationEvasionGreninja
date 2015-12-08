package starvationevasion.greninja.gui.ComponentImageView;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import starvationevasion.common.PolicyCard;

import java.util.List;

/**
 * Created by Jalen on 12/7/2015.
 */
public class Deck extends ImageView implements EffectsConstantsForDisplayInfo
{
  public Deck(List<PolicyCard> deckOfPolicyCards)
  {
    super(BACK_OF_CARD);
    /**
     * this will be context based, dependent on whether or not a player can look at the cards in here or not
     */
  }

}
