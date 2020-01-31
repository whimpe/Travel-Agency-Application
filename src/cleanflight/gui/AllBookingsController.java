/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanflight.gui;

import cleanflight.logic.Booking;
import cleanflight.logic.CleanFlight;
import cleanflight.logic.Customer;
import cleanflight.logic.Flight;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @authors Willem Himpe & Thomas Mathijs 
 */
public class AllBookingsController implements Initializable {
    
    private CleanFlight model;
    
    @FXML
    private AnchorPane dataPane;
  
    
    @FXML
    private ListView<Booking> BookingListView;
    private ObservableList<Booking> allbookings;
     /**
     * Initializes the controller class.
     */
    @Override
     public void initialize(URL location, ResourceBundle resources) {
    model = CleanFlight.getInstance();
    
    allbookings = FXCollections.observableArrayList(model.getBookings());       
    BookingListView.setItems(allbookings); 
        
    
  }  

    public void addBooking(Booking b) {
        allbookings.add(b);
    }
    public void deleteCustomer(Booking b) {
        allbookings.remove(b);
    }
    
    
}