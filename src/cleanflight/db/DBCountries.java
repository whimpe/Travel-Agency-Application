/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanflight.db;

import static cleanflight.db.DBCustomer.getCustomer;
import java.util.ArrayList;
import cleanflight.logic.Countries;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @authors Willem Himpe & Thomas Mathijs 
 */
public class DBCountries {
    
    
    public static ArrayList<Countries> get_all() throws DBException{
    
        Connection con = null;
        try {
          con = DBConnect.getConnection();
          Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
          String sql = "SELECT CountryNr,CountryName "
            + " FROM countries";

          ResultSet srs = stmt.executeQuery(sql);
          ArrayList<Countries> countries = new ArrayList<>();
          while (srs.next()){
              int i = srs.getInt("CountryNr");
              String n = srs.getString("CountryName");
              Countries c = new Countries(i,n);
              countries.add(c);  
              
            }
          DBConnect.closeConnection(con);
          return countries;

        } catch (DBException | SQLException ex) {
          DBConnect.closeConnection(con);
          throw new DBException(ex);
        }

    }
    
     public static String getCountryName(int countryNr) throws DBException{
    
        Connection con = null;
        try {
          con = DBConnect.getConnection();
          Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
          String sql = "SELECT CountryName "
            + " FROM countries "
            + " WHERE CountryNr = '"+countryNr+"' ";

          ResultSet srs = stmt.executeQuery(sql);
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
            + " FROM countries "
            + " WHERE CountryName = '"+countryName+"' ";
            // let op de spatie na 'summary' en 'Students' in voorgaande SQL

          ResultSet srs = stmt.executeQuery(sql);
         
          while (srs.next()){
              
              int n = srs.getInt("CountryNr");
              Countries c = new Countries(n);
              return c.getCountryNr(); 
              
            }
          DBConnect.closeConnection(con);
          return 0;

        } catch (DBException | SQLException ex) {
          DBConnect.closeConnection(con);
          throw new DBException(ex);
        }

    }
    
}
