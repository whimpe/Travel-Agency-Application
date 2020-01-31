/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanflight.logic;

/**
 *
 * @authors Willem Himpe & Thomas Mathijs 
 */
public class Books {
    
    private int BookingNr;
    private String  PassportNr;
   
    

    
    public Books(int BookingNr, String PassportNr) {
        this.BookingNr = BookingNr;
        this.PassportNr = PassportNr;
        
    }

    public int getBookingNr() {
        return BookingNr;
    }
    public String getPassportNr() {
        return PassportNr;
    }
    public void setBookingNr(int BookingNr) {
        this.BookingNr = BookingNr;
    }
    public void setPassportNr(String PassportNr) {
        this.PassportNr = PassportNr;
    }

  
    
   
    
}
