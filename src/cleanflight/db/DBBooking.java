/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanflight.db;
import cleanflight.logic.*;
import cleanflight.logic.Booking;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.HashSet;
import javafx.collections.ObservableList;
/**
 *
 * @authors Willem Himpe & Thomas Mathijs 
 */
public class DBBooking {
    
    
    public static Booking getBooking(int booking_id) throws DBException {
    Connection con = null;
        try {
          con = DBConnect.getConnection();
          Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
          String sql = "SELECT BookingNr,NrOfPeople,NrOfStops,Insurance,TotalPrice "
            + "FROM booking WHERE (BookingNr="+booking_id+")";

          ResultSet srs = stmt.executeQuery(sql);
          if(srs.next()){
              int bk_id = srs.getInt("BookingNr");
              int aantal_pers = srs.getInt("NrOfPeople");
              int aantal_stops = srs.getInt("NrOfStops");        
              int insur = srs.getInt("Insurance");
              double tp = srs.getDouble("TotalPrice");
              Booking bk = new Booking(bk_id, aantal_pers, aantal_stops, insur, tp);
              return bk;
            }
          else{
            DBConnect.closeConnection(con);
            return null;
            }
        } catch (Exception ex) {
          ex.printStackTrace();
          DBConnect.closeConnection(con);
          throw new DBException(ex);
        }
      }
    
    
    public static ArrayList<Booking> getBookings() throws DBException {
        Connection con = null;
        try {
          con = DBConnect.getConnection();
          Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
          String sql = "SELECT BookingNr,NrOfPeople,NrOfStops, Insurance, TotalPrice "
            + " FROM booking ";
            // let op de spatie na 'summary' en 'Students' in voorgaande SQL

          ResultSet srs = stmt.executeQuery(sql);
          ArrayList<Booking> bookings = new ArrayList<Booking>();
          while (srs.next()){
                bookings.add(getBooking(srs.getInt("BookingNr")));                       
            }
          DBConnect.closeConnection(con);
          return bookings;

        } catch (Exception ex) {
          ex.printStackTrace();
          DBConnect.closeConnection(con);
          throw new DBException(ex);
        }
      }
    
    public static double getSumTP(ObservableList<Flight> f) {
    
    
   
      double totsum = 0;
      for(int i = 0; i < f.size(); i++){
          double x = f.get(i).getPrice();
          totsum = totsum + x;
      }
      return totsum;
    }
      
        
     public static int addBooking_return_id(int aantal_pers, int nr_stops,int ins,double tp) throws DBException{
    Connection con = null;
    int booking_id = 0;
    try {
      con = DBConnect.getConnection();
      //first add a booking
      String query = "INSERT INTO booking(NrOfPeople,NrOfStops,Insurance,TotalPrice) VALUES (?,?,?,?)";                          
      PreparedStatement pstmt = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
      pstmt.setInt(1,aantal_pers);
      pstmt.setInt(2,nr_stops); 
      pstmt.setInt(3,ins);
      pstmt.setDouble(4,tp);
      pstmt.execute();
      
      // get the autoincremented booking id
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);      
      String query2 = "SELECT BookingNr  FROM booking WHERE (NrOfPeople='"+aantal_pers+"') AND (NrOfStops='"+nr_stops+"') AND (Insurance = '"+ins+"') AND (TotalPrice = '"+tp+"')";
      ResultSet srs = stmt.executeQuery(query2);
      while (srs.next()){
        booking_id = srs.getInt("BookingNr");
        }
      DBConnect.closeConnection(con);
      return booking_id;

    } catch (DBException | SQLException ex) {
      DBConnect.closeConnection(con);
      throw new DBException(ex);
    }
  }
    
    
 
  
    
    
}
