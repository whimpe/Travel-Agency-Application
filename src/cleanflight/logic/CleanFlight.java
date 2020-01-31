/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanflight.logic;

import cleanflight.db.*;
import cleanflight.db.DBException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import cleanflight.db.DBException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CleanFlight {
  private ArrayList<Customer> customers;
  private ArrayList<Booking> bookings;
  private ArrayList<Books> booking_customer;
  private ArrayList<Flight> flights;
  private ArrayList<Flight> selected_flights;
  private ArrayList<Countries> all_countries;
  private ArrayList<Countries> country;
  private ArrayList<Airport> all_airports;
  private ArrayList<Flight> pre_selected_flights;
  private ArrayList<Customer> pre_selected_customers;
  private ArrayList<BestCustomer> bestcustomers;
  private ArrayList<BestAirports> bestairports;

  
  
  private static CleanFlight cleanflight = new CleanFlight();
  
  public CleanFlight() {
    try {
      customers = DBCustomer.getCustomers();    
      bookings = DBBooking.getBookings();
      flights = DBFlight.getFlights2();
      all_countries = DBCountries.get_all();
      all_airports =DBAirport.get_all();
      pre_selected_flights = new ArrayList<Flight>();
      pre_selected_customers = new ArrayList<Customer>();;
      bestairports = DBAirport.best_airports();
      bestcustomers = DBCustomer.BestCustomers();
      
    } catch (DBException ex) {
      Logger.getLogger(CleanFlight.class.getName()).log(Level.SEVERE, null, ex);
    }
    
  }
  
    public static CleanFlight getInstance(){
        return cleanflight;
    }

    public ArrayList<BestCustomer> getBestcustomers() {
        return bestcustomers;
    }

    public ArrayList<BestAirports> getBestairports() {
        return bestairports;
    }
    
    
    
   public void set_Preselected_Customer(ArrayList<Customer> input){
        this.pre_selected_customers = input;
       
        }       
    
    public void set_Preselected_Flight(Flight input){
         this.pre_selected_flights.add(input);
        }

    public ArrayList<Customer> getPre_selected_customers() {
        return pre_selected_customers;
    }

    public ArrayList<Flight> getPre_selected_flights() {
        return pre_selected_flights;
    }
   
    public void addPre_selected_flight(Flight f){
        this.pre_selected_flights.add(f);
        }
    public void addPre_selected_Customer(Customer c){
        this.pre_selected_customers.add(c);
        }
    
    
    
    public ArrayList<Airport> getAirports() {
        return all_airports;
    }
    
    
   public ArrayList<Customer> getCustomers() {
    return customers;
  }
   public void AddCustomer(Customer customer) {
       this.customers.add(customer);
    
  }
   
    
   public ArrayList<Books> getBooking_Customers(){
    return booking_customer;
    }
   public void AddBooking_Customer(Books BC) {
      this.booking_customer.add(BC);
    
  }
  
   
   public ArrayList<Booking> getBookings() {
        return bookings;
    }
   public void addBooking(Booking B){
      this.bookings.add(B);
      //DBBooking.save  TO DO
  }
   public Booking getBooking(int id){
    for(int i=0;i<bookings.size();i++){
        if(id==bookings.get(i).getBookingNr())
            return bookings.get(i);                    
        }
    return null;
  }     
   

   public ArrayList<Flight> getFlights() {
        return flights;
    }
    public Flight getFlight(int id){
    for(int i=0;i<flights.size();i++){
        if(id==flights.get(i).getFlightNr())
            return flights.get(i);                    
        }
    return null;
  }     
   
   public void set_checked_flights( ArrayList<Flight> input) {
        this.selected_flights=input;
    }
   public ArrayList<Flight> getSelected_flights() {
        return selected_flights;
    }
   
    public ArrayList<Countries> getAll_countries() {
        return all_countries;
    }
 public static String getCountryName(int countryNr) throws DBException{
    
        Connection con = null;
        try {
          con = DBConnect.getConnection();
          Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
          String sql = "SELECT CountryName "
            + " FROM countries"
            + " WHERE CountryNr = "+countryNr+"";
            // let op de spatie na 'summary' en 'Students' in voorgaande SQL

          ResultSet srs = stmt.executeQuery(sql);
          ArrayList<Countries> country = new ArrayList<>();
          while (srs.next()){
              
              String n = srs.getString("CountryName");
              Countries c = new Countries(n);
              return c.getCountryName(); 
              
            }
          DBConnect.closeConnection(con);
          return null;

        } catch (DBException | SQLException ex) {
          DBConnect.closeConnection(con);
          throw new DBException(ex);
        }

    }
     
    public static int getCountryNr(String countryName) throws DBException{
    
        Connection con = null;
        try {
          con = DBConnect.getConnection();
          Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
          String sql = "SELECT CountryNr "
            + " FROM countries"
            + " WHERE CountryName = '"+countryName+"'";
            // let op de spatie na 'summary' en 'Students' in voorgaande SQL
          ResultSet srs = stmt.executeQuery(sql);
          while (srs.next()){              
              int n = srs.getInt("CountryNr");
              return n; 
              
            }
          DBConnect.closeConnection(con);
          return 0;

        } catch (DBException | SQLException ex) {
          DBConnect.closeConnection(con);
          throw new DBException(ex);
        }

    } 
  
  public void delete_customer(Customer c){
  
      customers.remove(c);
  
  }
  
  public void update_customer(Customer oud,Customer cust_new ){      
    customers.remove(oud);
    customers.add(cust_new);
    
  }
    
}
