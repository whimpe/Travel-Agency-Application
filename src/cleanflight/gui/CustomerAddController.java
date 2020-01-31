/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanflight.gui;


import javafx.application.Application; 
import javafx.scene.Scene; 
import javafx.scene.control.Button; 
import javafx.scene.layout.*; 
import javafx.event.ActionEvent; 
import javafx.event.EventHandler; 
import javafx.scene.control.*; 
import javafx.stage.Stage; 
import javafx.scene.control.Alert.AlertType; 
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;
import cleanflight.logic.CleanFlight;
import cleanflight.logic.Customer;
import cleanflight.db.DBCustomer;
import cleanflight.db.DBException;
import cleanflight.gui.CustomerListViewController;
import cleanflight.logic.Countries;
import cleanflight.logic.Flight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class CustomerAddController implements Initializable {
  
  private CleanFlight model;

  @FXML
  private TextField intPassportNr;
  @FXML
  private TextField txtFirst_Name;
  @FXML
  private TextField txtLast_Name;
  @FXML
  private ComboBox<String> cbCountry;
  private ObservableList<String> all_countries;
  
  @FXML
  private DatePicker  dpDob;

  @FXML
  private TextField txtEmail;
  @FXML
  private TextField txtPhone_nbr;
  @FXML
  private Button addCustomerBtn;
  
  /**
   * Initializes the controller class.
     * @param url
     * @param rb
   */
  
 
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    model = CleanFlight.getInstance();
    all_countries = FXCollections.observableArrayList();
        for(Countries obj : model.getAll_countries()) {
            String a2 = obj.getCountryName();           
            all_countries.add(a2);
            }
    cbCountry.setItems(all_countries);
   }
  
  @FXML
  private void addCustomer(ActionEvent event) throws IOException {
 
    try {
      model = CleanFlight.getInstance();
      String passportnr = intPassportNr.getText();
      String firstname = txtFirst_Name.getText();
      String lastname = txtLast_Name.getText();
      String country = cbCountry.getValue();
      System.out.println(country);
      int countrynr = model.getCountryNr(country);
      String dob = dpDob.getValue().toString();
      String email = txtEmail.getText();
      String phone_nbr = txtPhone_nbr.getText();
      
       Alert a = new Alert(AlertType.NONE);         
       if (passportnr.length()!=10){
           a.setAlertType(AlertType.ERROR); 
           a.setContentText("Passport number has to contain exactly 10 characters!");
           a.show(); 
           intPassportNr.setText(null);
           return;
            }
        Alert b = new Alert(AlertType.NONE);  
       if (phone_nbr.length()!=10){
           b.setAlertType(AlertType.ERROR); 
           b.setContentText("Phone number has to contain exactly 10 characters!");
           b.show(); 
           txtPhone_nbr.setText(null);
           return;
            }
                  
      Customer customer = new Customer(passportnr,firstname, lastname, countrynr, dob,email,phone_nbr);
      DBCustomer.addCustomer(customer);
      model.AddCustomer(customer);
      intPassportNr.setText(null);
      txtFirst_Name.setText(null);
      txtLast_Name.setText(null);
      cbCountry.setValue(null);
      dpDob.setValue(null);
      txtEmail.setText(null);
      txtPhone_nbr.setText(null);


      
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/cleanflight/gui/CustomerList.fxml"));    
      loader.load();                                                                                        
      CustomerListViewController controller = (CustomerListViewController) loader.getController();           
      controller.addCustomer(customer);                                                                     
   
      a.setAlertType(AlertType.CONFIRMATION); 
      a.setContentText("Customer is added to the database!");
      a.show(); 
            
     }catch(DBException ex){
      Logger.getLogger(CleanFlight.class.getName()).log(Level.SEVERE, null, ex);
      }
 
  }
  
  
}
