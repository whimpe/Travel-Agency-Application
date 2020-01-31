/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanflight.gui;

/**
 *
 * @author Thomas
 */

import cleanflight.db.DBCountries;
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
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.Alert;
import javafx.util.converter.LocalDateStringConverter;
import javax.swing.JComboBox;

public class UpdateDeleteCustomerController implements Initializable{
  private CleanFlight model;
  

  @FXML
  private TextField firstn;
  @FXML
  private TextField lastn;
  @FXML
  private TextField pasnr;
  @FXML
  private TextField email;
  @FXML
  private TextField phonen;
  @FXML
  private ComboBox<String> country;
  private ObservableList<String> countrylist;
  @FXML
  private ComboBox<Customer> cuslist;
  private ObservableList<Customer> allcus;
  @FXML
  private DatePicker  dob;
  @FXML
  private Button updatecus;
  @FXML 
  private Button cusdata;
  @FXML
  private Button deletecus;
  
  public static final LocalDate LOCAL_DATE (String dateString){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    LocalDate localDate = LocalDate.parse(dateString, formatter);
    return localDate;
}

  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    model = CleanFlight.getInstance();
    countrylist = FXCollections.observableArrayList();
        for(Countries obj : model.getAll_countries()) {
            String a2 = obj.getCountryName();           
            countrylist.add(a2);
            }
    country.setItems(countrylist);
    //txtEmail.setText("hier email");
   
    allcus = FXCollections.observableArrayList(model.getCustomers());       
    cuslist.setItems(allcus);

  }
  
  @FXML
  private void updatecustomer(ActionEvent event) throws IOException {
 
    try {
        
      String passportnr = pasnr.getText();
      String firstname = firstn.getText();
      String lastname = lastn.getText();
      
      String country1 = country.getValue();
      int countrynr = model.getCountryNr(country1);      
      String dob1 = dob.getValue().toString();
      String email1 = email.getText();
      String phone_nbr = phonen.getText();
      Customer customer = new Customer(passportnr,firstname, lastname, countrynr, dob1,email1,phone_nbr);
      DBCustomer.updateCustomer(customer);
      
      
      Customer c = cuslist.getValue();
//      this.model.delete_customer(c);
//      this.model.AddCustomer(customer);
      this.model.update_customer(c, customer);

      
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/cleanflight/gui/CustomerList.fxml"));    
      loader.load();                                                                                        
      CustomerListViewController controller = (CustomerListViewController) loader.getController();          
      controller.addCustomer(customer);                                                       
      
      Alert a = new Alert(Alert.AlertType.NONE);         
      a.setAlertType(Alert.AlertType.CONFIRMATION); 
      a.setContentText("CUSTOMER is updated!");
      a.show(); 
      
      
      firstn.setText(null);   // +
      lastn.setText(null);
      pasnr.setText(null);
      email.setText(null);
      phonen.setText(null);
      dob.setValue(null);
      country.setValue(null);
      cuslist.setValue(null);
      
      }catch(DBException ex){
      Logger.getLogger(CleanFlight.class.getName()).log(Level.SEVERE, null, ex);
      }
    
    
     
      
  }
  @FXML
  private void loadData(ActionEvent event) throws IOException, DBException{
      model = CleanFlight.getInstance();
      Customer c = cuslist.getValue();
      firstn.setText(c.getFirstName());   // +
      lastn.setText(c.getLastName());
      pasnr.setText(c.getPassportNr());
      email.setText(c.getEmail());
      phonen.setText(c.getPhone());
      
      //to set the date 
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      String date = c.getDoB();	        
      LocalDate localDate = LocalDate.parse(date, formatter);           
      dob.setValue(localDate);
      
      country.setEditable(true);
      int country_int = c.getCountryNr();
      String countryname = model.getCountryName(country_int);
      country.setValue(countryname);
      
   
      
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/cleanflight/gui/CustomerList.fxml"));    
      loader.load();                                                                                        
      CustomerListViewController controller = (CustomerListViewController) loader.getController();          
      controller.addCustomer(c);                                                 
}
  
   public void deletecustomer(ActionEvent event) throws IOException {
 
    try {
        Customer customer = cuslist.getValue();
        DBCustomer.deleteCustomer(customer);                
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cleanflight/gui/CustomerList.fxml"));    
        loader.load();                                                                                        
        CustomerListViewController controller = (CustomerListViewController) loader.getController();           
        controller.deleteCustomer(customer);                                                                     
        
        allcus.remove(customer);
        this.model.delete_customer(customer);
        
        Alert a = new Alert(Alert.AlertType.NONE);         
        a.setAlertType(Alert.AlertType.CONFIRMATION); 
        a.setContentText("CUSTOMER is deleted!");
        a.show(); 
      
     
        
        
        firstn.setText(null);   // +
        lastn.setText(null);
        pasnr.setText(null);
        email.setText(null);
        phonen.setText(null);
        dob.setValue(null);
        country.setValue(null);
        cuslist.setValue(null);

      }catch(DBException ex){
      Logger.getLogger(CleanFlight.class.getName()).log(Level.SEVERE, null, ex);
      }
      
            
    }

}

