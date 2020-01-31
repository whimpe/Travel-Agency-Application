/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanflight.gui;

import cleanflight.db.DBFlight;
import cleanflight.logic.Airport;
import cleanflight.logic.BestAirports;
import cleanflight.logic.BestCustomer;
import cleanflight.logic.CleanFlight;
import cleanflight.logic.Customer;
import cleanflight.logic.Flight;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @authors Willem Himpe & Thomas Mathijs 
 */
public class InternalReportController implements Initializable {

    private CleanFlight model;
    
    @FXML
    private ListView<BestAirports> airportListView;
    private ObservableList<BestAirports> airport_rank;

    @FXML
    private ListView<BestCustomer> CustomerListView;
    private ObservableList<BestCustomer> customer_rank;

    @FXML
    private TextArea TotalRevenueTF;
    @FXML
    private TextField co2TF;

    /**
     * Initializes the controller class.
     */
   
         private static DecimalFormat df2 = new DecimalFormat("#.##");

    public void initialize(URL url, ResourceBundle rb) {
        model = CleanFlight.getInstance();
        
        airport_rank = FXCollections.observableArrayList(model.getBestairports());               
        airportListView.setItems(airport_rank);
        
        customer_rank = FXCollections.observableArrayList(model.getBestcustomers());               
        CustomerListView.setItems(customer_rank);
        
        //to get total revenue and co2             
        TotalRevenueTF.setText(" Total revenue: € " + df2.format(DBFlight.TotalRevenue()) 
                + "\n\n Profit for CleanFlight(€50/person/booking): € " + df2.format(DBFlight.TotalProfit()) 
                + "\n\n Revenue for the airline companies: € " + df2.format((DBFlight.TotalRevenue())-(DBFlight.TotalProfit()))) ;   
        co2TF.setText(df2.format(DBFlight.TotalCO2()/1000)+" kilograms");          //
        

        
        
    }    
    
}
