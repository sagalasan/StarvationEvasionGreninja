package starvationevasion.greninja.gui.componentPane;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import starvationevasion.common.EnumPolicy;
import starvationevasion.common.EnumRegion;
import starvationevasion.common.PolicyCard;
import starvationevasion.greninja.gui.ComponentImageView.CardImage;
import starvationevasion.greninja.gui.ComponentImageView.DraftCard;
import starvationevasion.greninja.gui.GuiBase;

import javax.smartcardio.Card;
import java.util.ArrayList;

/**
 * Created by Jalen on 11/22/2015.
 */
public class DraftedPolicyCardPane extends HBox
{

  //private CardImage[] draftedCards;
  private final static int MAX_CARDS = 2;
  private GuiBase base;
  /**
   * just a placeholder constructor that makes cards for now
   * todo This needs to be able to make the cards according to what drafted cards are currently in play
   */
  public DraftedPolicyCardPane(GuiBase base)
  {

    setAlignment(Pos.BOTTOM_CENTER);
    setSpacing(30);
    this.base = base;
    //base.getGameController().setDraftedCards();
    updateCards();

  }
  //todo work on overlay when icon clicked
  //todo get policy drafting pane working better
  //todo make back of card


  public void updateCards()
  {
    ArrayList<PolicyCard> draftedCards = base.getGameController().getDraftedCards();
    for (int i = 0; i < MAX_CARDS; i++)
    {
      if (draftedCards.size() > i)
      {
        Image image = new Image("file:assets/CardImages/magikarp.png");
        if (getChildren().size() > i)
        {
          getChildren().set(i, new DraftCard(image, draftedCards.get(i), base).getScrollCard());
        }
        else
        {
          getChildren().add(new DraftCard(image, draftedCards.get(i), base).getScrollCard());
        }
      }
      if (draftedCards.size() <= i && draftedCards.size() < getChildren().size())
      {
        System.out.println("removing card from drafting pane");
        getChildren().remove(i);


      }
    }
    //remove everything that is greater than the size of drafted cards

    //looks at controller, makes new cards to display
  }

}
