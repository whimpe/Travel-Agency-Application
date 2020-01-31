/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanflight.gui;
import java.text.DecimalFormat;
import cleanflight.db.DBException;
import cleanflight.db.DBFlight;
import cleanflight.logic.CleanFlight;
import cleanflight.logic.Countries;
import cleanflight.logic.Customer;
import cleanflight.logic.Flight;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @authors Willem Himpe & Thomas Mathijs 
 */
public class CustomerReportController implements Initializable {

    private CleanFlight model;
    
    @FXML
    private AnchorPane DataPane;
    @FXML
    private ListView<Flight> AllFlightsListView;
    private ObservableList<Flight> allflights;

    @FXML
    private ComboBox<Customer> cbCustomer;
    private ObservableList<Customer> allcustomers;
    @FXML
    private TextArea totalco2TF;
    @FXML
    private TextArea Total_price_payTA;
    @FXML
    private Button findreportBtn;


     @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = CleanFlight.getInstance();
        allcustomers = FXCollections.observableArrayList(model.getCustomers());           
        cbCustomer.setItems(allcustomers);
        
      } 
     private static DecimalFormat df2 = new DecimalFormat("#.##");
     public void loadCustomerData(ActionEvent event) throws IOException, DBException, SQLException {
         
        Customer cust = cbCustomer.getValue();
         
        allflights = FXCollections.observableArrayList(DBFlight.allFlightsCustomer(cust.getPassportNr()));           
        AllFlightsListView.setItems(allflights);
        
        double total_payed=0.0;
        double total_co2=0.0;
        double total_distance_travelled = 0.0;
        ArrayList<Flight> f = new ArrayList<>();
        f = DBFlight.allFlightsCustomer(cust.getPassportNr());
        for(int i = 0; i < f.size(); i++){
            total_payed = total_payed+f.get(i).getPrice();
            total_co2 = total_co2 + f.get(i).getCo2();
            total_distance_travelled = total_distance_travelled+f.get(i).getDistance();                        
            }
        totalco2TF.setText("Total price = €" + df2.format(total_payed)
                +"\nTotal CO2-emission = "+df2.format(total_co2/1000)+ " kilograms"
                +"\nTotal distance travelled = "+df2.format(total_distance_travelled) + " km");
         
        Total_price_payTA.setText("The cost of your pollution for the earth is €"+ df2.format((total_co2)/10000)+"!"
                +"\nPlease donate this amount to myclimate.org, then you'll become CO2 neutral!");
         
     }
    
}
