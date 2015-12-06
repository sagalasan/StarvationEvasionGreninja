package starvationevasion.greninja.gui.componentPane;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import starvationevasion.vis.controller.EarthViewer;

/**
 * Created by Jalen on 12/5/2015.
 */
public class WorldViewer extends VBox{

  private GridPane topBar = new GridPane();
  private GridPane leftBarGrid = new GridPane();
  //private GridPane rightBarGrid = new GridPane();
  //private GridPane centerGrid = new GridPane();



  //public  Label title = new Label("Year, World/Region \nPopulation and HDI");
  private Label toggleEarthMode = new Label("Press tab to toggle between Earth sizes");

  private EarthViewer earthViewer;


  public WorldViewer()
  {
    //Earth Viewer takes two parameters, one is the desired radius of your mini Earth
    //and one is the desired radius of your large Earth
    //This was done so that each client could easily size the Earth to fit in with their GUI
    //Start rotate will put the earthViewer object in an automatic and continuous rotation (this is for the mini view)
    setPrefWidth(100);
    setMaxHeight(30);
    EarthViewer earthViewer = new EarthViewer(30, 140);
    this.earthViewer = earthViewer;
    getChildren().add(earthViewer.updateMini());

  }

//The rest of these functions are layout things and not important



}
