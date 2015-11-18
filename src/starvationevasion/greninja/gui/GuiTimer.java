package starvationevasion.greninja.gui;

import javafx.animation.AnimationTimer;

/**
 * Core event timer for gui ops.
 */
public class GuiTimer extends AnimationTimer
{
  private GuiBase base;

  public GuiTimer(GuiBase base)
  {
    this.base = base;
  }
  @Override
  public void handle(long now)
  {
    base.timerTick();//
  }
}
