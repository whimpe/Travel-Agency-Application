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
public class Airline {
    
    private int AirlineNr;
    private String AirlineName;
    private double AirlinePrice;
    
    public Airline(int AirlineNr, String AirlineName, double AirlinePrice) {
        this.AirlineNr = AirlineNr;
        this.AirlineName = AirlineName;
        this.AirlinePrice = AirlinePrice;
    }

    public int getAirlineName() {
        return AirlineNr;
    }

    public String getName() {
        return AirlineName;
    }

   

    public double getAirlinePrice() {
        return AirlinePrice;
    }

    public void setAirlineName(int AirlineNr) {
        this.AirlineNr = AirlineNr;
    }

    public void setName(String AirlineName) {
        this.AirlineName = AirlineName;
    }

   
    public void setAirlinePrice(double AirlinePrice) {
        this.AirlinePrice = AirlinePrice;
    }
    
    
}
