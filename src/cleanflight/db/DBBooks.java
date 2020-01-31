/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanflight.db;
import static cleanflight.db.DBBooking.getBooking;
import cleanflight.logic.Booking;
import cleanflight.logic.Books;
import cleanflight.logic.Customer;

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
public class DBBooks {
    
    public static void addBookingCustomer(Books bc ) throws DBException {
        Connection con = null;
        try {
          con = DBConnect.getConnection();

          String query = "INSERT INTO books (BookingNr,PassportNr) VALUES (?,?)";

          //ResultSet srs = stmt.executeQuery(sql);
          PreparedStatement pstmt = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
           pstmt.setInt(1,bc.getBookingNr());
           pstmt.setString(2,bc.getPassportNr());
           
           pstmt.execute();
           ResultSet srs = pstmt.getGeneratedKeys();
           DBConnect.closeConnection(con);

        }catch (Exception ex) {
          ex.printStackTrace();
          DBConnect.closeConnection(con);
          throw new DBException(ex);
        }
         
    }
            
   
       
   
    
    
}
