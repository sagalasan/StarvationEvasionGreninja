package starvationevasion.greninja.gui.basePane;


import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
 * @author Justin Thomas(jthomas105@unm.edu)
 */
public class StagingPane extends StackPane implements MapHolder, GuiTimerSubscriber
{
  private GuiBase base;
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
    basePane = new VBox();
    getChildren().add(basePane);
    basePane.getChildren().add(new Label("Staging Pane, select a region."));
    //selectedRegion = "None.";
    regionSelectedLabel = new Label(selectedRegion);

    basePane.getChildren().add(regionSelectedLabel);
    map = new ClickableMap("staging");
    map.setContainingPane(this);

    countdownMessage = new Label("");


    //EXAMPLE OF HOW TO GREY OUT, JUST SETS THE OPAQUENESS
    //true to set grey
    //false to set back to full colored
    //map.greyOut(EnumRegion.CALIFORNIA, true);
    //map.greyOut(EnumRegion.CALIFORNIA, false);

    basePane.getChildren().add(map);
    basePane.getChildren().add(countdownMessage);
  }

  /**
   * Setup Staging Pane.
   */
  /**
  public void initPane()
  {
    basePane = new VBox();
    getChildren().add(basePane);
    basePane.getChildren().add(new Label("Staging Pane, select a region."));
    //selectedRegion = "None.";
    regionSelectedLabel = new Label(selectedRegion);

    basePane.getChildren().add(regionSelectedLabel);
    map = new ClickableMap("staging");
    map.setContainingPane(this);


    //EXAMPLE OF HOW TO GREY OUT, JUST SETS THE OPAQUENESS
    //true to set grey
    //false to set back to full colored
    //map.greyOut(EnumRegion.CALIFORNIA, true);
    //map.greyOut(EnumRegion.CALIFORNIA, false);

    basePane.getChildren().add(map);
  }
   **/

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

  public void lock(EnumRegion region)
  {
    System.out.println("Locking staging pane");
    map.greyOut(region, true);
    //regionClicked(region);
  }

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
            countdownMessage.setText("Waiting for simulator to load...");
          }
        });
      }
    }, 0, 1000);
  }

  public void beginGameMessageReceived()
  {
    timer.cancel();
    countdownMessage.setText("Waiting for simulator to load...");
  }
}
