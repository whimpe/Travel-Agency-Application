/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanflight.gui;

import cleanflight.logic.CleanFlight;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Willem Himpe
 */
public class JavaFXApplication extends Application {
  
  private CleanFlight cleanflightModel;
  
  @Override
  public void start(Stage stage) throws Exception {    //stage is the opening of a window,,A Stage in JavaFX is a top-level container that hosts a Scene, which consists of visual elements.
    
    cleanflightModel = new CleanFlight();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/cleanflight/gui/StartWindow.fxml"));  //The FXML loader parses the FXML document, instantiates the nodes specified in the document, and builds the scene graph.
    Parent root = loader.load();
        
    Scene scene = new Scene(root);   //A scene represents the physical contents of a JavaFX application. It contains all the contents of a scene graph.
    stage.setScene(scene);    //starts the scene
    stage.show();
    stage.setMaximized(true);
  }


  public static void main(String[] args) {
    
    launch(args);   //starts the application
  }
  
}

