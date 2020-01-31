/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanflight.gui;

import cleanflight.db.DBException;
import cleanflight.db.DBFlight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import cleanflight.logic.Booking;
import cleanflight.logic.CleanFlight;
import cleanflight.logic.Flight;
import java.util.ArrayList;
import java.util.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;



/**
 * FXML Controller class
 *
 * @authors Willem Himpe & Thomas Mathijs 
 */
public class AllFlightsController  {

    private CleanFlight model;
    
    @FXML
    private ListView<Flight> AllFlightsListView;
    private ObservableList<Flight> allflights;
       
    @FXML
    private AnchorPane dataPane;
            
    /**
     * Initializes the controller class.
     */
     public void initialize() throws DBException {
        model = CleanFlight.getInstance();        
        
        allflights = FXCollections.observableArrayList(DBFlight.getFlights2());   
        //DBFlight.getFlights2()
        
        //model.getFlights()
        AllFlightsListView.setItems(allflights);   
        
        
        
    }    
    
}
