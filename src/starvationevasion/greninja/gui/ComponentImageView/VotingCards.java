package starvationevasion.greninja.gui.ComponentImageView;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;




/**
 * Created by Jalen on 11/30/2015.
 */
public class VotingCards extends VBox
{
  private int yesVotes = 0;
  private int noVotes = 0;
  private int neutralVotes = 7;
  private Text votes;
  private int currentStatus = 2;
  private ImageView card;
  //can find unicode symbols from https://en.wikipedia.org/wiki/List_of_Unicode_characters#Arrows
  private final String upArrow = "\u2191";
  private final String downArrow = "\u2193";
  private final String sidewaysArrow = "\u2194";

  private final String GREEN = "-fx-effect: dropshadow( gaussian, #32cd32 , 3,1,0,0 );";
  private final String RED = "-fx-effect: dropshadow( gaussian ,  #dc143c , 3,1,0,0 );";
  private final String GREY = "-fx-effect: dropshadow( gaussian , #778899, 3,1,0,0 );";

  private final String GREEN_SHADOW = "-fx-effect: innershadow( gaussian, #32cd32 , 2,1,0,0 );";
  private final String RED_SHADOW = "-fx-effect: innershadow( gaussian ,  #dc143c , 2,1,0,0 );";
  private final String GREY_SHADOW = "-fx-effect: innershadow( gaussian , #778899, 2,1,0,0 );";

  public VotingCards(Image image, boolean isVotingCard)
  {
    //card will have a background dependent if voting card
    card = new ImageView(image);
    setAlignment(Pos.CENTER);
    if (isVotingCard)
    {
      //create background
      //each vote needs to be gotten from the controller
      votes = new Text(upArrow + yesVotes + "  " + sidewaysArrow + neutralVotes + "  " + downArrow + noVotes);
      votes.setFill(Color.WHITE);

      getChildren().addAll(card, votes);
      card.setStyle(GREY);

      setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          //System.out.println(event.getButton());

          String eventPressed = event.getButton().toString();
          if (eventPressed.equals("PRIMARY"))
          {
            upVote();
            System.out.println("mouse 1 clicked");
          }
          else if (eventPressed.equals("SECONDARY"))
          {
            downVote();
            System.out.println("mouse 2 clicked");
          }
          //currentstatus gets updated as a player clicks upvoting or downvoting
          if (currentStatus == 1)
          {
            card.setStyle(RED);
            card.setStyle(RED_SHADOW);
          }
          else if (currentStatus == 2)
          {
            card.setStyle(GREY);
            card.setStyle(GREY_SHADOW);
          }
          else if (currentStatus == 3)
          {

            card.setStyle(GREEN);
            card.setStyle(GREEN_SHADOW);
          }

        }
      });
      }
      else
      {
        getChildren().add(card);
     }

      setId("card-image");

    }
  private void upVote()
  {
    if (currentStatus < 3)
    {
      currentStatus++;
    }
  }

  private void downVote()
  {
    if (currentStatus > 1)
    {
      currentStatus--;
    }
  }
}
