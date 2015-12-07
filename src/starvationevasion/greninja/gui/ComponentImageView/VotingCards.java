package starvationevasion.greninja.gui.ComponentImageView;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import starvationevasion.common.PolicyCard;
import starvationevasion.greninja.gui.GuiBase;

/**
 * The voting cards for the voting pane
 */
public class VotingCards extends VBox
{
  private int yesVotes = 0;
  private int noVotes = 0;
  private int neutralVotes = 7;
  private Text votes;
  private int currentStatus = 1;
  private CardImage card;

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

  public VotingCards(boolean isVotingCard, PolicyCard policyCard, GuiBase base)
  {
    //card will have a background dependent if voting card
    setPrefHeight(100);
    setPrefWidth(150);
    card = new CardImage(policyCard, base);
    setAlignment(Pos.CENTER);
    if (isVotingCard)
    {
      //create background
      //each vote needs to be gotten from the controller
      votes = new Text(upArrow + yesVotes + "  " + sidewaysArrow + neutralVotes + "  " + downArrow + noVotes);
      votes.setFill(Color.WHITE);
      getChildren().addAll(card.getScrollCard(), votes);
      setStyle(GREY);

      card.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {

          String eventPressed = event.getButton().toString();
          if (eventPressed.equals("PRIMARY"))
          {
            upVote();
            System.out.println("mouse 1 clicked");
          }
          //currentstatus gets updated as a player clicks upvoting
          if (currentStatus % 3 == 0)
          {
            setStyle(RED);
            //card.setStyle(RED_SHADOW);
          }
          else if (currentStatus % 3 == 1)
          {
            setStyle(GREY);
            //card.setStyle(GREY_SHADOW);
          }
          else if (currentStatus %3 == 2)
          {
            setStyle(GREEN);
            //card.setStyle(GREEN_SHADOW);
          }
          //todo dependent on what wote status
          //send update to the server at end of timeout for pane

        }

      });
    }
    else
    {
      getChildren().add(card.getScrollCard());
    }

  }

  /**
   * upVotes.  The status is dependent on the currentstatus modulo 3
   *
   */
  private void upVote()
  {
    currentStatus++;
  }

}
