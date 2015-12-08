package starvationevasion.greninja.gui.componentPane;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import starvationevasion.common.EnumPolicy;
import starvationevasion.common.EnumRegion;
import starvationevasion.common.PolicyCard;
import starvationevasion.greninja.gui.ComponentImageView.CardImage;
import starvationevasion.greninja.gui.ComponentImageView.Deck;
import starvationevasion.greninja.gui.GuiBase;

public class DiscardPile extends HBox
{
  /**
   *
   * @param base the gui base from which this class gets derived
   * Makes two Piles(VBox panes) for the deck, and discard piles.
   * Makes a new stage for the discard pile when picked
   */
  public DiscardPile(GuiBase base)
  {
    setId("withdraw-and-discard-pile");
    addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        System.out.println("id for card is " + getId());
        event.consume();
      }
    });

    //this should be gotten from the game controller
    //just a placeholder
    PolicyCard policyCard = PolicyCard.create(EnumRegion.CALIFORNIA, EnumPolicy.Clean_River_Incentive);
    PolicyCard policyCard2 = PolicyCard.create(EnumRegion.CALIFORNIA, EnumPolicy.Fertilizer_Subsidy);
    CardImage[] pile = new CardImage[2];
    pile[0] = new CardImage(policyCard, base);
    pile[1] = new CardImage(policyCard2, base);

    //makes the discard pile
    VBox discardBox = new VBox(10);
    discardBox.setAlignment(Pos.TOP_CENTER);
    Label discardLabel = new Label("Discard: "+pile.length);

    CardImage discardPile = new CardImage(policyCard, base );

    discardBox.getChildren().addAll(discardLabel, discardPile.getScrollCard());

    DiscardDisplay dis = new DiscardDisplay(pile);
    Scene discard = new Scene(dis, 250, 250);
    discard.setFill(Color.TRANSPARENT);
    discard.setRoot(dis);
    Stage newStage = new Stage();
    newStage.initStyle(StageStyle.TRANSPARENT);

    //gets the bounds to make stage maximum screen size
    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();

    newStage.setX(bounds.getMinX());
    newStage.setY(bounds.getMinY());
    newStage.setWidth(bounds.getWidth());
    newStage.setHeight(bounds.getHeight());


    discard.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        String eventPressed = event.getButton().toString();

        if (eventPressed.equals("PRIMARY")) {

          newStage.hide();
        }
      }
    });

    discardPile.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        newStage.show();
        newStage.toFront();
      }
    });
    discard.setRoot(dis);
    newStage.setScene(discard);

    //This part makes the deck
    VBox deckBox = new VBox(10);
    deckBox.setAlignment(Pos.TOP_CENTER);
    Label deckLabel = new Label("Deck");
    Deck deck = new Deck(null); //instead of null, it will have the deck of cards
    deckBox.getChildren().addAll(deckLabel, deck);

    discardBox.setScaleX(.9);
    discardBox.setScaleY(.9);
    getChildren().addAll(discardBox, deckBox);
  }
}
