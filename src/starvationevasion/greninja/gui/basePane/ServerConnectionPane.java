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

import java.util.Timer;

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

  private TextField nameField;
  private TextField portField;

  private Label responseMessageLabel;

  private static final String INVALID_PORT_MSG = "This is an invalid port, please try again";
  private static final String UNABLE_CONNECT_MSG = "Could not connect to server, please try again";
  private static final String ATTEMPT_CONNECT_MSG = "Attempting to connect to server...";

  // Variables used to retry connections up to 4 times every 15 seconds
  private Timer connectToServerTimer;
  private int connectionCountTimeout;
  private static final int MAX_ATTEMPT_CONNECT = 4;
  private static final int CONNECTION_INTERVAL = 15000;
  private static final int REFRESH_INTERVAL = 1000;

  private boolean helloReceived = false;

  public ServerConnectionPane(GuiBase guiBase)
  {
    this.guiBase = guiBase;
    buttonListener = new ButtonListener(this);
    initGui();
  }

  public void initGui()
  {
    serverBox = new GridPane();
    Rectangle rect    = new Rectangle(500, 300, Color.GRAY);

    connectButton = new Button("Connect");
    cancelButton  = new Button("Cancel");

    nameField = new TextField();
    portField = new TextField();
    nameField.setMaxWidth(Double.MAX_VALUE);
    portField.setMaxWidth(Double.MAX_VALUE);

    responseMessageLabel = new Label("Temp response message");

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
    serverBox.add(responseMessageLabel, 0, 4, 2, 1);
    this.getChildren().add(rect);
    this.getChildren().add(serverBox);
  }

  public void buttonPressed(ActionEvent event)
  {
    Button source = (Button) event.getSource();
    System.out.println("Button pressed");
    if(source == connectButton)
    {
      String hostName = nameField.getText();
      String portString = portField.getText();
      int portInt = getPortInt(portString);

      System.out.println("Attempting to connect:");
      System.out.println("\tHostname: " + hostName);
      System.out.println("\tPort: " + portInt);

      if(portInt == -1)
      {
        responseMessageLabel.setText(INVALID_PORT_MSG);
        responseMessageLabel.setTextFill(Color.RED);
      }
      else
      {
        responseMessageLabel.setText(ATTEMPT_CONNECT_MSG);
        responseMessageLabel.setTextFill(Color.WHITE);
      }
    }
    else if(source == cancelButton)
    {
      System.out.println("Cancel pressed");
      guiBase.serverConnectionPaneCancelled();
    }
  }

  public int getPortInt(String portString)
  {
    int port;
    try
    {
      port = Integer.parseInt(portString);
    }
    catch (NumberFormatException nfe)
    {
      port = -1;
      System.out.println("Invalid port");
      //nfe.printStackTrace();
    }

    return port;
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
