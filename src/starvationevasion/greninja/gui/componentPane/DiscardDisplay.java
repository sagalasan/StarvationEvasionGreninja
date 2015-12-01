package starvationevasion.greninja.gui.componentPane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import starvationevasion.greninja.gui.ComponentImageView.CardImage;



/**
 * Created by Jalen on 12/1/2015.
 */
public class DiscardDisplay extends StackPane
{

  //going to get all discarded cards
  //cycle through
  //private discarded cards
  private ImageView[] discardedCards;
  public DiscardDisplay(ImageView[] discardedCards)
  {
    setStyle("-fx-background-color: #333333;");
    ImageView card = discardedCards[0];
    ImageView rightArrow = new ImageView(new Image("file:assets/greninjaAssets/arrow.png"));
    ImageView leftArrow = new ImageView(new Image("file:assets/greninjaAssets/arrow.png"));
    leftArrow.setRotate(180);
    BorderPane main = new BorderPane();
    main.setCenter(card);
    main.setRight(rightArrow);
    main.setLeft(leftArrow);
  }
  public DiscardDisplay(ImageView discardedCards)
  {
    setStyle("-fx-background-color: #333333;");
    ImageView card = discardedCards;
    ImageView rightArrow = new ImageView(new Image("file:assets/greninjaAssets/arrow.png"));
    ImageView leftArrow = new ImageView(new Image("file:assets/greninjaAssets/arrow.png"));


    leftArrow.setRotate(180);
    BorderPane main = new BorderPane();
    main.setCenter(card);
    main.setRight(rightArrow);
    main.setLeft(leftArrow);

    rightArrow.setLayoutY(main.getBoundsInParent().getHeight()/2);
    leftArrow.setLayoutY(main.getBoundsInParent().getHeight()/2);

   // setMaxSize(200, 200);
    getChildren().add(main);



  }
}
