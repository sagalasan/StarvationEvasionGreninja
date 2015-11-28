package starvationevasion.greninja.gui.basePane;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import starvationevasion.common.messages.Login;
import starvationevasion.greninja.gui.GuiBase;

import javax.smartcardio.Card;

/**
 * Entry pane where the player chooses game mode.
 * @author Justin Thomas(jthomas105@unm.edu)
 */
public class EntryPane extends StackPane
{
  private Button solo;
  private Button multiplayer;
  private Button quit;
  private Button serverSelected;
  private ButtonControl buttonListener;
  private GuiBase base;
  private VBox basePane;

  private boolean helloMessageReceived = false;


  public EntryPane(GuiBase gui)
  {
    base = gui;
    setAlignment(this, Pos.CENTER);
    basePane = new VBox();

    buttonListener = new ButtonControl(this);
    solo = new Button("Single Player");
    solo.setOnAction(buttonListener);
    multiplayer = new Button("Multiplayer");
    multiplayer.setOnAction(buttonListener);
    quit = new Button("Quit");
    quit.setOnAction(buttonListener);
    basePane.setSpacing(1.5);
    basePane.getChildren().add(new Label("Entry Pane, choose a game type."));
    basePane.getChildren().add(solo);
    basePane.getChildren().add(multiplayer);
    basePane.getChildren().add(quit);
    basePane.setAlignment(Pos.CENTER);
    getChildren().add(basePane);
  }

  public void buttonPressed(ActionEvent e)
  {
    Button source = (Button)e.getSource();
    if(source == solo)
    {
      System.out.println("Pressed Solo");
      base.beginSinglePlayer();
    }
    else if(source == multiplayer)
    {
      System.out.println("Pressed Multiplayer");
      //createServerSelectDialog();
      base.multiPlayerSelected();
    }
    else if(source == quit)
    {
      System.out.println("Pressed Quit");
      base.exitGame();
    }
  }


  /**
   * Show the login dialog and collect login info.  Send as a login message
   * back to control.
   */
  public void showLoginDialog()
  {
    StackPane dialog = new StackPane();
    GridPane loginBox = new GridPane();
    Rectangle rect = new Rectangle(500, 500, Color.GRAY);
    Button ok = new Button("OK");
    TextField nameField = new TextField();
    nameField.setMaxWidth(Double.MAX_VALUE);
    PasswordField passwordField = new PasswordField();
    passwordField.setMaxWidth(Double.MAX_VALUE);
    ok.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e)
      {
        System.out.println("OK");
        //TODO get name and password from user.
        base.loginInfoSent(nameField.getText(), passwordField.getText());
        //close dialog
        getChildren().remove(dialog);
      }
    });
    Button cancel = new Button("Cancel");
    cancel.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent e)
      {
        System.out.println("Cancelled");
        //close dialog
        getChildren().remove(dialog);
      }
    });
    loginBox.setAlignment(Pos.CENTER);
    loginBox.setHgap(4);
    loginBox.setVgap(4);
    loginBox.setPadding(new Insets(25, 25, 25, 25));
    loginBox.add(new Label("Please enter your name and password."), 0, 0, 2, 1);
    loginBox.add(new Label("Name: "), 0, 1);
    loginBox.add(nameField, 1, 1);
    loginBox.add(new Label("Password: "), 0, 2);
    loginBox.add(passwordField, 1, 2);
    loginBox.add(ok, 0, 3);
    loginBox.add(cancel, 1, 3);
    dialog.getChildren().add(rect);
    dialog.getChildren().add(loginBox);
    getChildren().add(dialog);
  }

  /**
   * Creates and shows the server select dialog.
   * TODO get server name and port and send to base.  Needs a reworking.
   */
  public void createServerSelectDialog()
  {
    StackPane dialog  = new StackPane();
    GridPane serverBox = new GridPane();
    Rectangle rect    = new Rectangle(500, 500, Color.GRAY);

    Button connectButton = new Button("Connect");
    Button cancelButton  = new Button("Cancel");

    TextField nameField = new TextField();
    TextField portField = new TextField();
    nameField.setMaxWidth(Double.MAX_VALUE);
    portField.setMaxWidth(Double.MAX_VALUE);

    connectButton.setOnAction(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent event)
      {
        String serverName = nameField.getText();
        String portName   = portField.getText();
        System.out.println("connectButton pressed");
        System.out.println("Sending connect message with:");
        System.out.println("\tHostname: " + serverName);
        System.out.println("\tport: " + portName);
        int port = Integer.parseInt(portName);
        base.attemptToConnectServer(serverName, port);
      }
    });


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
    dialog.getChildren().add(rect);
    dialog.getChildren().add(serverBox);
    getChildren().add(dialog);

    /*Rectangle rect = new Rectangle(500, 500);
    rect.setFill(Color.GRAY);
    VBox serverSelectDialog = new VBox();
    serverSelected = new Button("OK");
    serverSelected.setOnAction(buttonListener);
    serverSelectDialog.getChildren().add(new Label("Please enter the server name."));
    //Form goes in.
    serverSelectDialog.getChildren().add(serverSelected);
    serverSelectDialog.setAlignment(Pos.CENTER);

    getChildren().addAll(rect, serverSelectDialog);
    */
  }

  private class ButtonControl implements EventHandler<ActionEvent>
  {
    private EntryPane parentPane;

    public ButtonControl(EntryPane parentPane)
    {
      this.parentPane = parentPane;
    }

    @Override
    public void handle (ActionEvent e)
    {
      parentPane.buttonPressed(e);
    }
  }
}
