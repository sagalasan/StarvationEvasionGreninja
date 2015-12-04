package starvationevasion.greninja.gui.basePane;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import starvationevasion.common.EnumRegion;
import starvationevasion.common.PolicyCard;
import starvationevasion.greninja.gui.ComponentImageView.CardImage;
import starvationevasion.greninja.gui.GuiBase;

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
    //this.setOpacity(.3);
    initGui();
  }

  private void initGui()
  {
    initCheckBoxVBox();

    gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    Rectangle rect = new Rectangle(500, 500,Color.GRAY);
    sendButton = new Button("Send");
    cancelButton = new Button("Cancel");

    sendButton.setOnAction(this::buttonPressed);
    cancelButton.setOnAction(this::buttonPressed);

    HBox buttonHBox = new HBox();
    buttonHBox.getChildren().add(sendButton);
    buttonHBox.getChildren().add(cancelButton);

    Image image = new Image("file:assets/CardImages/magikarp.png");
    cardImage = new CardImage(image, policyCard, guiBase);

    Label title = new Label("Send Proposal to Selected Regions");
    gridPane.add(title, 0, 0, 2, 1);
    gridPane.add(cardImage, 0, 1);
    gridPane.add(buttonHBox, 0, 2);
    gridPane.add(checkBoxGroup, 1, 1, 1, 2);

    this.getChildren().add(rect);
    this.getChildren().add(gridPane);
  }

  private void initCheckBoxVBox()
  {
    checkBoxGroup = new VBox();
    checkBoxGroup.setSpacing(5);
    checkBoxGroup.getChildren().add(new Label("Select Regions to send proposal:"));

    checkBoxes = new CheckBox[checkBoxNames.length];
    for(int i = 0; i < checkBoxNames.length; i++)
    {
      String name = getNameOfEnumRegion(checkBoxNames[i]);
      CheckBox box = new CheckBox(name);
      checkBoxGroup.getChildren().add(box);
    }
  }

  private void buttonPressed(ActionEvent event)
  {
    Button button = (Button) event.getSource();
    if(button == sendButton)
    {
      System.out.println("Send pressed");
    }
    else if(button == cancelButton)
    {
      System.out.println("Cancel pressed");
      guiBase.removeProposalDialog(this);
    }
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
