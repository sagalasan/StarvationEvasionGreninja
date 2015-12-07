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

public class TestWithdrawAndDiscardPile extends HBox
{
  public TestWithdrawAndDiscardPile(GuiBase base)
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

    //makes the discard pile
    VBox discardBox = new VBox(10);
    discardBox.setAlignment(Pos.TOP_CENTER);
    Label discardLabel = new Label("Discard");
    PolicyCard policyCard = PolicyCard.create(EnumRegion.CALIFORNIA, EnumPolicy.Clean_River_Incentive);
    PolicyCard policyCard2 = PolicyCard.create(EnumRegion.CALIFORNIA, EnumPolicy.Fertilizer_Subsidy);


    CardImage discardPile = new CardImage(image, policyCard, base );
    CardImage[] pile = new CardImage[2];
    pile[0] = new CardImage(image, policyCard, base);
    pile[1] = new CardImage(image, policyCard2, base);
    /**for (int i = 0; i < 2; i++)
    {

      pile[i] = new CardImage(image, policyCard);
      //getChildren().add(pile[i]);
    }
    **/
    discardBox.getChildren().addAll(discardLabel, discardPile.getScrollCard());

    //makes a discarddisplay which is made up of cards passed, and two arrow buttons
    //this proceeds to make new stage with a new scene that is transparent
    //DiscardDisplay dis = new DiscardDisplay(new CardImage(image, policyCard));
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

        //on mouse click, will hide pane
        //not sure why event.isprimarybuttondown method does not work
        if (eventPressed.equals("PRIMARY")) {

          newStage.hide();
        }
      }
    });




    discardPile.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        System.out.println("discardPileClicked");
        newStage.show();
        newStage.toFront();
      }
    });
    discard.setRoot(dis);
    newStage.setScene(discard);


    Image image1 = new Image("file:assets/CardImages/magikarp1.png");
    //This part makes the deck
    VBox deckBox = new VBox(10);
    deckBox.setAlignment(Pos.TOP_CENTER);
    Label deckLabel = new Label("Deck");
    Deck deck = new Deck(image1, null); //instead of null, it will have the deck of cards
    //CardImage deck = new CardImage(image1, policyCard, base);
    deckBox.getChildren().addAll(deckLabel, deck);

//    getChildren().addAll(discardPile, deck);
    //deckBox.setScaleY(.7);
    //deckBox.setScaleX(.7);

    discardBox.setScaleX(.9);
    discardBox.setScaleY(.9);
    getChildren().addAll(discardBox, deckBox);
  }
}
