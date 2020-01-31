/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanflight.logic;

import cleanflight.db.DBConnect;
import cleanflight.db.DBException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @authors Willem Himpe & Thomas Mathijs 
 */
    public class Flight {
    private int FlightNr;
    private int AirplaneNr;    
    private String AirportCodeOrig;
    private String AirportCodeDest;
    private String DepTime;
    private String ArrTime;
    private int PlacesAvailable;
    private String AirlineCode;
    private double distance;
    private double co2;
    private double price;
    private String FlightCode;
    private String DepDate;
    private String ArrDate;

  
          

    public Flight(int FlightNr, String DepDate, int AirplaneNr, String AirportCodeOrig, String AirportCodeDest, String AirlineCode, String ArrDate, int PlacesAvailable,String DepTime, String ArrTime, double distance, double co2, double price,String FlightCode) {
        this.FlightNr = FlightNr;
        this.AirplaneNr = AirplaneNr;
        this.AirportCodeOrig = AirportCodeOrig;
        this.AirportCodeDest = AirportCodeDest;
        this.DepTime = DepTime;
        this.ArrTime = ArrTime;
        this.PlacesAvailable = PlacesAvailable;
        this.DepDate = DepDate;
        this.ArrDate = ArrDate;
        this.AirlineCode = AirlineCode;
        this.price = price;
        this.distance = distance;
        this.co2 = co2;
        this.FlightCode = FlightCode;
    }
    
   

    public int getFlightNr() {
        return FlightNr;
    }

    public int getAirplaneNr() {
        return AirplaneNr;
    }

    public String getAirportCodeOrig() {
        return AirportCodeOrig;
    }

    public String getAirportCodeDest() {
        return AirportCodeDest;
    }

    public String getDepTime() {
        return DepTime;
    }

    public String getArrTime() {
        return ArrTime;
    }

    public int getPlacesAvailable() {
        return PlacesAvailable;
    }

    public String getAirlineCode() {
        return AirlineCode;
    }

    public double getDistance() {
        return distance;
    }

    public double getCo2() {
        return co2;
    }

    public double getPrice() {
        return price;
    }

    
    public String getDepDate() {
        return DepDate;
    }

    public String getArrDate() {
        return ArrDate;
    }

    public void setFlightNr(int FlightNr) {
        this.FlightNr = FlightNr;
    }

    public void setAirplaneNr(int AirplaneNr) {
        this.AirplaneNr = AirplaneNr;
    }

    public void setAirportCodeOrig(String AirportCodeOrig) {
        this.AirportCodeOrig = AirportCodeOrig;
    }

    public void setAirportCodeDest(String AirportCodeDest) {
        this.AirportCodeDest = AirportCodeDest;
    }

    public void setDepTime(String DepTime) {
        this.DepTime = DepTime;
    }

    public void setArrTime(String ArrTime) {
        this.ArrTime = ArrTime;
    }

    public void setPlacesAvailable(int PlacesAvailable) {
        this.PlacesAvailable = PlacesAvailable;
    }

    public void setAirlineCode(String AirlineCode) {
        this.AirlineCode = AirlineCode;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setCo2(double co2) {
        this.co2 = co2;
    }

    public void setPrice(double price) {
        this.price = price;
    }

 

    public void setDepDate(String DepDate) {
        this.DepDate = DepDate;
    }

    public void setArrDate(String ArrDate) {
        this.ArrDate = ArrDate;
    }
    
   
   
  
    private static DecimalFormat df2 = new DecimalFormat("#.##");

     public String toString(){
        
        try {
            Airport p1 = new Airport(AirportCodeOrig);
            Airport p2 = new Airport(AirportCodeDest);
            String a1 = p1.getAirportName(AirportCodeOrig);
            String a2 = p2.getAirportName(AirportCodeDest);
            double DifTimeZone = p1.getTimeZone(AirportCodeDest) - p2.getTimeZone(AirportCodeOrig);
            int DifTimeZoneInt = (int)DifTimeZone;
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss"); 
            Date arrtime = timeFormat.parse(ArrTime);
            Timestamp localtime = new Timestamp(arrtime.getTime() + (1000 * 60 * 60 * DifTimeZoneInt));
            String localtime1 = timeFormat.format(localtime);

            return FlightNr + "| " + a1 + " (" + AirportCodeOrig  + ")" + " --> " + a2 + " ("+ AirportCodeDest + ")" + "   " 
                    + "\n    " + FlightCode
                    + "\n    DATE DEPARTURE: "+DepDate+" DATE ARRIVAL: "+ArrDate
                    + "\n    TIME DEPARTURE: "+DepTime+" TIME ARRIVAL: "+ArrTime + " LOCAL TIME ARRIVAL: " + localtime1
                    + "\n    PRICE: €"+ df2.format(price)
                    + "\n    CO2-EMISSION: "+ df2.format(co2) + " grams";
        } catch (DBException ex) {
            Logger.getLogger(Flight.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Flight.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
     }
  }
    
    
    

