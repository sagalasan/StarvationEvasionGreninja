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

  private final Color timerGreen;
  private Color timerDefaultColor;
  private Label timeLabel;
  private String timeRemaining;
  private Color timerColor;

  public TimerPane(String initialTime)
  {
    timerGreen = new Color(0.0, 0.9, 0.0, 1.0);
    timeRemaining = initialTime;
    timeLabel = new Label(timeRemaining);
    timerDefaultColor = timerGreen;
    timerColor = timerDefaultColor;
    timeLabel.setTextFill(timerGreen);
    getChildren().add(timeLabel);
  }

  /**
   * Start and add the timer visualization.
   */
  public void initPhaseTimer()
  {
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
