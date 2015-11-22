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
  private String timeRemaining, phaseLength;
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
    phaseLength = initialTime;
    timeRemaining = new String(initialTime);
    timeLabel = new Label(timeRemaining);
    timerDefaultColor = timerGreen;
    timerColor = timerDefaultColor;
    timeLabel.setTextFill(timerGreen);
    timeLabel.setFont(new Font(16));
    timeLabel.setStyle(GuiStyles.paddedGray());
    getChildren().add(timeLabel);
  }

  public void resetTimer()
  {
    timeRemaining = new String(phaseLength);
  }

  public void updateTimeLabel()
  {
    timeLabel.setText(timeRemaining);
    timeLabel.setTextFill(timerColor);
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
