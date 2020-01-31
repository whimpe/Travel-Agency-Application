/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanflight.db;
import static cleanflight.db.DBBooking.getBooking;
import cleanflight.logic.*;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.HashSet;

/**
 *
 * @authors Willem Himpe & Thomas Mathijs 
 */
public class DBDetails {
    

        
    
    public static void addDetails(Details det) throws DBException {
        
        Connection con = null;
        try {
          con = DBConnect.getConnection();
          Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
          //int booking_id = det.getBooking_id();
          //int FlightNr = det.getFlightNr();
          
          
          String sql = "INSERT INTO details(BookingNr,FlightNr) VALUES (?,?)";
          
          
          PreparedStatement pstmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
          pstmt.setInt(1,det.getBookingNr());
          pstmt.setInt(2,det.getFlightNr());
          pstmt.execute();
          DBConnect.closeConnection(con);
          

        } catch (DBException | SQLException ex) {
          DBConnect.closeConnection(con);
        }
    }
   
    
    
    
    
    
    
    
}
