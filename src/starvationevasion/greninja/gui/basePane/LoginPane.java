package starvationevasion.greninja.gui.basePane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import starvationevasion.common.messages.LoginResponse;
import starvationevasion.greninja.gui.GuiBase;

/**
 * Created by sagalasan on 11/29/15.
 */
public class LoginPane extends StackPane
{
  private GuiBase guiBase;

  private GridPane loginBox;

  private Button okButton;
  private Button cancelButton;

  private Label title = new Label("Please enter your name and password.");
  private TextField userNameField;
  private PasswordField passwordField;
  private String salt = "llllllll";
  private Label responseMessage;

  private boolean singlePlayerMode;

  public LoginPane(GuiBase guiBase)
  {
    this(guiBase, false);
  }

  public LoginPane(GuiBase guiBase, boolean singlePlayerMode)
  {
    this.guiBase = guiBase;
    this.singlePlayerMode = singlePlayerMode;
    initGui();
  }

  private void initGui()
  {
    loginBox = new GridPane();
    Rectangle rect = new Rectangle(500, 500, Color.GRAY);
    okButton = new Button("OK");
    cancelButton = new Button("Cancel");

    userNameField = new TextField();
    passwordField = new PasswordField();
    responseMessage = new Label("");
    userNameField.setMaxWidth(Double.MAX_VALUE);
    passwordField.setMaxWidth(Double.MAX_VALUE);

    okButton.setOnAction(this::buttonPressed);
    cancelButton.setOnAction(this::buttonPressed);

    loginBox.setAlignment(Pos.CENTER);
    loginBox.setHgap(4);
    loginBox.setVgap(4);
    loginBox.setPadding(new Insets(25, 25, 25, 25));
    loginBox.add(title, 0, 0, 2, 1);
    loginBox.add(new Label("Name: "), 0, 1);
    loginBox.add(userNameField, 1, 1);
    loginBox.add(new Label("Password: "), 0, 2);
    loginBox.add(passwordField, 1, 2);
    loginBox.add(okButton, 0, 3);
    loginBox.add(cancelButton, 1, 3);
    loginBox.add(responseMessage, 0, 4, 2, 1);
    this.getChildren().add(rect);
    this.getChildren().add(loginBox);

    if(singlePlayerMode) singlePlayerMode();
    else  this.setOnKeyReleased(this::keyReleased);
  }

  private void singlePlayerMode()
  {
    title.setText("Logging in please wait");
    userNameField.setText("user7");
    passwordField.setText("password");
    okButton.setDisable(true);
    cancelButton.setDisable(true);
    userNameField.setDisable(true);
    passwordField.setDisable(true);
    sendLoginInfo();
  }

  private void sendLoginInfo()
  {
    guiBase.loginInfoSent(userNameField.getText(), passwordField.getText(), salt);
  }

  public void setSalt(String salt)
  {
    this.salt = salt;
    System.out.println("Salt set: " + salt);
  }

  /**
   * TODO implement actual response handling
   * @param response
   */
  public void loginFailed(LoginResponse response)
  {
    responseMessage.setText("Login failed, try again");
    if(singlePlayerMode) sendLoginInfo();
  }

  private void buttonPressed(ActionEvent event)
  {
    Button source = (Button) event.getSource();
    System.out.println("Button pressed");

    if(source == okButton)
    {
      System.out.println("OK pressed");
      sendLoginInfo();
    }
    else if(source == cancelButton)
    {
      System.out.println("Cancel pressed");
    }
  }

  private void keyReleased(KeyEvent event)
  {
    KeyCode keyCode = event.getCode();
    if(keyCode == KeyCode.ENTER)
    {
      System.out.println("Enter Released");
      okButton.fire();
    }
  }
}
