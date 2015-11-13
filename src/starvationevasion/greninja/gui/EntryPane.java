package starvationevasion.greninja.gui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
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
      createServerSelectDialog();
    }
    else if(source == quit)
    {
      System.out.println("Pressed Quit");
      base.exitGame();
    }
    else if(source == serverSelected)
    {
      System.out.println("Server selected, validate please.");
      base.beginMultiPlayer();
    }
  }

  private void createServerSelectDialog()
  {
    Rectangle rect = new Rectangle(500, 300);
    rect.setFill(Color.GRAY);
    VBox serverSelectDialog = new VBox();
    serverSelected = new Button("OK");
    serverSelected.setOnAction(buttonListener);
    serverSelectDialog.getChildren().add(new Label("Please enter the server name."));
    //Form goes in.
    serverSelectDialog.getChildren().add(serverSelected);
    serverSelectDialog.setAlignment(Pos.CENTER);

    getChildren().addAll(rect, serverSelectDialog);
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
