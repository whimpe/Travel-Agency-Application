/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanflight.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import cleanflight.logic.CleanFlight;
import java.awt.Color;
import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class StartWindowController implements Initializable {

  private CleanFlight model;
    
  @FXML
  private Button customersBtn;
  @FXML
  private Button addcustomersBtn;
  @FXML
  private Button BookingBtn;
  @FXML
  private Button InternalReportBtn;
  @FXML
  private Button CustomerReportBtn;  
  @FXML
  private Button checkflightsBtn;
  @FXML  
  private Button AllFlightsBtn;
  @FXML  
  private Button deletecustomerBtn; 
  @FXML  
  private Button UpdCusBtn;  
  @FXML
  private AnchorPane dataPane;
  @FXML
  private ImageView imageView;
  @FXML  
  private Button Mapsbtn;  

  
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
   model = CleanFlight.getInstance();
   File file = new File("src/cleanflight/airplane.jpg");
   Image image = new Image(file.toURI().toString());
   imageView.setImage(image);
   
  }  


  @FXML
  private void loadMaps(ActionEvent event) {
    try {
      AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/cleanflight/gui/Maps.fxml"));  //stelt pane in op listview van de customers
      dataPane.getChildren().setAll(pane);      //vervangt het vorige datapane 
      
      
    } catch (IOException ex) {
      Logger.getLogger(StartWindowController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }   
    
       
 
 @FXML
  private void loadAddCustomerPane(ActionEvent event) {
    try {
      AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/cleanflight/gui/CustomerAdd.fxml"));  //stelt pane in op listview van de customers
      dataPane.getChildren().setAll(pane);      //vervangt het vorige datapane 
      
      
    } catch (IOException ex) {
      Logger.getLogger(StartWindowController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  @FXML
  private void loaddeletecustomer(ActionEvent event) {
    try {
      AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/cleanflight/gui/DeleteCustomer.fxml"));  //stelt pane in op listview van de customers
      dataPane.getChildren().setAll(pane);      //vervangt het vorige datapane 
      
      
    } catch (IOException ex) {
      Logger.getLogger(StartWindowController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  @FXML
  private void loadCustomerPane(ActionEvent event) {
    try {
      
      
      AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/cleanflight/gui/CustomerList.fxml"));
      dataPane.getChildren().setAll(pane);
      
      
    } catch (IOException ex) {
      Logger.getLogger(StartWindowController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  
  @FXML
    private void loadBookingsPane(ActionEvent event) {
    try {
      
      
      AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/cleanflight/gui/AllBookings.fxml"));
      dataPane.getChildren().setAll(pane);
      
      
    } catch (IOException ex) {
      Logger.getLogger(StartWindowController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  @FXML
    private void loadInternalReportPane(ActionEvent event) {
    try {
      AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/cleanflight/gui/InternalReport.fxml"));
      dataPane.getChildren().setAll(pane);
      
    } catch (IOException ex) {
      Logger.getLogger(StartWindowController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
   
   @FXML
    private void loadCheckFlights(ActionEvent event) {
    try {            
      AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/cleanflight/gui/CheckFlight.fxml"));
      dataPane.getChildren().setAll(pane);          
      
      
    } catch (IOException ex) {
      Logger.getLogger(StartWindowController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
    
    
    
  @FXML
  private void loadAllFlightsPane(ActionEvent event) {
    try {
      
      
      AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/cleanflight/gui/AllFlights.fxml"));
      dataPane.getChildren().setAll(pane);
      
      
      
    } catch (IOException ex) {
      Logger.getLogger(StartWindowController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }    
   
  @FXML
    private void loadCustomerReportPane(ActionEvent event) {
    try {
      
      
      AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/cleanflight/gui/CustomerReport.fxml"));
      dataPane.getChildren().setAll(pane);
      
      
    } catch (IOException ex) {
      Logger.getLogger(StartWindowController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
 @FXML
    private void loadUpdCusPane(ActionEvent event) {
    try {
      
      
      AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/cleanflight/gui/UpdateDeleteCustomer.fxml"));
      dataPane.getChildren().setAll(pane);
      
      
    } catch (IOException ex) {
      Logger.getLogger(StartWindowController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

}

