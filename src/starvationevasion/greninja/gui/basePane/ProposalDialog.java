package starvationevasion.greninja.gui.basePane;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import starvationevasion.common.EnumRegion;
import starvationevasion.common.PolicyCard;
import starvationevasion.greninja.gui.ComponentImageView.CardImage;
import starvationevasion.greninja.gui.GuiBase;

import java.util.ArrayList;

/**
 * Created by Christiaan Martinez on 12/3/15.
 */
public class ProposalDialog extends StackPane
{
  private GuiBase guiBase;
  private PolicyCard policyCard;

  private GridPane gridPane;

  private Button sendButton;
  private Button cancelButton;

  private CardImage cardImage;

  private CheckBox[] checkBoxes;
  private static EnumRegion[] checkBoxNames = EnumRegion.US_REGIONS;
  private VBox checkBoxGroup;

  public ProposalDialog(GuiBase guiBase, PolicyCard policyCard)
  {
    this.guiBase = guiBase;
    this.policyCard = policyCard;
    initGui();
  }

  private void initGui()
  {
    initCheckBoxVBox();

    gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setVgap(4);
    gridPane.setHgap(4);
    Rectangle rect = new Rectangle(600, 600, Color.GRAY);

    sendButton = new Button("Send");
    sendButton.setAlignment(Pos.CENTER);
    sendButton.setOnAction(this::buttonPressed);

    cancelButton = new Button("Cancel");
    cancelButton.setAlignment(Pos.CENTER_RIGHT);
    cancelButton.setOnAction(this::buttonPressed);

    cardImage = new CardImage(policyCard, guiBase);

    HBox checkParent = new HBox();
    checkParent.getChildren().add(checkBoxGroup);
    checkParent.setAlignment(Pos.CENTER_RIGHT);

    Label title = new Label("Send Proposal to Selected Regions");
    title.setAlignment(Pos.CENTER);
    gridPane.add(title, 0, 0, 2, 1);
    gridPane.add(cardImage, 0, 1);
    gridPane.add(checkBoxGroup, 1, 1);
    gridPane.add(sendButton, 0, 2);
    gridPane.add(cancelButton, 1, 2);

    this.getChildren().add(rect);
    this.getChildren().add(gridPane);
  }

  private void initCheckBoxVBox()
  {
    EnumRegion ownerRegion = policyCard.getOwner();

    checkBoxGroup = new VBox();
    checkBoxGroup.setSpacing(5);
    checkBoxGroup.getChildren().add(new Label("Regions"));

    checkBoxes = new CheckBox[checkBoxNames.length];
    for(int i = 0; i < checkBoxNames.length; i++)
    {
      String name = getNameOfEnumRegion(checkBoxNames[i]);
      CheckBox box;
      if(checkBoxNames[i].equals(ownerRegion))
      {
        System.out.println("Owner");
        box = new CheckBox(name + " - (You)")
        {
          @Override
          public void fire() {}
        };
      }
      else
      {
        box = new CheckBox(name);
      }

      box.setAllowIndeterminate(false);
      box.setIndeterminate(false);
      box.setSelected(false);

      checkBoxes[i] = box;
      checkBoxGroup.getChildren().add(box);
    }
  }

  private void buttonPressed(ActionEvent event)
  {
    Button button = (Button) event.getSource();
    if(button == sendButton)
    {
      System.out.println("Send pressed");
      guiBase.removeProposalDialog(this);
      sendChatMessages();
    }
    else if(button == cancelButton)
    {
      System.out.println("Cancel pressed");
      guiBase.removeProposalDialog(this);
    }
  }

  private void sendChatMessages()
  {
    ArrayList<EnumRegion> regions = new ArrayList<>();
    EnumRegion[] regionArray;
    for(int i = 0; i < checkBoxes.length; i++)
    {
      boolean state = checkBoxes[i].isSelected();
      if(state)
      {
        EnumRegion region = checkBoxNames[i];
        regions.add(region);
        System.out.println("Chat message: " + policyCard.getTitle() + " -> " + region);
      }
    }
    regionArray = new EnumRegion[regions.size()];
    regionArray = regions.toArray(regionArray);
    guiBase.sendChatMessage(policyCard.getCardType(), regionArray);
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
