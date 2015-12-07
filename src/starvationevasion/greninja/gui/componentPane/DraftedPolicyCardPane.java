package starvationevasion.greninja.gui.componentPane;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import starvationevasion.common.PolicyCard;
import starvationevasion.greninja.gui.ComponentImageView.DraftCard;
import starvationevasion.greninja.gui.GuiBase;

import java.util.ArrayList;

/**
 * Created by Jalen on 11/22/2015.
 */
public class DraftedPolicyCardPane extends HBox
{

  private final static int MAX_CARDS = 2;
  private GuiBase base;
  /**
   * just a placeholder constructor that makes cards for now
   */
  public DraftedPolicyCardPane(GuiBase base)
  {
    setAlignment(Pos.BOTTOM_CENTER);
    setSpacing(30);
    this.base = base;
    updateCards();

  }

  /**
   * gets drafted cards from game controller, adds each of the cards to the display
   */
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
  }

}
