package starvationevasion.greninja.gui.ComponentImageView;


import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import starvationevasion.common.EnumPolicy;
import starvationevasion.common.EnumRegion;
import starvationevasion.common.PolicyCard;
import starvationevasion.greninja.gui.GuiBase;

import javax.smartcardio.Card;

/**
 * Created by Jalen on 11/19/2015.
 */
public class CardImage extends VBox
{
  private PolicyCard policyCard;

  private Button proposeButton;

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

    //PolicyCard policyCard = PolicyCard.create(EnumRegion.CALIFORNIA,
      //  EnumPolicy.Clean_River_Incentive);
    //this.policyCard = policyCard;
    //Label title = new Label(policyCard.getTitle());
    card = new ImageView(image);
    //ScrollPane scrollPane = new ScrollPane();

    Text title = new Text(policyCard.getTitle());
    title.setStyle("-fx-font-size: 14;");
    title.setFill(Color.WHITE);
    title.setWrappingWidth(card.getBoundsInParent().getWidth()+10);

    gameText = new Text(policyCard.getGameText());
    gameText.setWrappingWidth(card.getBoundsInParent().getWidth()+10); //the 20 is to pad the text area
    gameText.setFill(Color.WHITE);

    proposeButton = new Button("propose");
    proposeButton.setAlignment(Pos.BOTTOM_LEFT);
    proposeButton.setScaleX(.5);
    proposeButton.setScaleY(.5);
    VBox cardInfo = new VBox();
    cardInfo.getChildren().addAll(gameText, proposeButton);

    proposeButton.setOnMouseClicked(this::proposeButtonClicked);

   // scrollPane.setContent(cardInfo);

    //scrollPane.setMaxWidth(card.getBoundsInParent().getWidth()+20);
    //scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //will set vertical scroll bar to not show
    //scrollPane.setStyle("-fx-background: black;");
    setStyle( "-fx-font-size:10;-fx-background-fill: #992299;" +
            "-fx-border-color: white; -fx-background-color: black;" +
            "-fx-border-width: 2;-fx-background-width: 4;");

    //setId("card-image");


    //todo place a draft card button and a setting to choose how much money/resources to use for it


    setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        //card.toFront();
        //scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        //to set to front
        //get the parent pane
        //get the child at this index
        //set that to front

      }
    });


    addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {

        System.out.println("id for card is " + getId());
        event.consume();
      }
    });
    setAlignment(Pos.CENTER);
    //getChildren().add(card);
    getChildren().addAll(title, card, gameText, proposeButton);
   // ScrollPane scroll = new ScrollPane(this);
  }
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
        scroll.setPrefHeight(200);
        scroll.setPrefWidth(290);
      }
    });

    scroll.setOnMouseExited(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setStyle( "-fx-font-size:10;-fx-background-fill: #992299;" +
            "-fx-border-color: white; -fx-background-color: black;" +
            "-fx-border-width: 2;-fx-background-width: 4;");

        gameText.setWrappingWidth(card.getBoundsInParent().getWidth()+10);
        getChildren().set(getChildren().indexOf(gameText), gameText);
        scroll.setPrefHeight(180);
        scroll.setPrefWidth(230);
      }
    });
    scroll.setPrefHeight(180);
    scroll.setPrefWidth(230);

    return scroll;
  }




  private void proposeButtonClicked(MouseEvent event)
  {
    System.out.println("Propose button clicked");
    base.displayProposeDialog(policyCard);
  }

}
