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
public class Booking {
    
    
    private int BookingNr;    
    private int NrOfPeople;
    private int NrOfStops;
    private int Insurance;
    private double TotalPrice;

    public Booking(int BookingNr, int NrOfPeople,int NrOfStops, int Insurance, double TotalPrice) {
        this.BookingNr = BookingNr;
        this.NrOfPeople = NrOfPeople;
        this.NrOfStops = NrOfStops;
        this.Insurance = Insurance;
        this.TotalPrice = TotalPrice;
    }

    public int getBookingNr() {
        return BookingNr;
    }
    public int getNrOfPeople() {
        return NrOfPeople;
    }
    public void setBookingNr(int BookingNr) {
        this.BookingNr = BookingNr;
    }
    public void setNrOfPeople(int NrOfPeople) {
        this.NrOfPeople = NrOfPeople;
    }

    public int getNrOfStops() {
        return NrOfStops;
    }

    public void setNrOfStops(int NrOfStops) {
        this.NrOfStops = NrOfStops;
    }

    public int getInsurance() {
        return Insurance;
    }

    public void setInsurance(int Insurance) {
        this.Insurance = Insurance;
    }


    
    

    @Override
    public String toString() {
        return "BookingNr: " + BookingNr +
               ", Number of persons: " + NrOfPeople + 
                ", Number of stops: " + NrOfStops;
    }
    
    
    
}
