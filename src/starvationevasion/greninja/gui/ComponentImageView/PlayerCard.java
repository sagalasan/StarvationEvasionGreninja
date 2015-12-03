package starvationevasion.greninja.gui.ComponentImageView;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import starvationevasion.greninja.gui.GuiBase;

import javax.smartcardio.Card;

/**
 * Created by Jalen on 12/3/2015.
 */
public class PlayerCard
{
  private CardImage card;
  public PlayerCard(CardImage card, GuiBase base)
  {
    this.card = card;
    //get card and add a button to say draft at the bottom
    Button draftButton = new Button("Draft");
    card.getChildren().add(draftButton);
    //make button smaller, add to end of scrollpane?
    draftButton.resize(20, 20);
    draftButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        //add a drafted card to controller
        //make it a drafted card, remove button
      }
    });
  }
  public CardImage getCard()
  {
    return card;
  }

}
