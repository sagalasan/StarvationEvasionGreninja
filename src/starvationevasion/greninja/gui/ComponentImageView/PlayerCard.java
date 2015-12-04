package starvationevasion.greninja.gui.ComponentImageView;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import starvationevasion.greninja.gui.GuiBase;

import javax.smartcardio.Card;

/**
 * Created by Jalen on 12/3/2015.
 */
public class PlayerCard
{
  private CardImage card;
  private GuiBase base;
  private Button proposeButton;

  public PlayerCard(CardImage card, GuiBase base)
  {
    this.base = base;
    this.card = card;
    //get card and add a button to say draft at the bottom
    Button draftButton = new Button("Draft");

    proposeButton = new Button("propose");
    proposeButton.setAlignment(Pos.BOTTOM_LEFT);
    proposeButton.setScaleX(.5);
    proposeButton.setScaleY(.5);
    //VBox cardInfo = new VBox();
    proposeButton.setOnMouseClicked(this::proposeButtonClicked);
    card.getChildren().addAll(draftButton, proposeButton);
    //make button smaller, add to end of scrollpane?
    draftButton.resize(20, 20);
    draftButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event)
      {
        base.getGameController().setDraftCard(card.getPolicyCard());
        //add a drafted card to controller
        //make it a drafted card, remove button

      }
    });
  }
  public CardImage getCard()
  {
    return card;
  }
  private void proposeButtonClicked(MouseEvent event)
  {
    System.out.println("Propose button clicked");
    base.displayProposeDialog(card.getPolicyCard());
  }

}
