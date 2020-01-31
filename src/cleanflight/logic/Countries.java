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
public class Countries {
    
    private int CountryNr;
    private String CountryName;

    

    public Countries(int CountryNr, String CountryName) {
        this.CountryNr = CountryNr;
        this.CountryName = CountryName;
    }
    public Countries(String CountryName){
        this.CountryName = CountryName;
    }
    public Countries(int CountryNr){
        this.CountryNr = CountryNr;
    }
    
    public void setCountryNr(int CountryNr) {
        this.CountryNr = CountryNr;
    }

    public void setCountryName(String CountryName) {
        this.CountryName = CountryName;
    }

    public int getCountryNr() {
        return CountryNr;
    }

    public String getCountryName() {
        return CountryName;
    }
    
    @Override
    public String toString() {
        return  CountryName;
    }
    
    
    
}
