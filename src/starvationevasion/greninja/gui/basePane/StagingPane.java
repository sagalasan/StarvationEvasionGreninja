package starvationevasion.greninja.gui.basePane;


import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import starvationevasion.common.EnumRegion;
import javafx.scene.control.Label;
import starvationevasion.common.messages.AvailableRegions;
import starvationevasion.common.messages.ReadyToBegin;
import starvationevasion.greninja.gui.GuiBase;
import starvationevasion.greninja.gui.GuiTimerSubscriber;
import starvationevasion.greninja.gui.MapHolder;
import starvationevasion.greninja.gui.componentPane.ClickableMap;
import starvationevasion.greninja.model.PlayerInterface;

import javax.smartcardio.Card;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Select region, wait for other players to join.
 * TODO Just testing right now, finalize.
 * @author Justin Thomas
 */
public class StagingPane extends StackPane implements MapHolder, GuiTimerSubscriber
{
  private GuiBase base;
  private BorderPane mainPane = new BorderPane();
  private VBox basePane;
  private String selectedRegion;
  private Label regionSelectedLabel;
  private ClickableMap map;
  private AvailableRegions availableRegions;

  private Label countdownMessage;
  private String cString = "Game will begin in... %ds";
  private Timer timer;
  private long timeLeft;
  //private CardController card;

  public StagingPane(GuiBase gui)
  {
    base = gui;
    availableRegions = null;
    regionSelectedLabel = new Label(selectedRegion);

    BorderPane topPane = new BorderPane();
    topPane.setMinHeight(50);
    topPane.setId("topLayout");

    // Draw divider
    Label divider = new Label();
    divider.setId("divider");
    divider.setMaxHeight(1);
    divider.setMinHeight(1);
    divider.setMinWidth(Screen.getPrimary().getBounds().getWidth());

    VBox titleBox = new VBox();
    titleBox.setAlignment(Pos.CENTER);
    Label title = new Label("Staging Phase: Choose your region.");
    title.setAlignment(Pos.CENTER);
    titleBox.getChildren().add(title);

    topPane.setCenter(titleBox);
    topPane.setBottom(divider);


    map = new ClickableMap("staging");
    map.setContainingPane(this);
    mainPane.setTop(topPane);
    mainPane.setCenter(map);
    getChildren().add(mainPane);

    //EXAMPLE OF HOW TO GREY OUT, JUST SETS THE OPAQUENESS
    //true to set grey
    //false to set back to full colored
    //map.greyOut(EnumRegion.CALIFORNIA, true);
    //map.greyOut(EnumRegion.CALIFORNIA, false);

    // TODO: add regionSelectedLabel and countdownMessage?
    countdownMessage = new Label();
  }

  /**
   * When interactive map is clicked, set region selected to that region.
   * @param region        region on interactive map that was clicked.
   */
  public void regionClicked(EnumRegion region)
  {
    base.regionSelected(region);
  }

  /**
   * When mouse enters a region on the interactive map, update the region selected
   * string.
   * @param region        EnumRegion entered.
   */
  public void regionEntered(EnumRegion region)
  {
    selectedRegion = region.toString();
  }

  /**
   * When mouse exits region, clear region selected string.
   * @param region        region exited.  Not used.
   */
  public void regionExited(EnumRegion region)
  {
    selectedRegion = "None.";
  }

  /**
   * Sets the available regions for the player to choose from.
   * @param availableRegions  the regions that are available to the player.
   * @param player            the player currently selecting regions.
   */
  public void setAvailableRegions(AvailableRegions availableRegions, PlayerInterface player)
  {
    this.availableRegions = availableRegions;
  }

  /**
   * Implements GuiTimerSubscriber.  Update text on region label.
   */
  public void timerTick()
  {
    regionSelectedLabel.setText(selectedRegion);
    if(availableRegions != null)
    {
      //map.updateAvailableRegions(availableRegions);
    }
  }

  /**
   * Locks a region and prevents a player from selecting it if it has already been selected by another player.
   * @param region
   */
  public void lock(EnumRegion region)
  {
    System.out.println("Locking staging pane");
    map.greyOut(region, true);
    //regionClicked(region);
  }

  /**
   * Starts the countdown before the server starts up a game.
   * @param readyToBegin  message saying if all regions have been taken or if someone unselected a region.
   */
  public void startCountdown(ReadyToBegin readyToBegin)
  {
    long serverStart = readyToBegin.gameStartServerTime;
    long serverCurrent = readyToBegin.currentServerTime;
    long start = System.currentTimeMillis();
    long diff = (serverStart - serverCurrent) * 1000;
    System.out.println("diff: " + diff);
    timeLeft = (diff - (System.currentTimeMillis() - start)) / 1000;
    timer = new Timer();
    timer.schedule(new TimerTask()
    {
      @Override
      public void run()
      {
        Platform.runLater(() ->
        {
          timeLeft = (diff - (System.currentTimeMillis() - start)) / 1000;
          countdownMessage.setText(String.format(cString, timeLeft));
          if(timeLeft < 0)
          {
            timer.cancel();
            //countdownMessage.setText("Waiting for simulator to load...");
            countdownMessage.setText("Timer ended, waiting for server...");
          }
        });
      }
    }, 0, 1000);
  }

  /**
   * Ends the timer and sends a message to the console telling the player that it is waiting for the simulator to load.
   */
  public void beginGameMessageReceived()
  {
    if(!base.isTesting())
    {
      timer.cancel();
      countdownMessage.setText("Waiting for simulator to load...");
    }
  }
}
