/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanflight.db;
import cleanflight.logic.*;
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
public class DBCustomer {
   
     
  public static Customer getCustomer(String PassportNr) throws DBException {
    Connection con = null;
    try {
      con = DBConnect.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      String sql = "SELECT c.PassportNr, c.FirstName, c.LastName,c.CountryNr,c.DoB,c.Email,c.Phone "
	+ "FROM customer as c "
	+ "WHERE PassportNr = '"+PassportNr+"' ";

      ResultSet srs = stmt.executeQuery(sql);
      
      
      if (srs.next()) {
          
        String passportnbr =srs.getString("PassportNr");              
        String first_name = srs.getString("FirstName");
        String last_name = srs.getString("LastName");
        int country =srs.getInt("CountryNr");
        
        String dob = srs.getString("DoB");
        String email = srs.getString("Email");
        String phone = srs.getString("Phone");        
        
        Customer customer = new Customer(passportnbr, first_name, last_name, country, dob,  email, phone);
        DBConnect.closeConnection(con);
        return customer;
        
      } else {
	DBConnect.closeConnection(con);
	return null;
      }      
      
    } catch (DBException | SQLException ex) {
      DBConnect.closeConnection(con);
      throw new DBException(ex);
    }
  }
  
   public static BestCustomer getBestCustomer(String PassportNr,int nbookings, int rank) throws DBException {
    Connection con = null;
    try {
      con = DBConnect.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      String sql = "SELECT FirstName, LastName "
	+ "FROM customer "
	+ "WHERE PassportNr = '"+PassportNr+"' " ;

      ResultSet srs = stmt.executeQuery(sql);
      
      if (srs.next()) {
          
        String first_name = srs.getString("FirstName");
        String last_name = srs.getString("LastName");
             
        
        BestCustomer bcustomer = new BestCustomer(rank,nbookings, first_name, last_name);
        DBConnect.closeConnection(con);
        return bcustomer;
        
      } else {
	DBConnect.closeConnection(con);
	return null;
      }      
      
    } catch (DBException | SQLException ex) {
      DBConnect.closeConnection(con);
      throw new DBException(ex);
    }
  }
  
  
  public static ArrayList<Customer> getCustomers() throws DBException {
    Connection con = null;
    try {
      con = DBConnect.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String sql = "SELECT c.PassportNr, c.FirstName, c.LastName,con.CountryName,c.DoB,c.Email,c.Phone "
	+ "FROM customer as c, countries as con "
	+ "WHERE (con.CountryNr=c.CountryNr)";
   
        
      ResultSet srs = stmt.executeQuery(sql);
      ArrayList<Customer> customers = new ArrayList<>();
      while (srs.next()){
        customers.add(getCustomer(srs.getString("PassportNr")));                       
        }
      DBConnect.closeConnection(con);
      return customers;
                
    } catch (DBException | SQLException ex) {
      DBConnect.closeConnection(con);
      throw new DBException(ex);
    }
  }      
    
  public static void addCustomer(Customer c) throws DBException {
    Connection con = null;
    try {
      con = DBConnect.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
      String sql_2 = "INSERT INTO customer (PassportNr,FirstName, LastName,CountryNr, Dob,Email,Phone) VALUES (?,?,?,?,?,?,?)";
      
    
       PreparedStatement pstmt = con.prepareStatement(sql_2,Statement.RETURN_GENERATED_KEYS);
       pstmt.setString(1,c.getPassportNr());
       pstmt.setString(2,c.getFirstName() );
       pstmt.setString(3,c.getLastName() );
       pstmt.setInt(4,c.getCountryNr());               
       pstmt.setString(5,c.getDoB());
       
       pstmt.setString(6,c.getEmail());
       pstmt.setString(7,c.getPhone());
       
       pstmt.execute();
       DBConnect.closeConnection(con);
       
    } catch (DBException | SQLException ex) {
      DBConnect.closeConnection(con);
      throw new DBException(ex);
    }
  }
    
  
   public static void updateCustomer(Customer c) throws DBException {
    Connection con = null;
    try {
      con = DBConnect.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
         
      String sql_2 = "UPDATE customer SET PassportNr = '"+c.getPassportNr()+"', FirstName = '"+c.getFirstName()+"', LastName = '"+c.getLastName()
                        +"', CountryNr = '"+c.getCountryNr()+"', DoB = '"+c.getDoB()+"', Email = '"+c.getEmail()+"', Phone = '"+c.getPhone()+"' WHERE (PassportNr=?)";
      
       PreparedStatement pstmt = con.prepareStatement(sql_2,Statement.RETURN_GENERATED_KEYS);
       pstmt.setString(1,c.getPassportNr());
       
       pstmt.execute();
       DBConnect.closeConnection(con);
       
    } catch (DBException | SQLException ex) {
      DBConnect.closeConnection(con);
      throw new DBException(ex);
    }
  }
    
  public static void deleteCustomer(Customer customer) throws DBException {
        Connection con = null;
        try {
            con = DBConnect.getConnection();

            String sql = "DELETE FROM customer WHERE (PassportNr=?)";        
            PreparedStatement pstmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);            
            pstmt.setString(1,customer.getPassportNr());
            pstmt.execute();            

            DBConnect.closeConnection(con);

        } catch (DBException | SQLException ex) {
          DBConnect.closeConnection(con);
          throw new DBException(ex);
        }
      }
  
  public static ArrayList<BestCustomer> BestCustomers() throws DBException{
        Connection con = null;
        try {
          con = DBConnect.getConnection();
          Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
          String sql = "SELECT COUNT(bo.PassportNr) as aantal_books, bo.PassportNr "
                        +" FROM  books as bo "
                        
                        +" GROUP BY bo.PassportNr " 
                        +" ORDER BY aantal_books DESC "
                        +" LIMIT 5";
            

          ResultSet srs = stmt.executeQuery(sql);
          int plaats = 0;
          ArrayList<BestCustomer> bestcustomers = new ArrayList<>();      
          while (srs.next()){
            int aantal_books = 0;
            plaats ++;

            aantal_books = srs.getInt("aantal_books");
            String p = srs.getString("PassportNr");
                  
            BestCustomer bc = getBestCustomer(p, aantal_books, plaats);
            
            bestcustomers.add(bc);
            
            
          }
          DBConnect.closeConnection(con);
          return bestcustomers;

        } catch (DBException | SQLException ex) {
          DBConnect.closeConnection(con);
          throw new DBException(ex);
        }
    }
    
    
    

    
    
}