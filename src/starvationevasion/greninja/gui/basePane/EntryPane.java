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
  private VBox basePane;

  private boolean helloMessageReceived = false;
  private boolean DEBUG = true;

  /**
   * Creates the entry pane, allowing players to choose if they want to play single player or multiplayer.
   * @param gui
   */
  public EntryPane(GuiBase gui)
  {
    base = gui;
    setAlignment(this, Pos.CENTER);
    basePane = new VBox();

    // Create game title and game instructions
    VBox gameInfoBox = new VBox(10);
    gameInfoBox.setAlignment((Pos.CENTER));
    Label title = new Label("Starvation Evasion");
    title.setId("gameTitle");
    Label instructions = new Label("Please select what type of game you want to play.");
    gameInfoBox.getChildren().addAll(title, instructions);

    // Create buttons
    testing = new Button("Testing");
    solo = new Button("Single Player");
    multiplayer = new Button("Multiplayer");
    quit = new Button("Quit");

    testing.setMaxWidth(Double.MAX_VALUE);
    solo.setMaxWidth(Double.MAX_VALUE);
    multiplayer.setMaxWidth(Double.MAX_VALUE);
    quit.setMaxWidth(Double.MAX_VALUE);

    testing.setOnAction(this::buttonPressed);
    solo.setOnAction(this::buttonPressed);
    multiplayer.setOnAction(this::buttonPressed);
    quit.setOnAction(this::buttonPressed);

    VBox buttonBox = new VBox(5);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setMaxWidth(200);
    buttonBox.getChildren().addAll(testing, solo, multiplayer, quit);

    // Add nodes to base pane
    basePane.setSpacing(10);
    basePane.setAlignment(Pos.CENTER);
    basePane.getChildren().addAll(gameInfoBox, buttonBox);
    getChildren().add(basePane);
  }

  /**
   * Handles button presses.
   * @param e the ActionEvent to handle.
   */
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
