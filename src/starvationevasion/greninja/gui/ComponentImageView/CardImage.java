package starvationevasion.greninja.gui.ComponentImageView;


import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import starvationevasion.common.PolicyCard;
import starvationevasion.greninja.gui.GuiBase;


/**
 * The CardImage class is responsible for making the cards with text and a title
 * dependent on a given policycard and image
 */
public class CardImage extends VBox
{
  private PolicyCard policyCard;
  private ImageView card;
  private GuiBase base;
  private Text gameText;

  public CardImage(Image image, PolicyCard policyCard, GuiBase base)
  {
    this.base = base;
    this.policyCard = policyCard;
    setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    setPrefWidth(100);
    setPrefHeight(200);
    setSpacing(10);
    this.policyCard = policyCard;
    card = new ImageView(image);

    Text title = new Text(policyCard.getTitle());
    title.setStyle("-fx-font-size: 14;");
    title.setFill(Color.WHITE);
    title.setWrappingWidth(card.getBoundsInParent().getWidth()+10);

    gameText = new Text(policyCard.getGameText());
    gameText.setWrappingWidth(card.getBoundsInParent().getWidth()+10); //the 20 is to pad the text area
    gameText.setFill(Color.WHITE);

    VBox cardInfo = new VBox();
    cardInfo.getChildren().addAll(gameText);//, proposeButton);


    setStyle( "-fx-font-size:10;-fx-background-fill: #992299;" +
            "-fx-border-color: white; -fx-background-color: black;" +
            "-fx-border-width: 2;-fx-background-width: 4;");


    addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        System.out.println("id for card is " + policyCard.getTitle());
        event.consume();
      }
    });
    setAlignment(Pos.CENTER);
    getChildren().addAll(title, card, gameText);
  }

  /**
   *
   * @return makes the card into a scroll pane.  This is what the prefered look of
   * the card in the player hand should be.  In some cases, it is preferred to see
   * the card as a vbox which is why cardimage does not extends scrollpane instead
   */
  public ScrollPane getScrollCard()
  {
    ScrollPane scroll = new ScrollPane(this);
    scroll.setId("card-image");
    scroll.setStyle("-fx-background: black;");
    scroll.setFitToHeight(true);
    scroll.setFitToWidth(true);
    scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


    scroll.setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        setStyle( "-fx-font-size:16;-fx-background-fill: #992299;" +
            "-fx-border-color: white; -fx-background-color: black;" +
            "-fx-border-width: 2;-fx-background-width: 4;");

        gameText.setWrappingWidth(card.getBoundsInParent().getWidth()+60);
        getChildren().set(getChildren().indexOf(gameText), gameText);
        //scroll.setPrefHeight(200);
        scroll.setPrefWidth(220);
      }
    });

    scroll.setOnMouseExited(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        setStyle("-fx-font-size:10;-fx-background-fill: #992299;" +
            "-fx-border-color: white; -fx-background-color: black;" +
            "-fx-border-width: 2;-fx-background-width: 4;");

        gameText.setWrappingWidth(card.getBoundsInParent().getWidth()+10);
        getChildren().set(getChildren().indexOf(gameText), gameText);
        //scroll.setPrefHeight(180);
        scroll.setPrefWidth(150);
      }
    });
    scroll.setStyle("-fx-font-size:8");
    scroll.setPrefHeight(160);
    scroll.setPrefWidth(150);

    return scroll;
  }

  /**
   *
   * @return returns the card
   */
  public CardImage getCard()
  {
    return this;
  }
  /**
   *
   * @return returns the policy card for this card
   */
  public PolicyCard getPolicyCard()
  {
    return policyCard;
  }

}
