package starvationevasion.greninja.gui.componentPane;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import starvationevasion.greninja.gui.ComponentImageView.CardImage;

public class TestWithdrawAndDiscardPile extends HBox
{
  public TestWithdrawAndDiscardPile()
  {
    setId("withdraw-and-discard-pile");
    addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        System.out.println("id for card is " + getId());
        event.consume();
      }
    });

    //from the list of piles, make piles depending on how many cards are in deck?
    //get withdraw/discard pile info from gui
    Image image = new Image("file:assets/CardImages/magikarp.png");

    //todo discard will have to show last card discarded face up
    //todo will have to be able to cycle through all images of previous cards


    VBox discardBox = new VBox(10);
    discardBox.setAlignment(Pos.TOP_CENTER);
    Label discardLabel = new Label("Discard");
    CardImage discardPile = new CardImage(image);
    discardBox.getChildren().addAll(discardLabel, discardPile);

    DiscardDisplay dis = new DiscardDisplay(new ImageView(image));
    Scene discard = new Scene(dis, 300, 300);
    Stage newStage = new Stage();
    //newStage.setMaxHeight(300);
    //newStage.setMaxWidth(300);

    //todo get discarded pile from controller

    discard.setRoot(dis);
    newStage.setScene(discard);

    discardPile.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        System.out.println("discardPileClicked");

        newStage.show();
      }
    });

    VBox deckBox = new VBox(10);
    deckBox.setAlignment(Pos.TOP_CENTER);
    Label deckLabel = new Label("Deck");
    CardImage deck = new CardImage(image);
    deckBox.getChildren().addAll(deckLabel, deck);

//    getChildren().addAll(discardPile, deck);
    getChildren().addAll(discardBox, deckBox);
  }
}
