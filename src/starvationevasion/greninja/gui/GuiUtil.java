package starvationevasion.greninja.gui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Static utility methods for building the gui.
 */
public class GuiUtil
{
  /**
   * Create a spring spacer.  Similar to swing horizontal glue.
   * @return        Region that expands horizontally.
   */
  public static Region getHBoxSpring()
  {
    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    return spacer;
  }

  /**
   * Create a spring spacer.  Similar to swing vertical glue.
   * @return      Region that expands vertically
   */
  public static Region getVBoxSpring()
  {
    Region spacer = new Region();
    VBox.setVgrow(spacer, Priority.ALWAYS);
    return spacer;
  }
}
