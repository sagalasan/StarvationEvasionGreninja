package starvationevasion.greninja.gui.ComponentImageView;


import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

import javax.smartcardio.Card;

/**
 * Created by Jalen on 11/19/2015.
 */
public class CardImage extends VBox
{


  //private ImageView card;

  //private PolicyCard policyCard;
  public CardImage(Image image)
  {
    setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    setMaxWidth(100);
    setMaxHeight(150);
    PolicyCard policyCard = PolicyCard.create(EnumRegion.CALIFORNIA,
        EnumPolicy.Clean_River_Incentive);
    //this.policyCard = policyCard;
    //Label title = new Label(policyCard.getTitle());
    ImageView card = new ImageView(image);
    ScrollPane scrollPane = new ScrollPane();

    Text title = new Text(policyCard.getTitle());
    title.setStyle("-fx-font-size: 14;");
    title.setFill(Color.WHITE);
    title.setWrappingWidth(card.getBoundsInParent().getWidth()+10);

    Text gameText = new Text(policyCard.getGameText());
    gameText.setWrappingWidth(card.getBoundsInParent().getWidth()+10); //the 20 is to pad the text area
    gameText.setFill(Color.WHITE);
    scrollPane.setContent(gameText);
    //scrollPane.set
    scrollPane.setMaxWidth(card.getBoundsInParent().getWidth()+20);

    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //will set vertical scroll bar to not show
    //scrollPane.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(1), null)));
    //setStyle("-fx-background: black");


    scrollPane.setStyle("-fx-background: black;");



    setId("card-image");

    //isHover()

    //todo place a draft card button and a setting to choose how much money/resources to use for it


    setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        //card.toFront();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

      }
    });

    setOnMouseExited(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
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
    getChildren().addAll(title, card, scrollPane);
  }

  /**
  public double getWidth()
  {
    return getBoundsInParent().getWidth();
  }
  public double getHeight()
  {
    return getBoundsInParent().getHeight();
  }
   **/



}
