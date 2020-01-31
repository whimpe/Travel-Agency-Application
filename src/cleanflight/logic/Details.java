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
public class Details {
    
    private int BookingNr;
    private int FlightNr;

    public Details(int BookingNr, int FlightNr) {
        this.BookingNr = BookingNr;
        this.FlightNr = FlightNr;
    }

    public int getBookingNr() {
        return BookingNr;
    }

    public int getFlightNr() {
        return FlightNr;
    }

    public void setBookingNr(int BookingNr) {
        this.BookingNr = BookingNr;
    }

    public void setFlightNr(int FlightNr) {
        this.FlightNr = FlightNr;
    }

    @Override
    public String toString() {
        return "Details{" + "BookingNr: " + BookingNr + ", FlightNr: " + FlightNr + '}';
    }
    
    
           
          
    
}
