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
import starvationevasion.greninja.gui.GuiBase;

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
  public DraftedPolicyCardPane(GuiBase base)
  {

    setAlignment(Pos.BOTTOM_CENTER);
    setSpacing(30);
    //setMaxWidth(300);
    //setStyle("-fx-background-color: black;");


    // this.draftedCards = draftedCards;
    this.draftedCards = new CardImage[2];
    Image image = new Image("file:assets/CardImages/magikarp.png");
    //PolicyCard policyCard = PolicyCard.create(base.getGameController().getPlayer().getPlayerRegion(), EnumPolicy.Covert_Intelligence);
    PolicyCard policyCard = PolicyCard.create(EnumRegion.CALIFORNIA, EnumPolicy.Covert_Intelligence);
    for (int i = 0; i < 2; i++)
    {
      draftedCards[i] = new CardImage(image, policyCard, base);
      getChildren().add(draftedCards[i]);
    }
    //setMaxHeight(300);
    setMaxWidth(200);
    setMaxHeight(draftedCards[0].getBoundsInParent().getHeight());

    //setPrefHeight(300);
    //setMinHeight(150);
  }
  public HBox getThis()
  {
    return this;
  }

}
