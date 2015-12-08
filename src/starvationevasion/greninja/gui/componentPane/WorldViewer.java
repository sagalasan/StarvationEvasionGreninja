package starvationevasion.greninja.gui.componentPane;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import starvationevasion.vis.controller.EarthViewer;
import starvationevasion.vis.visuals.ResourceLoader;
import starvationevasion.vis.visuals.VisualizerLayout;


/**
 * Created by Jalen on 12/5/2015.
 */
public class WorldViewer extends VBox{

  //private GridPane topBar = new GridPane();
  //private GridPane leftBarGrid = new GridPane();
  //private GridPane rightBarGrid = new GridPane();
  //private GridPane centerGrid = new GridPane();



  //public  Label title = new Label("Year, World/Region \nPopulation and HDI");
  //private Label toggleEarthMode = new Label("Press tab to toggle between Earth sizes");


  private EarthViewer earthViewer;
  private Stage earthStage = new Stage();

  public WorldViewer()
  {
    //Earth Viewer takes two parameters, one is the desired radius of your mini Earth
    //and one is the desired radius of your large Earth
    //This was done so that each client could easily size the Earth to fit in with their GUI
    //Start rotate will put the earthViewer object in an automatic and continuous rotation (this is for the mini view)
    setPrefWidth(100);
    setMaxHeight(30);
    EarthViewer earthViewer = new EarthViewer(30, 140);
    //this.earthViewer = earthViewer;
    getChildren().add(earthViewer.updateMini());


    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();

    earthStage.setX(bounds.getMinX());
    earthStage.setY(bounds.getMinY());
    earthStage.setWidth(bounds.getWidth());
    earthStage.setHeight(bounds.getHeight());

    earthStage.initStyle(StageStyle.TRANSPARENT);



    setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        //visLayout = earthViewer.updateFull();
        VisualizerLayout visLayout = earthViewer.updateFull();
        visLayout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-background-radius: 0;");
        Scene earthScene = new Scene(visLayout, 700, 700);
        earthScene.setFill(Color.TRANSPARENT);
        earthStage.setScene(earthScene);
        earthStage.show();
        earthStage.toFront();

        earthScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            String eventPressed = event.getButton().toString();
            updateTheMiniGlobe();
            //earthViewer.updateMini();
            //on mouse click, will hide pane
            if (eventPressed.equals("PRIMARY")) {

              earthStage.hide();
            }
          }
        });

      }
    });

  }

  /**
   * updates the mini globe and adds back to display
   */
  private void updateTheMiniGlobe()
  {

    earthViewer = new EarthViewer(30, 140);
    getChildren().add(earthViewer.updateMini());

  }


}
