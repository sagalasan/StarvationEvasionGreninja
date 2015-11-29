package starvationevasion.greninja.gui.componentPane;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import starvationevasion.greninja.gui.GuiBase;

/**
 *
 */
public class TimerDisplay extends HBox
{
  private GuiBase base;
  private TimerPane timer;

  public TimerDisplay(GuiBase base, TimerPane timer)
  {
    this.base = base;
    this.timer = timer;
    setSpacing(5);
    setAlignment(Pos.CENTER);

    Label timerLabel = new Label("Time remaining: ");
    timerLabel.setId("timerLabel");

    getChildren().addAll(timerLabel, timer);
  }

}

