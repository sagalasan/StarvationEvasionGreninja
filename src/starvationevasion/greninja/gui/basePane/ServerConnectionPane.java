package starvationevasion.greninja.gui.basePane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import starvationevasion.greninja.gui.GuiBase;

/**
 * Created by Christiaan Martinez on 11/28/15.
 */
public class ServerConnectionPane extends StackPane
{
  private GuiBase guiBase;

  private GridPane serverBox;

  private Button connectButton;
  private Button cancelButton;
  private ButtonListener buttonListener;

  public ServerConnectionPane(GuiBase guiBase)
  {
    this.guiBase = guiBase;
    buttonListener = new ButtonListener(this);
    initGui();
  }

  public void initGui()
  {
    serverBox = new GridPane();
    Rectangle rect    = new Rectangle(500, 500, Color.GRAY);

    connectButton = new Button("Connect");
    cancelButton  = new Button("Cancel");

    TextField nameField = new TextField();
    TextField portField = new TextField();
    nameField.setMaxWidth(Double.MAX_VALUE);
    portField.setMaxWidth(Double.MAX_VALUE);

    connectButton.setOnAction(buttonListener);
    cancelButton.setOnAction(buttonListener);

    serverBox.setAlignment(Pos.CENTER);
    serverBox.setHgap(4);
    serverBox.setVgap(4);
    serverBox.setPadding(new Insets(25, 25, 25, 25));
    serverBox.add(new Label("Please enter Server hostname and port."), 0, 0, 2, 1);
    serverBox.add(new Label("Hostname: "), 0, 1);
    serverBox.add(nameField, 1, 1);
    serverBox.add(new Label("Port: "), 0, 2);
    serverBox.add(portField, 1, 2);
    serverBox.add(connectButton, 0, 3);
    serverBox.add(cancelButton, 1, 3);
    this.getChildren().add(rect);
    this.getChildren().add(serverBox);
  }

  public void buttonPressed(ActionEvent event)
  {
    Button source = (Button) event.getSource();
    System.out.println("Button pressed");
    if(source == connectButton)
    {

    }
    else if(source == cancelButton)
    {
      System.out.println("Cancel pressed");
      guiBase.serverConnectionPaneCancelled();
    }
  }

  private class ButtonListener implements EventHandler<ActionEvent>
  {
    private ServerConnectionPane serverConnectionPane;

    public ButtonListener(ServerConnectionPane serverConnectionPane)
    {
      this.serverConnectionPane = serverConnectionPane;
    }

    @Override
    public void handle(ActionEvent event)
    {
      serverConnectionPane.buttonPressed(event);
    }
  }
}
