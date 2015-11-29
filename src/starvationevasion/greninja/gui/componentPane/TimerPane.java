package starvationevasion.greninja.gui.componentPane;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import starvationevasion.greninja.clientCommon.GuiStyles;

/**
 * Pane that shows the time remaining in the current phase.  This is not
 * guaranteed to match actual phase time.  Only to be used as a visual
 * representation for the player.
 */
public class TimerPane extends StackPane
{
  private final Color timerGreen; //basic green color
  private Color timerDefaultColor; //color that this timer will remain as
  private Label timeLabel;
  //time remaining is the current time string, phase length is the default time
  //string
  private String timeRemaining, phaseLength;
  private Color timerColor; //current color
  //keeps track of the time to be shown. [0] = minutes, [1] = seconds
  private Integer[] timeArray;
  //timeline controls the countdown.
  private Timeline timeline;
  //default minutes for the timer.
  private int phaseMinutes;
  //default seconds for the timer.
  private int phaseSeconds;

  /**
   * Basic constructor that initializes default green color.
   */
  public TimerPane()
  {
    timerGreen = new Color(0.0, 0.9, 0.0, 1.0);
  }

  /**
   * Start and add the timer visualization.
   * @param minutes       number of minutes for the initial value.
   * @param seconds       number of seconds for the initial value.
   */
  public void initPhaseTimer(int minutes, int seconds)
  {
    timeArray = new Integer[2];
    timeArray[0] = minutes;
    timeArray[1] = seconds;
    phaseMinutes = minutes;
    phaseSeconds = seconds;
    phaseLength = makeTimeString();
    timeRemaining = new String(phaseLength);
    timeLabel = new Label(timeRemaining);
    timerDefaultColor = timerGreen;
    timerColor = timerDefaultColor;
    timeLabel.setTextFill(timerGreen);
    timeLabel.setFont(new Font(16));
//    timeLabel.setStyle(GuiStyles.paddedGray());
    getChildren().add(timeLabel);

    //setup timeline for timer.  counts down once a second until timelimit hit.
    timeline = new Timeline();
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.getKeyFrames().add(
        new KeyFrame(Duration.seconds(1),
            new EventHandler<ActionEvent>()
            {
              @Override
              public void handle(ActionEvent e)
              {
                updateRemainingTime();
                updateTimeRemainingString(makeTimeString());
                setTimerColor(timeArray[0]);
                updateTimeLabel();
                if (timeArray[0] <= 0 && timeArray[1] <= 0)
                {
                  timeline.stop();
                }
              }
            }));
    //timeline.playFromStart();
  }

  /**
   * Start timer.
   */
  public void startTimer()
  {
    if(timeline != null)
    {
      setTimerColor(timeArray[0]);
      timeline.playFromStart();
    }
  }

  /**
   * Stop timer.
   */
  public void stopTimer()
  {
    if(timeline != null)
    {
      timeline.stop();
    }
  }

  /**
   * Set timer back to default values.
   */
  public void resetTimer()
  {
    timeRemaining = new String(phaseLength);
    timeArray[0] = phaseMinutes;
    timeArray[1] = phaseSeconds;
  }

  /**
   * Set text and color of timer label.
   */
  public void updateTimeLabel()
  {
    timeLabel.setText(timeRemaining);
    timeLabel.setTextFill(timerColor);
  }

  /**
   * Set the timer color.
   * @param minuteMark        whole minutes left in phase
   */
  private void setTimerColor(int minuteMark)
  {
    if(minuteMark < 1)
    {
      timerColor = Color.RED;
    }
    else if(minuteMark < 2)
    {
      timerColor = Color.ORANGE;
    }
    else
    {
      timerColor = timerDefaultColor;
    }
  }

  /**
   * Update remaining time string.
   * @param timeRemaining       String representing remaining time.
   */
  private void updateTimeRemainingString(String timeRemaining)
  {
    this.timeRemaining = timeRemaining;
  }

  /**
   * Convert timeRemaining into string.
   * @return        string representation of time. "m:ss"
   */
  private String makeTimeString()
  {
    if(timeArray[0] < 2)//check and update color.
    {
      setTimerColor(timeArray[0]);
    }
    StringBuilder timeString = new StringBuilder();
    timeString.append(timeArray[0]);
    timeString.append(':');

    if (timeArray[1] < 10)
    {
      timeString.append(0);
    }
    timeString.append(timeArray[1]);
    return timeString.toString();
  }

  /**
   * Return array with current time remaining.
   * @return        int array of size two representing minutes and seconds left.
   */
  private void updateRemainingTime()
  {
    if(timeArray[1] <= 0)
    {
      timeArray[1] = 59;
      timeArray[0]--;
    }
    else
    {
      timeArray[1]--;
    }
  }
}
