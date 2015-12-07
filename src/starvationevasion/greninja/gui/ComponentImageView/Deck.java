package starvationevasion.greninja.gui.ComponentImageView;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import starvationevasion.common.PolicyCard;

import java.util.List;

/**
 * Created by Jalen on 12/7/2015.
 */
public class Deck extends ImageView
{
  public Deck(Image image, List<PolicyCard> deckOfPolicyCards)
  {
    super(image);
  }

}
