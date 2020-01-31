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
public class Customer {
    
  private String PassportNr;
  private String FirstName;
  private String LastName;
  private int CountryNr;
  private String DoB;
  private String Email;
  private String Phone;
  
 
 
    public Customer(String PassportNr, String FirstName, String LastName,  int CountryNr, String DoB, String Email, String Phone) {
        this.PassportNr = PassportNr;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.CountryNr = CountryNr;
        this.DoB = DoB;
        this.Email = Email;
        this.Phone = Phone;
    }

      public Customer(String FirstName, String LastName, int CountryNr, String DoB) {

    this.FirstName = FirstName;
    this.LastName = LastName;
    this.CountryNr =CountryNr;
    this.DoB =DoB;
    
    }
    
    public Customer(String PassportNr, String FirstName, String LastName){
        this.PassportNr = PassportNr;
        this.FirstName = FirstName;
        this.LastName = LastName;
        
    }
    
    public String getPassportNr() {
        return PassportNr;
    }

    public void setPassportNr(String PassportNr) {
        this.PassportNr = PassportNr;
    }
   
    public String getFirstName() {
        return FirstName;
    }
    public String getLastName() {
        return LastName;
    }
    public int getCountryNr() {
        return CountryNr;
    }
    public String getDoB() {
        return DoB;
    }
        
    public String getEmail() {
        return Email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }
    public void setLastName(String LastName) {
        this.LastName = LastName;
    }
    public void setCountryNr(int CountryNr) {
        this.CountryNr = CountryNr;
    }
    public void setDoB(String DoB) {
        this.DoB = DoB;
    }



    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }
  
    public String toString(){
    return PassportNr + " | " + FirstName+ " " + LastName;
  }
    
    
}