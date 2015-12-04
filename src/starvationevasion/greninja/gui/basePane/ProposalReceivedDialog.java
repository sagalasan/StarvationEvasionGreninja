package starvationevasion.greninja.gui.basePane;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import starvationevasion.common.EnumPolicy;
import starvationevasion.common.EnumRegion;
import starvationevasion.common.PolicyCard;
import starvationevasion.greninja.gui.ComponentImageView.CardImage;
import starvationevasion.greninja.gui.GuiBase;

/**
 * Created by sagalasan on 12/3/15.
 */
public class ProposalReceivedDialog extends StackPane
{
  private GuiBase guiBase;
  private EnumPolicy policy;
  private EnumRegion sender;
  private PolicyCard[] playerHand;
  private String senderName;

  private PolicyCard policyCard;
  private Image image = new Image("file:assets/CardImages/magikarp.png");
  private CardImage cardImage;

  private GridPane currentPane;
  private GridPane counterProposal;
  private GridPane initialPane;

  private Button agreeButton;
  private Button counterButton;
  private Button declineButton;
  private Button sendCounterButton;
  private Button cancelCounterButton;

  private enum PanePhase { INITIAL, COUNTER }
  private PanePhase phase;

  public ProposalReceivedDialog(GuiBase guiBase, EnumPolicy policy, EnumRegion sender, PolicyCard[]playerHand)
  {
    this.guiBase = guiBase;
    this.policy = policy;
    this.sender = sender;
    this.playerHand = playerHand;
    senderName = getNameOfEnumRegion(sender);

    policyCard = PolicyCard.create(sender, policy);
    cardImage = new CardImage(image, policyCard, guiBase);

    phase = PanePhase.INITIAL;
    initInitialPane();
    initCounterProposal();

    currentPane = initialPane;
    Rectangle rect = new Rectangle(600, 600, Color.GRAY);
    this.getChildren().add(rect);
    this.getChildren().add(currentPane);
  }

  private void initInitialPane()
  {
    initialPane = new GridPane();
    agreeButton = new Button("Agree");
    counterButton = new Button("Counter");
    declineButton = new Button("Decline");

    agreeButton.setOnAction(this::ButtonPressed);
    counterButton.setOnAction(this::ButtonPressed);
    declineButton.setOnAction(this::ButtonPressed);

    initialPane.setAlignment(Pos.CENTER);
    initialPane.setHgap(4);
    initialPane.setVgap(10);

    HBox buttons = new HBox();
    buttons.setSpacing(5);
    buttons.getChildren().addAll(agreeButton, counterButton, declineButton);

    Label title = new Label(senderName + " has sent you a proposal!");
    initialPane.add(title, 0, 0);
    initialPane.add(cardImage, 0, 1);
    initialPane.add(buttons, 0, 2);
  }

  private void initCounterProposal()
  {
    counterProposal = new GridPane();
    sendCounterButton = new Button("Send Offer");
    cancelCounterButton = new Button("Cancel");

    sendCounterButton.setOnAction(this::ButtonPressed);
    cancelCounterButton.setOnAction(this::ButtonPressed);

    counterProposal.setAlignment(Pos.CENTER);
    counterProposal.setVgap(4);
    counterProposal.setHgap(10);
  }

  private void ButtonPressed(ActionEvent event)
  {
    Button button = (Button) event.getSource();
    if(button == agreeButton)              sendAgree();
    else if(button == counterButton)       swapToCounterPane();
    else if(button == declineButton)       sendDecline();
    else if(button == sendCounterButton)   sendCounter();
    else if(button == cancelCounterButton) swapToInitialPane();
  }

  private void sendAgree()
  {
  }

  private void sendCounter()
  {
  }

  private void sendDecline()
  {
  }

  private void swapToInitialPane()
  {
    this.getChildren().remove(currentPane);
    currentPane = initialPane;
    this.getChildren().add(currentPane);
  }

  private void swapToCounterPane()
  {
    this.getChildren().remove(currentPane);
    currentPane = counterProposal;
    this.getChildren().add(currentPane);
  }

  private String getNameOfEnumRegion(EnumRegion region)
  {
    String name;
    switch (region)
    {
      case CALIFORNIA:
        name = "California";
        break;
      case HEARTLAND:
        name = "Heartland";
        break;
      case NORTHERN_PLAINS:
        name = "Northern Plains";
        break;
      case SOUTHEAST:
        name = "Southeast";
        break;
      case NORTHERN_CRESCENT:
        name = "Northern Crescent";
        break;
      case SOUTHERN_PLAINS:
        name = "Southern Plains";
        break;
      case MOUNTAIN:
        name = "Mountain";
        break;
      default:
        name = "Invalid EnumRegion";
        break;
    }
    return name;
  }

}
