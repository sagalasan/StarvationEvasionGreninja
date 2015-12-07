package starvationevasion.greninja.gui.ComponentImageView;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import starvationevasion.common.PolicyCard;
import starvationevasion.greninja.gui.GuiBase;

import javax.smartcardio.Card;

/**
 * These are the cards seen in the player hand.
 * Each one has a draft button and also displays the sliders for when
 * the user needs to specify a unit to draft.
 */
public class PlayerCard extends CardImage
{
  private GuiBase base;
  private Button proposeButton;

  public PlayerCard(PolicyCard policyCard, GuiBase base, int cardIndex)
  {
    super(policyCard, base);
    this.base = base;

    Button draftButton = new Button("Draft");

    proposeButton = new Button("propose");
    proposeButton.setAlignment(Pos.BOTTOM_LEFT);
    proposeButton.setScaleX(.5);
    proposeButton.setScaleY(.5);
    proposeButton.setOnMouseClicked(this::proposeButtonClicked);

    /**
     * gathers what units the cards need and set it
     */
    for (PolicyCard.EnumVariable val : PolicyCard.EnumVariable.values())
    {
      for (PolicyCard.EnumVariableUnit unit : PolicyCard.EnumVariableUnit.values())
      {
        if(policyCard.getRequiredVariables(val) != null && policyCard.getRequiredVariables(val).equals(unit))
        {
          Slider slider = new Slider(0, 100, 10);
          slider.setShowTickMarks(true);
          slider.setShowTickLabels(true);
          if (unit.equals(PolicyCard.EnumVariableUnit.MILLION_DOLLAR))
          {
            Text dollarLabel = new Text("Dollars");
            dollarLabel.setFill(Color.WHITE);

            getChildren().add(dollarLabel);
          }
          else if (unit.equals(PolicyCard.EnumVariableUnit.PERCENT))
          {
            Text percentLabel = new Text("Percent");
            percentLabel.setFill(Color.WHITE);
            getChildren().add(percentLabel);
          }
          else if (unit.equals(PolicyCard.EnumVariableUnit.UNIT))
          {
            Text unitLabel = new Text("Units");
            unitLabel.setFill(Color.WHITE);
            getChildren().add(unitLabel);
          }
          getChildren().addAll(slider);
        }
      }
    }
    draftButton.resize(20, 20);
    draftButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event)
      {
       if(base.getGameController().setDraftCard(getPolicyCard(), cardIndex))
       {
         base.updateDraftedCardsAndPlayerHand();
       }
      }
    });
    getChildren().addAll(draftButton, proposeButton);
  }

  private void proposeButtonClicked(MouseEvent event)
  {
    System.out.println("Propose button clicked");
    base.displayProposeDialog(getPolicyCard());
  }

}
