package starvationevasion.greninja.gui.basePane;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import starvationevasion.common.EnumPolicy;
import starvationevasion.common.EnumRegion;
import starvationevasion.common.PolicyCard;
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

    phase = PanePhase.INITIAL;
    initInitialPane();
    initCounterProposal();

    currentPane = initialPane;
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
  }

  private void initCounterProposal()
  {
    counterProposal = new GridPane();
    sendCounterButton = new Button("Send Offer");
    cancelCounterButton = new Button("Cancel");

    sendCounterButton.setOnAction(this::ButtonPressed);
    cancelCounterButton.setOnAction(this::ButtonPressed);
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

}
