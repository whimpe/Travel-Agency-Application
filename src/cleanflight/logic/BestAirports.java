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
public class BestAirports {
    private String Airport;
    private int NrOfBookings;
    private int Rank;
    
    public BestAirports(int Rank, String Airport, int NrOfBookings) {
        this.Rank = Rank;
        this.Airport = Airport;
        this.NrOfBookings = NrOfBookings;
    }
    
    @Override
    public String toString() {
        return Rank + ": " + Airport + " --> Number of starts of a flight: " + NrOfBookings;
    }

    public void setAirport(String Airport) {
        this.Airport = Airport;
    }

    public void setNrOfBookings(int NrOfBookings) {
        this.NrOfBookings = NrOfBookings;
    }

    public void setRank(int Rank) {
        this.Rank = Rank;
    }

    public String getAirport() {
        return Airport;
    }

    public int getNrOfBookings() {
        return NrOfBookings;
    }

    public int getRank() {
        return Rank;
    }

    
    
}
