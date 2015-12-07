package starvationevasion.greninja.gui.ComponentImageView;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import starvationevasion.common.PolicyCard;
import starvationevasion.greninja.gui.GuiBase;

/**
 * The DraftCard is a card when it gets drafted
 * This class just adds an undo button to the card drafted
 */
public class DraftCard extends CardImage
{
  public DraftCard(PolicyCard policyCard, GuiBase base)
  {
    super(policyCard, base);
    Button undo = new Button("Undo");
    getCard().getChildren().add(undo);

    undo.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if(base.getGameController().undoDraftCard(getPolicyCard()))
        {
          base.updateDraftedCardsAndPlayerHand();
        }
      }
    });
  }
  //make undo draft button

}
