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
  private GuiBase base;
  private VBox basePane;

  private boolean helloMessageReceived = false;


  public EntryPane(GuiBase gui)
  {
    base = gui;
    setAlignment(this, Pos.CENTER);
    basePane = new VBox();

    solo = new Button("Single Player");
    multiplayer = new Button("Multiplayer");
    quit = new Button("Quit");

    solo.setOnAction(this::buttonPressed);
    multiplayer.setOnAction(this::buttonPressed);
    quit.setOnAction(this::buttonPressed);

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
}
