/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanflight.db;

import static cleanflight.db.DBCustomer.getCustomer;
import cleanflight.logic.Airport;
import cleanflight.logic.BestAirports;
import cleanflight.logic.Customer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @authors Willem Himpe & Thomas Mathijs 
 * 
 */
public class DBAirport {
    
     public static ArrayList<Airport> get_all() throws DBException {
    Connection con = null;
    try {
      con = DBConnect.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT AirportCode, AirportName, Latitude, Longitude, TimeZone "
	+ "FROM airport";
        // let op de spatie na 'summary' en 'Students' in voorgaande SQL
        
      ResultSet srs = stmt.executeQuery(sql);
      
      ArrayList<Airport> airports = new ArrayList<>();
      while (srs.next()){
        String code = srs.getString("AirportCode");
        double lat = srs.getDouble("Latitude");
        double lon = srs.getDouble("Longitude");
        String name = srs.getString("AirportName");
        double time = srs.getDouble("TimeZone");
        Airport ap = new Airport(code,name,lat,lon,time);
        airports.add(ap);                       
        }
      DBConnect.closeConnection(con);
      return airports;
                
    } catch (DBException | SQLException ex) {
      DBConnect.closeConnection(con);
      throw new DBException(ex);
    }
  }   
     
    
    public static ArrayList<BestAirports> best_airports() throws DBException {
        Connection con = null;
        try {
          con = DBConnect.getConnection();
          Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
         
          String sql = "SELECT COUNT(f.AirportCodeOrig) as tot_air, f.AirportCodeOrig "
                  + " FROM flight as f, details as d "
                  + " WHERE (d.FlightNr = f.FlightNr) "
                  + " GROUP BY f.AirportCodeOrig " 
                  + " ORDER BY tot_air DESC "
                  + " LIMIT 5 ";
          ResultSet srs = stmt.executeQuery(sql);

          ArrayList<BestAirports> bestairports = new ArrayList<>();      
          int plaats = 0;
          while (srs.next()){
            plaats ++;
            String code1 = srs.getString("AirportCodeOrig");
            String code = getAirportName(code1);
            int aantal_bookings = srs.getInt("tot_air");
            BestAirports ba = new BestAirports(plaats, code, aantal_bookings);
            bestairports.add(ba);
            }
          DBConnect.closeConnection(con);
          return bestairports;

        } catch (DBException | SQLException ex) {
          DBConnect.closeConnection(con);
          throw new DBException(ex);
        }
    }
    
     public static String getAirportName(String acode) throws DBException {
    Connection con = null;
    try {
      con = DBConnect.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT AirportName "
	+ " FROM airport "
        + " WHERE (AirportCode = '"+acode+"')";
        // let op de spatie na 'summary' en 'Students' in voorgaande SQL
        
      ResultSet srs = stmt.executeQuery(sql);
      String name = "";
      
      while (srs.next()){
        
        name = srs.getString("AirportName");
       
        }
      DBConnect.closeConnection(con);
      return name;
                
    } catch (DBException | SQLException ex) {
      DBConnect.closeConnection(con);
      throw new DBException(ex);
    }
  }   
     
    
}
