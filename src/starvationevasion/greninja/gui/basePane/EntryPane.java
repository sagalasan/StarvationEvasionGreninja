package starvationevasion.greninja.gui.basePane;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import starvationevasion.greninja.gui.GuiBase;

/**
 * Entry pane where the player chooses game mode.
 * @author Justin Thomas
 * @author Erin Sosebee
 */
public class EntryPane extends StackPane
{
  private Button solo;
  private Button multiplayer;
  private Button testing;
  private Button quit;
  private Button serverSelected;
  private GuiBase base;
  private BorderPane gameSelectionPane;
  private VBox basePane;

  private boolean helloMessageReceived = false;
  private boolean DEBUG = true;


  public EntryPane(GuiBase gui)
  {
    base = gui;
    setAlignment(this, Pos.CENTER);
    gameSelectionPane = new BorderPane();
    basePane = new VBox();

    testing = new Button("Testing");
    solo = new Button("Single Player");
    multiplayer = new Button("Multiplayer");
    quit = new Button("Quit");

    testing.setOnAction(this::buttonPressed);
    solo.setOnAction(this::buttonPressed);
    multiplayer.setOnAction(this::buttonPressed);
    quit.setOnAction(this::buttonPressed);

//    HBox titlePane = new HBox();
//    titlePane.setAlignment(Pos.TOP_CENTER);
//    titlePane.getChildren().add(title);

    VBox gameInfoBox = new VBox(10);
    gameInfoBox.setAlignment((Pos.CENTER));
    Label title = new Label("Starvation Evasion");
    title.setId("gameTitle");
    Label instructions = new Label("Please select what type of game you want to play.");
    gameInfoBox.getChildren().addAll(title, instructions);

    VBox buttonBox = new VBox(5);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.getChildren().addAll(testing, solo, multiplayer, quit);

    basePane.setSpacing(10);
    basePane.setAlignment(Pos.CENTER);
    basePane.getChildren().addAll(gameInfoBox, buttonBox);

//    basePane.setSpacing(1.5);
//    basePane.getChildren().add(new Label("Entry Pane, choose a game type."));
//    basePane.getChildren().add(testing);
//    basePane.getChildren().add(solo);
//    basePane.getChildren().add(multiplayer);
//    basePane.getChildren().add(quit);
//    basePane.setAlignment(Pos.CENTER);
//    gameSelectionPane.setTop(titlePane);
//    gameSelectionPane.setCenter(buttonBox);
//    getChildren().add(gameSelectionPane);
    getChildren().add(basePane);
  }

  public void buttonPressed(ActionEvent e)
  {
    Button source = (Button)e.getSource();
    if(source == solo)
    {
      if (DEBUG) System.out.println("Pressed Solo");
      base.beginSinglePlayer();
    }
    else if(source == testing)
    {
      if (DEBUG) System.out.println("Pressed Testing.");
      base.beginTestingGame();
    }
    else if(source == multiplayer)
    {
      if (DEBUG) System.out.println("Pressed Multiplayer");
      //createServerSelectDialog();
      base.multiPlayerSelected();
    }
    else if(source == quit)
    {
      if (DEBUG) System.out.println("Pressed Quit");
      base.exitGame();
    }
  }
}
