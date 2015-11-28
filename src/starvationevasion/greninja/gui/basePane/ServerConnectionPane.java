package starvationevasion.greninja.gui.basePane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import starvationevasion.greninja.gui.GuiBase;

/**
 * Created by Christiaan Martinez on 11/28/15.
 */
public class ServerConnectionPane extends StackPane
{
  private GuiBase guiBase;

  private Button connectButton;
  private Button cancelButton;
  private ButtonListener buttonListener;

  public ServerConnectionPane(GuiBase guiBase)
  {
    this.guiBase = guiBase;
  }

  public void buttonPressed(ActionEvent event)
  {

  }

  private class ButtonListener implements EventHandler<ActionEvent>
  {
    private ServerConnectionPane serverConnectionPane;

    public ButtonListener(ServerConnectionPane serverConnectionPane)
    {
      this.serverConnectionPane = serverConnectionPane;
    }

    @Override
    public void handle(ActionEvent event)
    {
      serverConnectionPane.buttonPressed(event);
    }
  }
}
