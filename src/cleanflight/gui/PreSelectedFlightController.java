/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanflight.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import cleanflight.logic.Customer;
import cleanflight.logic.CleanFlight;
import cleanflight.logic.Flight;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @authors Willem Himpe & Thomas Mathijs 
 */
public class PreSelectedFlightController implements Initializable {
    
    private CleanFlight model;
    
        
  @FXML
  private ListView<Flight> flightList;
  private ObservableList<Flight> selectedflights;

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        
        model = CleanFlight.getInstance();    
        selectedflights = FXCollections.observableArrayList(model.getPre_selected_flights());               
        flightList.setItems(selectedflights); 
    
    
    
    }    
    public void add(Flight f){
        selectedflights.add(f);        
    }
    
}



