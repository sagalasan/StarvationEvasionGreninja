package starvationevasion.greninja.gui.ComponentImageView;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import starvationevasion.common.PolicyCard;
import starvationevasion.greninja.gui.GuiBase;

/**
 * Created by Jalen on 12/4/2015.
 */
public class DraftCard extends CardImage
{
  public DraftCard(Image image, PolicyCard policyCard, GuiBase base)
  {
    super(image, policyCard, base);
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
