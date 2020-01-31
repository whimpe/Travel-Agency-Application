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
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 *
 * @author himpe
 */
public class CustomerListViewController  {
  
  private CleanFlight model;
  
  @FXML
  private ListView<Customer> customerListView;
  private ObservableList<Customer> allcustomers; 
  
  
  
  public void initialize() {
    model = CleanFlight.getInstance();
    
    allcustomers = FXCollections.observableArrayList(model.getCustomers());       
    customerListView.setItems(allcustomers); 
                 
  }  

    public void addCustomer(Customer customer) {
        allcustomers.add(customer);
    }
    public void deleteCustomer(Customer customer) {
        allcustomers.remove(customer);
    }


 
  
}
