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
public class BestCustomer {
    private int NrOfBookings;
    private String FirstName;
    private String LastName;
    private int Rank;

    @Override
    public String toString() {
        return Rank + ": " + FirstName + " " + LastName + " --> Number of bookings: " + NrOfBookings ;
    }

    public BestCustomer(int Rank, int NrOfBookings, String FirstName, String LastName) {
        this.NrOfBookings = NrOfBookings;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Rank = Rank;
    }

    public int getRank() {
        return Rank;
    }

    public int getNrOfBookings() {
        return NrOfBookings;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setNrOfBookings(int NrOfBookings) {
        this.NrOfBookings = NrOfBookings;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public void setRank(int Rank) {
        this.Rank = Rank;
    }
    
    
    
}
