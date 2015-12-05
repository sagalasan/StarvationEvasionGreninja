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

import java.util.List;

/**
 * Created by Jalen on 11/21/2015.
 * thursday 10
 */
public class PlayerHandGui extends HBox
{
  //gather what cards to draw, place them next to each other
  private PlayerCard[] playerCards;
  private GuiBase base;
  private List<PolicyCard> playerHand;
  public PlayerHandGui(GuiBase base)//prob will take an argument of the cards it receives
  {
    System.out.println("playerhand is being created");
    this.base = base;
    setAlignment(Pos.CENTER);
    int MAX_CARDS_IN_PLAYER_HAND = 7;
    playerCards = new PlayerCard[MAX_CARDS_IN_PLAYER_HAND];

    //HBox cardHand = new HBox();
    int j = 0;

    setSpacing(25);

    base.getGameController().fillHand();
    //updatePlayerHand();
    //base.getPlayerHand();
    //initiallizes and adds to player hand
    playerHand = base.getPlayerHand();
    int i = 0;
    for (PolicyCard policy: playerHand )
    {
      Image image = new Image("file:assets/CardImages/magikarp.png");
      getChildren().add(new PlayerCard(image, policy, base, i++).getScrollCard());

    }

  }

  public void updatePlayerHand()
  {
    playerHand = base.getPlayerHand();
    for (int i = 0; i < 7; i++ )
    {
      if (playerHand.size()>i)
      {
        Image image = new Image("file:assets/CardImages/magikarp.png");
        if (getChildren().size() > i)
        {
          getChildren().set(i, new PlayerCard(image, playerHand.get(i), base, i).getScrollCard());
        }
        else
        {
          getChildren().add(new PlayerCard(image, playerHand.get(i), base, i).getScrollCard());
        }

      }
      if (playerHand.size() <= i && playerHand.size() < getChildren().size())
      {
        System.out.println("removing card from drafting pane");
        getChildren().remove(i);


      }
    }


  }





}
