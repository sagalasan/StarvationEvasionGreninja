package starvationevasion.greninja.gui.componentPane;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
//import starvationevasion.common.EnumPolicy;
import starvationevasion.common.EnumPolicy;
import starvationevasion.common.EnumRegion;
import starvationevasion.common.PolicyCard;
import starvationevasion.greninja.gui.ComponentImageView.PlayerCard;
import starvationevasion.greninja.gui.GuiBase;
import starvationevasion.greninja.gui.ComponentImageView.CardImage;

/**
 * Created by Jalen on 11/21/2015.
 */
public class PlayerHandGui extends HBox
{
  //gather what cards to draw, place them next to each other
  private PlayerCard[] playerCards;
  private GuiBase base;

  public PlayerHandGui(GuiBase base)//prob will take an argument of the cards it receives
  {
    this.base = base;
    setAlignment(Pos.CENTER);
    int MAX_CARDS_IN_PLAYER_HAND = 7;
    playerCards = new PlayerCard[MAX_CARDS_IN_PLAYER_HAND];

    //HBox cardHand = new HBox();
    int j = 0;
    Image image = new Image("file:assets/CardImages/magikarp.png");
    setSpacing(25);

    //List<EnumPolicy> playerHand = base.getPlayerHand();
    //for (EnumPolicy enumPolicy : playerHand)
    //{
    //  System.out.println(j++);
    //}




    /**
     * this for loop is a place holder till the player hand returns something
     */
    PolicyCard policyCard = PolicyCard.create(EnumRegion.CALIFORNIA, EnumPolicy.Covert_Intelligence);
    for (int i = 0; i < MAX_CARDS_IN_PLAYER_HAND; i++)
    {

      playerCards[i] = new PlayerCard(new CardImage(image, policyCard, base), base);
      //adds to cardHand
      getChildren().add(playerCards[i].getCard());
    }
    //Image image = new Image("file:assets/CardImages/magikarp.png");
    //make n cards, place them at bottom of pane, side by side
    //have them adjust according to their position
    //setBottom(cardHand);

  }

  /**
   *
   *  updates the hand
   */
  public void updateCards()
  {
    base.getPlayerHand();
  }





}
