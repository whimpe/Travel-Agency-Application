/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanflight.gui;

import cleanflight.db.DBBooking;
import cleanflight.db.DBDetails;
import cleanflight.db.DBBooks;
import cleanflight.db.DBConnect;
import cleanflight.db.DBException;
import cleanflight.db.DBFlight;
import cleanflight.logic.Airport;
import cleanflight.logic.Booking;
import cleanflight.logic.Books;
import cleanflight.logic.CleanFlight;
import cleanflight.logic.Customer;
import cleanflight.logic.Details;
import cleanflight.logic.Flight;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.util.Random;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.Alert;
import java.util.*;
/**
 *
 * @authors Willem Himpe & Thomas Mathijs 
 */
public class CheckFlightController implements Initializable{
        
  private CleanFlight model;
  private ObservableList <Flight> selfli;
  @FXML
  private ComboBox<Airport> cbOrigin;
  private ObservableList<Airport> all_origins;
  @FXML
  private ComboBox<Airport> cbDestination;
  private ObservableList<Airport> all_destinations;
  
  @FXML
  private DatePicker  dpStart;
  @FXML
  private DatePicker dpEnd;
  @FXML
  private TextField txtAmountSeats;
   @FXML
  private TextField RoD;
    @FXML
  private TextField reduc;
  @FXML
  private TextField anothercustomer;
  @FXML
  private CheckBox CheckClimate;
  @FXML
  private TextField highprice;
  @FXML
  private TextField highco2;
  @FXML
  private Button BtnFindFlights;
  @FXML
  private AnchorPane dataPane3;
  @FXML
  private AnchorPane data1;
  @FXML
  private ComboBox<Flight> cbflight;
  private ObservableList<Flight> allflights;
  @FXML
  private ComboBox<Customer> cbcustomer;
  private ObservableList<Customer> allcustomers;
  @FXML
  private Button flightbtn;
  @FXML
  private Button customerbtn;
  @FXML
  private ListView<Flight> flightList;
  private ObservableList<Flight> selectedflights;
  
  @FXML
  private ListView<Customer> customerList;
  private ObservableList<Customer> selectedcustomers;  
  
  @FXML
  private CheckBox cbInsurance;  
  @FXML
  private Button addBookingbtn;   
  @FXML
  private AnchorPane data2;
  
 
 
  
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    model = CleanFlight.getInstance();
    all_origins = FXCollections.observableArrayList(model.getAirports());        
    cbOrigin.setItems(all_origins);
    all_destinations = FXCollections.observableArrayList(model.getAirports());        
    cbDestination.setItems(all_destinations);
    
    //allflights = FXCollections.observableArrayList(model.getFlights());        
    //cbflight.setItems(allflights);
    allcustomers = FXCollections.observableArrayList(model.getCustomers());        
    cbcustomer.setItems(allcustomers);            
    anothercustomer.setText(null);
  }
    
  @FXML
  private void loadSelectedFlights(ActionEvent event) throws ParseException, SQLException, DBException, IOException {
      
      try{
        String origin = cbOrigin.getValue().getAirportName();
        String destination= cbDestination.getValue().getAirportName();
        String startdate_txt = "";
        if(dpStart.getValue() != null){
            startdate_txt = dpStart.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            double highp = 1000000000000000000000.0;
            double highc = 1000000000000000000000.0;
            double range = 0.0;
            int amount_seats = 1000000000;
            if(txtAmountSeats.getText().trim().isEmpty() == false){
                amount_seats = Integer.parseInt(txtAmountSeats.getText());
                
                
            if(highprice.getText().trim().isEmpty() == false) {highp = Double.parseDouble(highprice.getText());}
            if(highco2.getText().trim().isEmpty() == false) {highc = Double.parseDouble(highco2.getText());}
            if(RoD.getText().trim().isEmpty() == false) {range  = Double.parseDouble(RoD.getText());}

             
            ArrayList<Flight> flights = DBFlight.get_check_flight(origin, destination, startdate_txt, amount_seats,highp,highc,range);

            if(flights.size() != 0){
            Alert a = new Alert(Alert.AlertType.NONE);         
            a.setAlertType(Alert.AlertType.CONFIRMATION); 
            a.setContentText("We found " + flights.size() + " flight(s) for you! It is possible that you'll have to pick 2 flights to reach your destination. We made sure there are 2 hours between touchdown and take-off.");
            a.show(); 
            selfli = FXCollections.observableArrayList(flights);
            model.set_checked_flights(flights);
            cbflight.setItems(selfli);
            cbflight.getSelectionModel().selectFirst();



            cbOrigin.setValue(null);
            cbDestination.setValue(null);
            dpStart.setValue(null);
            txtAmountSeats.setStyle("-fx-text-fill: white;");
            highprice.setText(null);
            highco2.setText(null);
            RoD.setText(null);

            }

            else {
            Alert b = new Alert(Alert.AlertType.NONE);         
            b.setAlertType(Alert.AlertType.CONFIRMATION); 
            b.setContentText("Sorry, we didn't find a flight for you...");
            b.show(); 
            cbOrigin.setValue(null);
            cbDestination.setValue(null);
            dpStart.setValue(null);
            txtAmountSeats.setText(null);
            highprice.setText(null);
            highco2.setText(null);
            RoD.setText(null);
            cbInsurance.setSelected(false);
            }
            
           
              }
            
        else{ 
        Alert b = new Alert(Alert.AlertType.NONE);         
        b.setAlertType(Alert.AlertType.CONFIRMATION); 
        b.setContentText("Please enter a number of persons!");
        b.show(); 
        

        
        }
        }
        else{
        Alert b = new Alert(Alert.AlertType.NONE);         
        b.setAlertType(Alert.AlertType.CONFIRMATION); 
        b.setContentText("Please enter a date!");
        b.show(); 
        

        }
  
        }catch (DBException ex) {
          Logger.getLogger(CustomerAddController.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    
    
  public void loadAddFlight (ActionEvent event) throws IOException{
            
      Flight selected_flight  = cbflight.getValue();
      model.addPre_selected_flight(selected_flight);
      
      selectedflights = FXCollections.observableArrayList(model.getPre_selected_flights()); 
      flightList.setItems(selectedflights);      
      
      
  }
  
  public void loadAddcustomer (ActionEvent event){
      Customer pre_select_cust = cbcustomer.getValue();
     
      model.addPre_selected_Customer(pre_select_cust);
      
      selectedcustomers = FXCollections.observableArrayList(model.getPre_selected_customers()); 
      customerList.setItems(selectedcustomers);
      int amount_seats = Integer.parseInt(txtAmountSeats.getText());
      
      
     
      if(selectedcustomers.size() < amount_seats){
      int anothcust = amount_seats;
      String cust = "customers";
       if(anothcust == 1){cust = "customer";}
       
       
      anothercustomer.setText("Please add " + anothcust +" " + cust + " in total!");
      }
  
  }
  
  public void loadAddBooking(ActionEvent event) throws DBException, IOException{
      
    
      int aantal_personen1 = selectedcustomers.size();
      int aantal_personen2 = Integer.parseInt(txtAmountSeats.getText());
       
      if(aantal_personen1 != aantal_personen2){
       Alert b = new Alert(Alert.AlertType.NONE);         
        b.setAlertType(Alert.AlertType.CONFIRMATION); 
        b.setContentText("Please add exactly " + aantal_personen2 + " customer(s)!");
        b.show(); 
      
       
      cbcustomer.setValue(null);
      
      selectedcustomers = null;
      customerList.getItems().clear();
      
     
      model.getPre_selected_customers().clear();
      model.getPre_selected_flights().clear();
      }
      
      else{
          
      
      double tp = DBBooking.getSumTP(selectedflights);
      
      if(reduc.getText().trim().isEmpty() == false){
          tp = Double.parseDouble(reduc.getText());
      }
      if(tp >= 50){
      int aantal_stops = selectedflights.size();    
      Boolean insured = cbInsurance.isSelected();
      int insur = 0;
      if(insured == true){insur = 1;}
      tp = tp*aantal_personen1;
      
      int booking_id = DBBooking.addBooking_return_id(aantal_personen1,aantal_stops,insur, tp);//ADDS TO BOOKING TABLE
     
      Booking b =new Booking(booking_id, aantal_personen1, aantal_stops, insur,tp);
      
      
      
      for(int i=0;i<aantal_personen1;i++){
          Customer c = selectedcustomers.get(i);
          
          Books bk = new Books(booking_id, c.getPassportNr());
          DBBooks.addBookingCustomer(bk);                          
        }
      for(int i=0;i<aantal_stops;i++){
          Flight f = selectedflights.get(i);
       
          Details det = new Details(booking_id,f.getFlightNr());
          
          DBDetails.addDetails(det);                                  
          
          DBFlight.reducePlacesFlight(f.getFlightNr(),aantal_personen1);          
        }
        Alert a = new Alert(Alert.AlertType.NONE);         
      a.setAlertType(Alert.AlertType.CONFIRMATION); 
      a.setContentText("Booking is added!");
      a.show();
      
      reduc.setText(null);
      cbflight.setValue(null);
      cbcustomer.setValue(null);
      customerList.setItems(null);
      flightList.setItems(null);
      anothercustomer.setText("");
      selectedcustomers.clear();
      selectedflights.clear();
      txtAmountSeats.setText(null);
      selectedflights.removeAll();
      selectedcustomers.removeAll();
      RoD.setText(null);
      txtAmountSeats.setStyle("-fx-text-fill: black;");
      cbInsurance.setSelected(false);
      model.getPre_selected_customers().clear();
      model.getPre_selected_flights().clear();

      FXMLLoader loader = new FXMLLoader(getClass().getResource("/cleanflight/gui/AllBookings.fxml"));    
      loader.load();                                                                                       
      AllBookingsController controller = (AllBookingsController) loader.getController();           
      controller.addBooking(b);   
      
      
      
      
    }
 
    else{
        Alert b = new Alert(Alert.AlertType.NONE);         
        b.setAlertType(Alert.AlertType.CONFIRMATION); 
        b.setContentText("Please enter a price greater than €50!");
        b.show(); 
       reduc.setText(null);
    
      }
       
  }
  }
}
    
  
  