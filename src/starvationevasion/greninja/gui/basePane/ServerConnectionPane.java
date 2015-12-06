package starvationevasion.greninja.gui.basePane;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import starvationevasion.greninja.gui.GuiBase;
import starvationevasion.greninja.serverCom.ServerConnection;
import starvationevasion.server.ServerConstants;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Christiaan Martinez on 11/28/15.
 */
public class ServerConnectionPane extends StackPane
{
  private GuiBase guiBase;

  private GridPane serverBox;

  private Button connectButton;
  private Button cancelButton;
  private Node enterKeyNode;

  private String titleText = "Please enter Server hostname and port.";
  private Label title = new Label(titleText);
  private TextField nameField;
  private TextField portField;

  private Label responseMessageLabel;

  private static final String INVALID_PORT_MSG = "This is an invalid port, please try again";
  private static final String UNABLE_CONNECT_MSG = "Could not connect to server, please try again";
  private static final String ATTEMPT_CONNECT_MSG = "Attempting to connect to server... ";
  private static final String RETRY_CONNECT_MSG = "Could not connect, retrying";
  private String firstAttemptMessage = ATTEMPT_CONNECT_MSG + "%ds";
  private String retryAttepmtMessage = RETRY_CONNECT_MSG + " {%d/" + MAX_ATTEMPT_CONNECT + "} %ds";

  // Variables used to retry connections up to 4 times every 15 seconds
  private Timer connectToServerTimer;
  private int connectionCountTimeout;

  // public because inner class needs these
  public static final int MAX_ATTEMPT_CONNECT = 4;
  public static final long CONNECTION_INTERVAL = 15000;
  private static final long REFRESH_INTERVAL = 1000;

  private boolean helloReceived = false;

  private String hostName;
  private int port;

  private Stage currentStage;

  private boolean singlePlayerMode;

  public ServerConnectionPane(GuiBase guiBase)
  {
    this(guiBase, false);
  }

  public ServerConnectionPane(GuiBase guiBase, boolean singlePlayerMode)
  {
    this.guiBase = guiBase;
    this.singlePlayerMode = singlePlayerMode;
    currentStage = Stage.DISCONNECTED;
    initGui();
  }

  private enum Stage
  {
    DISCONNECTED,
    CONNECTING,
    CONNECTED;
  }

  public void setHelloReceived(boolean received)
  {
    helloReceived = received;
    currentStage = Stage.CONNECTED;
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

    responseMessageLabel = new Label("");

    connectButton.setOnAction(this::buttonPressed);
    cancelButton.setOnAction(this::buttonPressed);

    serverBox.setAlignment(Pos.CENTER);
    serverBox.setHgap(4);
    serverBox.setVgap(4);
    serverBox.setPadding(new Insets(25, 25, 25, 25));
    serverBox.add(title, 0, 0, 2, 1);
    serverBox.add(new Label("Hostname: "), 0, 1);
    serverBox.add(nameField, 1, 1);
    serverBox.add(new Label("Port: "), 0, 2);
    serverBox.add(portField, 1, 2);
    serverBox.add(connectButton, 0, 3);
    serverBox.add(cancelButton, 1, 3);
    serverBox.add(responseMessageLabel, 0, 4, 2, 1);
    this.getChildren().add(rect);
    this.getChildren().add(serverBox);

    // Sets the default port to whatever is in the server api
    portField.setText(Integer.toString(ServerConstants.DEFAULT_PORT));


    if(singlePlayerMode) singlePlayerMode();
    else
    {
      this.setOnKeyPressed(this::keyPressed);
      this.setOnKeyReleased(this::keyReleased);
    }
  }

  private void singlePlayerMode()
  {
    title.setText("(Single Player - Wait)");
    nameField.setText("localhost");
    nameField.setDisable(true);
    portField.setDisable(true);
    serverBox.getChildren().remove(connectButton);
    serverBox.getChildren().remove(cancelButton);

    String hostNameString = nameField.getText();
    String portString = portField.getText();
    int portInt = getPortInt(portString);

    hostName = hostNameString;
    port = portInt;
    responseMessageLabel.setText(ATTEMPT_CONNECT_MSG);
    responseMessageLabel.setTextFill(Color.WHITE);
    connectToServerTimer = new Timer();
    connectToServerTimer.schedule(new ScheduledTimer(this, 1000, 10), 0, REFRESH_INTERVAL);
    currentStage = Stage.CONNECTING;
  }

  private void buttonPressed(ActionEvent event)
  {
    Button source = (Button) event.getSource();
    System.out.println("Button pressed");

    if(source == connectButton)
    {
      String hostNameString = nameField.getText();
      String portString = portField.getText();
      int portInt = getPortInt(portString);

      System.out.println("Attempting to connect:");
      System.out.println("\tHostname: " + hostNameString);
      System.out.println("\tPort: " + portInt);

      if(portInt == -1)
      {
        responseMessageLabel.setText(INVALID_PORT_MSG);
        responseMessageLabel.setTextFill(Color.RED);
      }
      else
      {
        hostName = hostNameString;
        port = portInt;
        responseMessageLabel.setText(ATTEMPT_CONNECT_MSG);
        responseMessageLabel.setTextFill(Color.WHITE);
        connectToServerTimer = new Timer();
        connectToServerTimer.schedule(new ScheduledTimer(this, CONNECTION_INTERVAL, MAX_ATTEMPT_CONNECT), 0, REFRESH_INTERVAL);
        currentStage = Stage.CONNECTING;
      }
    }
    else if(source == cancelButton)
    {
      System.out.println("Cancel pressed");
      guiBase.serverConnectionPaneCancelled();
    }
  }

  private void keyPressed(KeyEvent event)
  {
    KeyCode keyCode = event.getCode();
    if(keyCode == KeyCode.ENTER)
    {
      System.out.println("Enter Pressed");
    }
  }

  private void keyReleased(KeyEvent event)
  {
    KeyCode keyCode = event.getCode();
    if(keyCode == KeyCode.ENTER)
    {
      System.out.println("Enter Released");
      connectButton.fire();
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

  public void sendConnection()
  {
    guiBase.attemptToConnectServer(hostName, port);
  }

  public void updateMessageStatus(long timeDiff, int currentAttempt)
  {
    int timeDisplay = (int) timeDiff / 1000;
    if(currentAttempt == 0)
    {
      responseMessageLabel.setText(String.format(firstAttemptMessage, timeDisplay));
    }
    else
    {
      responseMessageLabel.setText(String.format(retryAttepmtMessage, currentAttempt, timeDisplay));
    }
  }

  public void stopTimer()
  {
    responseMessageLabel.setText(UNABLE_CONNECT_MSG);
    connectToServerTimer.cancel();
  }

  private class ScheduledTimer extends TimerTask
  {
    private ServerConnectionPane reference;

    private boolean hasStarted = false;

    private int maxAttempts;
    private long attemptInterval;
    private long attemptStart;
    private int currentAttempt;

    public ScheduledTimer(ServerConnectionPane reference, long attemptInterval, int maxAttempts)
    {
      this.reference = reference;
      this.maxAttempts = maxAttempts;
      this.attemptInterval = attemptInterval;
      currentAttempt = 0;
    }

    @Override
    public void run()
    {
      Platform.runLater(this::attemptConnection);
    }

    private void attemptConnection()
    {
      long timeDiff = System.currentTimeMillis() - attemptStart;
      if(helloReceived) connectToServerTimer.cancel();
      if(!hasStarted)
      {
        attemptStart = System.currentTimeMillis();
        hasStarted = true;
        sendConnection();
        reference.updateMessageStatus(0, currentAttempt);
      }
      else
      {
        if(timeDiff > attemptInterval)
        {
          currentAttempt++;
          if(currentAttempt > maxAttempts) reference.stopTimer();
          else
          {
            attemptStart = System.currentTimeMillis();
            reference.sendConnection();
            reference.updateMessageStatus(0, currentAttempt);
          }
        }
        else
        {
          reference.updateMessageStatus(timeDiff, currentAttempt);
        }
      }
    }

    private void sendConnection() { reference.sendConnection(); }
  }
}
