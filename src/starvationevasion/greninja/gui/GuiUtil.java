package GuiUtil;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Static utility methods.
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
   * Same as above but vertical.
   * @return
   */
  public static Region getVBoxSpring()
  {
    Region spacer = new Region();
    VBox.setVgrow(spacer, Priority.ALWAYS);
    return spacer;
  }
}
