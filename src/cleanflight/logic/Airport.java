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
import java.util.ArrayList;

/**
 *
 * @authors Willem Himpe & Thomas Mathijs 
 */
public class Airport {
    
    private String AirportCode;
    private double Latitude ;
    private double Longitude;
    private String AirportName;
    private double TimeZone;

    public Airport(String AirportCode, String AirportName, double Latitude, double Longitude,  double TimeZone) {
        this.AirportCode = AirportCode;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.AirportName = AirportName;
        this.TimeZone = TimeZone;
    }
    public Airport(String AirportCode){
        this.AirportCode = AirportCode;
    }
    public String getAirportCode() {
        return AirportCode;
    }
    public double getLatitude() {
        return Latitude;
    }
    public double getLongitude() {
        return Longitude;
    }
    public String getAirportName() {
        return AirportName;
    }

    public double getTimeZone() {
        return TimeZone;
    }
    
    public void setAirportCode(String AirportCode) {
        this.AirportCode = AirportCode;
    }
    public void setLatitude(double Latitude) {
        this.Latitude = Latitude;
    }
    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }
    public void setAirportName(String AirportName) {
        this.AirportName = AirportName;
    }

    public void setTimeZone(double TimeZone) {
        this.TimeZone = TimeZone;
    }

    
    public String toString() {
        return AirportName ;
    }
    
    
     public static double getTimeZone(String AirportCode) throws DBException{
    
        Connection con = null;
        try {
          con = DBConnect.getConnection();
          Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
          String sql = "SELECT TimeZone "
            + " FROM airport "
            + " WHERE AirportCode = '"+AirportCode+"' ";
            // let op de spatie na 'summary' en 'Students' in voorgaande SQL

          ResultSet srs = stmt.executeQuery(sql);
          while (srs.next()){
              
              double n = srs.getDouble("TimeZone");
              return n; 
              
            }
          DBConnect.closeConnection(con);
          return 0.0;

        } catch (DBException | SQLException ex) {
          DBConnect.closeConnection(con);
          throw new DBException(ex);
        }

    }
    
      public static String getAirportName(String AirportCode) throws DBException{
    
        Connection con = null;
        try {
          con = DBConnect.getConnection();
          Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
          String sql = "SELECT AirportName "
            + " FROM airport "
            + " WHERE AirportCode = '"+AirportCode+"' ";
            // let op de spatie na 'summary' en 'Students' in voorgaande SQL

          ResultSet srs = stmt.executeQuery(sql);
          while (srs.next()){
              
              String n = srs.getString("AirportName");
              return n;
              
            }
          DBConnect.closeConnection(con);
          return null;

        } catch (DBException | SQLException ex) {
          DBConnect.closeConnection(con);
          throw new DBException(ex);
        }

    }

    
    
}
