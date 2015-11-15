package starvationevasion.greninja.gui.componentPane;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Pane that shows the time remaining in the current phase.  This is not
 * guaranteed to match actual phase time.  Only to be used as a visual
 * representation for the player.
 */
public class TimerPane extends Pane
{

  private final Color timerGreen; //basic green color
  private Color timerDefaultColor; //color that this timer will remain as
  private Label timeLabel;
  private String timeRemaining;
  private Color timerColor; //current color

  /**
   * Basic constructor that initializes default green color.
   */
  public TimerPane()
  {
    timerGreen = new Color(0.0, 0.9, 0.0, 1.0);
  }

  /**
   * Start and add the timer visualization.
   */
  public void initPhaseTimer(String initialTime)
  {
    timeRemaining = initialTime;
    timeLabel = new Label(timeRemaining);
    timerDefaultColor = timerGreen;
    timerColor = timerDefaultColor;
    timeLabel.setTextFill(timerGreen);
    getChildren().add(timeLabel);
    Timeline timerCountdown = new Timeline(new KeyFrame(Duration.seconds(0.5),
        new EventHandler<ActionEvent>()
        {
          @Override
          public void handle(ActionEvent event)
          {
            timeLabel.setText(timeRemaining);
            timeLabel.setTextFill(timerColor);
          }
        }));
    timerCountdown.setCycleCount(Timeline.INDEFINITE);
    timerCountdown.play();
  }

  /**
   * Set the timer color.
   * @param minuteMark        whole minutes left in phase
   */
  public void setTimerColor(int minuteMark)
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
  public void updateTimeRemaining(String timeRemaining)
  {
    this.timeRemaining = timeRemaining;
  }
}
