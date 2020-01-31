/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanflight.db;
import static cleanflight.db.DBBooking.getBooking;
import cleanflight.logic.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.lang.Math;
import java.sql.Time;
import java.sql.TimeStamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.text.Format;
import java.util.TimeZone;

/**
 *
 * @authors Willem Himpe & Thomas Mathijs 
 */
public class DBFlight {
    
    
    
   

    public static Flight getFlight(int FlightNr) throws DBException {
    Connection con = null;
        try {
          con = DBConnect.getConnection();
          Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
          String sql = "SELECT f.FlightNr, f.FlightCode,f.DepDate,f.AirplaneNr,f.AirportCodeOrig,f.AirportCodeDest, f.ArrDate,f.PlacesAvailable,f.DepTime,f.ArrTime, aline.AirlineName, "
                  + "apo.Latitude as lat_o, apo.Longitude as lon_o, apo2.Latitude as lat_des, apo2.Longitude as lon_des, aline.AirlinePrice "
                  + "FROM flight as f, airport as apo, airport as apo2, airline as aline "
                  + "WHERE (f.FlightNr='"+FlightNr+"') AND (apo2.AirportCode = f.AirportCodeDest) AND (apo.AirportCode = f.AirportCodeOrig) AND (f.AirlineCode = aline.AirlineCode)";
          ResultSet srs = stmt.executeQuery(sql);
          if(srs.next()){
              int flnr = srs.getInt("FlightNr");
              
              java.sql.Date depdate = srs.getDate("DepDate");
              DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
              String depdate1 = dateFormat.format(depdate);
              int anr = srs.getInt("AirplaneNr");        
              String aco = srs.getString("AirportCodeOrig");
              String acd = srs.getString("AirportCodeDest");
              String alc = srs.getString("AirlineName");
              String fc = srs.getString("FlightCode");
              double pr = srs.getDouble("AirlinePrice");
              java.sql.Date arrdate = srs.getDate("ArrDate");
              String arrdate1 = dateFormat.format(arrdate);
              int plav = srs.getInt("PlacesAvailable");
              Format timeFormat = new SimpleDateFormat("HH:mm:ss");
              java.sql.Time deptime = srs.getTime("DepTime");
              Timestamp deptime2 = new Timestamp(deptime.getTime() + (1000 * 60 * 60 * (-6)));             
              String deptime1 = timeFormat.format(deptime2);              
              java.sql.Time arrtime = srs.getTime("ArrTime");
              Timestamp arrtime2 = new Timestamp(arrtime.getTime() + (1000 * 60 * 60 * (-6)));
              String arrtime1 = timeFormat.format(arrtime2);              
              double lat_o =srs.getDouble("lat_o");
              double lon_o =srs.getDouble("lon_o");
              double lat_des =srs.getDouble("lat_des");
              double lon_des =srs.getDouble("lon_des");
              double distance = distance_in_km(lat_o, lon_o, lat_des, lon_des);              
              double price = calculate_price(distance, pr);                           
              double co2 = calculate_co2_emission(distance);                             
              Flight flight  = new Flight(flnr, depdate1, anr, aco, acd, alc, arrdate1, plav, deptime1, arrtime1,distance, co2, price,fc);
              return flight;
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
    
    public static ArrayList<Flight> getFlights2() throws DBException {
    Connection con = null;
        try {
          con = DBConnect.getConnection();
          Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
          String sql = "SELECT f.FlightNr, f.FlightCode,f.DepDate,f.AirplaneNr,f.AirportCodeOrig,f.AirportCodeDest, f.ArrDate,f.PlacesAvailable,f.DepTime,f.ArrTime, aline.AirlineName, "
                  + "apo.Latitude as lat_o, apo.Longitude as lon_o, apo2.Latitude as lat_des, apo2.Longitude as lon_des, aline.AirlinePrice "
                  + "FROM flight as f, airport as apo, airport as apo2, airline as aline "
                  + "WHERE (f.PlacesAvailable>0) AND (apo2.AirportCode = f.AirportCodeDest) AND (apo.AirportCode = f.AirportCodeOrig) AND (f.AirlineCode = aline.AirlineCode)";
          ResultSet srs = stmt.executeQuery(sql);
          ArrayList<Flight> res = new ArrayList<>();
          while(srs.next()){
              int flnr = srs.getInt("FlightNr");              
              java.sql.Date depdate = srs.getDate("DepDate");
              DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
              String depdate1 = dateFormat.format(depdate);
              int anr = srs.getInt("AirplaneNr");        
              String aco = srs.getString("AirportCodeOrig");
              String acd = srs.getString("AirportCodeDest");
              String alc = srs.getString("AirlineName");
              String fc = srs.getString("FlightCode");
              double pr = srs.getDouble("AirlinePrice");
              java.sql.Date arrdate = srs.getDate("ArrDate");
              String arrdate1 = dateFormat.format(arrdate);
              int plav = srs.getInt("PlacesAvailable");
              Format timeFormat = new SimpleDateFormat("HH:mm:ss");
              java.sql.Time deptime = srs.getTime("DepTime");
              Timestamp deptime2 = new Timestamp(deptime.getTime() + (1000 * 60 * 60 * (-6)));             
              String deptime1 = timeFormat.format(deptime2);              
              java.sql.Time arrtime = srs.getTime("ArrTime");
              Timestamp arrtime2 = new Timestamp(arrtime.getTime() + (1000 * 60 * 60 * (-6)));
              String arrtime1 = timeFormat.format(arrtime2);              
              double lat_o =srs.getDouble("lat_o");
              double lon_o =srs.getDouble("lon_o");
              double lat_des =srs.getDouble("lat_des");
              double lon_des =srs.getDouble("lon_des");
              double distance = distance_in_km(lat_o, lon_o, lat_des, lon_des);              
              double price = calculate_price(distance, pr);                           
              double co2 = calculate_co2_emission(distance);                             
              Flight flight  = new Flight(flnr, depdate1, anr, aco, acd, alc, arrdate1, plav, deptime1, arrtime1,distance, co2, price,fc);
              res.add(flight);
            }
          return res;
        } catch (Exception ex) {
          ex.printStackTrace();
          DBConnect.closeConnection(con);
          throw new DBException(ex);
        }
      }
    
    
    
    
    
    
    
    
    
    public static ArrayList<Flight> getFlights() throws DBException{
        
         Connection con = null;
        try {
            con = DBConnect.getConnection();
          
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
          
            String sql = "SELECT FlightNr FROM flight WHERE PlacesAvailable > 0 ";

            ResultSet srs = stmt.executeQuery(sql);
            ArrayList<Flight> flights = new ArrayList<>();
            flights.clear();
          
          while (srs.next()){
             flights.add(getFlight(srs.getInt("FlightNr")));
                }
          DBConnect.closeConnection(con);
          return flights;

        } catch (Exception ex) {
          ex.printStackTrace();
          DBConnect.closeConnection(con);
          throw new DBException(ex);
        }                      
        
    }
    
    
  
    

    public static  ArrayList<Flight>  get_check_flight(String origin,String destination,String startdate,int amount_seats,double high, double maxco2, double rangedays) throws DBException, SQLException, ParseException{        
     Connection con = null;
     
  
    boolean b = false;
    ArrayList<Flight> flights = new ArrayList<>();
    ArrayList<Flight> flights1 = new ArrayList<>();
    

    Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
    int rd = (int)(rangedays * (-1));
    System.out.println(rd);
        try{  
    con = DBConnect.getConnection();
    Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
    for(int i = (rd); i <= rangedays; i++){
       DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
       LocalDateTime ldt = LocalDateTime.ofInstant(date1.toInstant(), ZoneId.systemDefault());
       LocalDateTime x = ldt.plusDays(i);
       String z = x.format(dateFormat);
    
    
                        
    String sql = "SELECT DISTINCT (f.FlightNr ), f.FlightCode, f.DepDate, f.ArrDate, f.AirplaneNr, al.AirlineName, "
                + " f.AirportCodeOrig,f.AirportCodeDest,f.DepTime,f.ArrTime,f.PlacesAvailable, "
                + " apo.Latitude as lat_o, apo.Longitude as lon_o, apo2.Latitude as lat_des, apo2.Longitude as lon_des, al.AirlinePrice "
           
                +" FROM  flight  as f,  airplane as apl,   airline as al,  airport as apo,airport as apo2 "
                +" WHERE (f.AirplaneNr=apl.AirplaneNr) AND (f.AirlineCode = al.AirlineCode) AND (f.AirportCodeOrig=apo.AirportCode) AND (f.AirportCodeDest=apo2.AirportCode) "
                +" AND (f.PlacesAvailable>'"+amount_seats+"') AND (f.DepDate = '"+z+"') "
                +" AND  (f.AirportCodeOrig = apo.AirportCode) AND (apo.AirportName = '"+origin+"') AND (f.AirportCodeDest = apo2.AirportCode) AND (apo2.AirportName = '"+destination+"') ";
    
   
    ResultSet srs = stmt.executeQuery(sql);
    
                      

        while (srs.next()){
           int flnr = srs.getInt("FlightNr");
              java.sql.Date depdate = srs.getDate("DepDate");
              DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
              String depdate1 = dateFormat1.format(depdate);
              int anr = srs.getInt("AirplaneNr");        
              String aco = srs.getString("AirportCodeOrig");
              String acd = srs.getString("AirportCodeDest");
              String alc = srs.getString("AirlineName");
              Double pr = srs.getDouble("AirlinePrice");
              String fc = srs.getString("FlightCode");
              java.sql.Date arrdate = srs.getDate("ArrDate");
              String arrdate1 = dateFormat1.format(arrdate);
              int plav = srs.getInt("PlacesAvailable");
              Time deptime = srs.getTime("DepTime");
              DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
              Timestamp deptime2 = new Timestamp(deptime.getTime() + (1000 * 60 * 60 * 3));
             
              String deptime1 = timeFormat.format(deptime2);
              java.sql.Time arrtime = srs.getTime("ArrTime");
              Timestamp arrtime2 = new Timestamp(arrtime.getTime() + (1000 * 60 * 60 * (-6)));

              String arrtime1 = timeFormat.format(arrtime2);
            
              double lat_o =srs.getDouble("lat_o");
              double lon_o =srs.getDouble("lon_o");
              double lat_des =srs.getDouble("lat_des");
              double lon_des =srs.getDouble("lon_des");
              double distance = distance_in_km(lat_o, lon_o, lat_des, lon_des);
              double price = calculate_price(distance,pr);
              double co2 = calculate_co2_emission(distance);              
            if (price<=high && co2 <= maxco2){
                Flight flight  = new Flight(flnr, depdate1, anr, aco, acd, alc, arrdate1, plav, deptime1, arrtime1,distance, co2, price,fc);
                flights1.add(flight); 
                b = true;
                 for(int ij = 0; ij < flights1.size(); ij++){
        }
                
                }
        }
            }
            if(b == false){
                  
                for(int i =  (rd); i <=  rangedays; i++){
                 
                   DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                   LocalDateTime ldt = LocalDateTime.ofInstant(date1.toInstant(), ZoneId.systemDefault());

                   LocalDateTime x = ldt.plusDays(i);
                   String z = x.format(dateFormat);
                   
                   
                String sql1 = "SELECT DISTINCT (f.FlightNr ),f.FlightCode, f.DepDate, f.ArrDate, f.AirplaneNr, al.AirlineName, "
                + " f.AirportCodeOrig,f.AirportCodeDest,f.DepTime,f.ArrTime,f.PlacesAvailable, "
                + " apo.Latitude as lat_o, apo.Longitude as lon_o, apo2.Latitude as lat_des, apo2.Longitude as lon_des, al.AirlinePrice "
           
                +" FROM  flight  as f,  airplane as apl,   airline as al,  airport as apo,airport as apo2 "
                +" WHERE (f.AirplaneNr=apl.AirplaneNr) AND (f.AirlineCode = al.AirlineCode) AND (f.AirportCodeOrig=apo.AirportCode) AND (f.AirportCodeDest=apo2.AirportCode) "
                +" AND (f.PlacesAvailable>'"+amount_seats+"') AND (f.DepDate = '"+z+"') "
                +" AND  (f.AirportCodeOrig = apo.AirportCode) AND (apo.AirportName = '"+origin+"') ";
    
   
                ResultSet srs1 = stmt.executeQuery(sql1);
                
                 
                    while (srs1.next()){
                        int flnr1 = srs1.getInt("FlightNr");
                        java.sql.Date depdate2 = srs1.getDate("DepDate");
                        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                        String depdate3 = dateFormat1.format(depdate2);
                        int anr1 = srs1.getInt("AirplaneNr");        
                        String aco1 = srs1.getString("AirportCodeOrig");
                        String acd1 = srs1.getString("AirportCodeDest");
                        String alc1 = srs1.getString("AirlineName");
                        Double pr1 = srs1.getDouble("AirlinePrice");

                        String fc1 = srs1.getString("FlightCode");
                        java.sql.Date arrdate2 = srs1.getDate("ArrDate");
                        String arrdate3 = dateFormat1.format(arrdate2);
                        int plav1 = srs1.getInt("PlacesAvailable");
                        java.sql.Time deptime2 = srs1.getTime("DepTime");
                        
                        DateFormat timeFormat1 = new SimpleDateFormat("HH:mm:ss");
                        Timestamp deptime4 = new Timestamp(deptime2.getTime() + (1000 * 60 * 60 * (-6)));

                        String deptime3 = timeFormat1.format(deptime4);
                        java.sql.Time arrtime2 = srs1.getTime("ArrTime");
                        Timestamp arrtime4 = new Timestamp(arrtime2.getTime() + (1000 * 60 * 60 * (-6)));

                        String arrtime3 = timeFormat1.format(arrtime4);
                     
                        double lat_o1 =srs1.getDouble("lat_o");
                        double lon_o1=srs1.getDouble("lon_o");
                        double lat_des1 =srs1.getDouble("lat_des");
                        double lon_des1 =srs1.getDouble("lon_des");
                        double distance1 = distance_in_km(lat_o1, lon_o1, lat_des1, lon_des1);
                        double price1 = calculate_price(distance1,pr1);
                        double co21 = calculate_co2_emission(distance1);  
                        String destin = destination;
                        String sd = depdate3;
                        int aseat = amount_seats;
                        double high1 = high;
                        double high2 = maxco2;
                         
                          if (price1<=high){
                             Flight flight  = new Flight(flnr1, depdate3, anr1, aco1, acd1, alc1, arrdate3, plav1, deptime3, arrtime3, distance1, co21, price1,fc1);
                             
                             flights.add(flight);   
                              for(int ij = 0; ij < flights.size(); ij++){
                
        }
                             flights1 = get_check_flight2(flights, destin, aseat, high1, high2, arrdate3,arrtime3);
                             
                             }
                       
                    }  
                   
                  
                }return flights1;
                    
        }
          
            
            DBConnect.closeConnection(con);
            return flights1;
            
      }catch (DBException | SQLException ex) {
          DBConnect.closeConnection(con);
          throw new DBException(ex);
        
    
    }
    }
    
      public static  ArrayList<Flight>  get_check_flight2(ArrayList<Flight> flights, String destination, int aseat, double high1, double mxco2, String arrdate10, String arrtime10) throws DBException, SQLException{        
    Connection con = null;
    try{
    con = DBConnect.getConnection();
    Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
    ArrayList <Flight> fl = new ArrayList <>();
    fl = flights;
    ArrayList <String> start = new ArrayList<>();
        for(int i = 0; i < flights.size(); i++) {
            String a2 = fl.get(i).getAirportCodeDest();           
            start.add(a2);
            }
        
    ArrayList<Flight> flights1 = new ArrayList<>();
    for(int i = 0; i < start.size(); i++){
    double totp = 0;
    double totc = 0;
    double price2 = fl.get(i).getPrice();
    double co22 = fl.get(i).getCo2();                
    String sql = "SELECT DISTINCT (f.FlightNr ),f.FlightCode, f.DepDate, f.ArrDate, f.AirplaneNr, al.AirlineName, "
                + " f.AirportCodeOrig,f.AirportCodeDest,f.DepTime,f.ArrTime,f.PlacesAvailable, "
                + " apo.Latitude as lat_o, apo.Longitude as lon_o, apo2.Latitude as lat_des, apo2.Longitude as lon_des, al.AirlinePrice "
           
                +" FROM  flight  as f,  airplane as apl,   airline as al,  airport as apo,airport as apo2 "
                +" WHERE (f.AirplaneNr=apl.AirplaneNr) AND (f.AirlineCode = al.AirlineCode) AND (f.AirportCodeOrig=apo.AirportCode) AND (f.AirportCodeDest=apo2.AirportCode) "
                +" AND (f.PlacesAvailable>'"+aseat+"') AND (f.AirportCodeOrig = '"+start.get(i)+"') AND (f.DepDate = '"+arrdate10+"') "
                +" AND  (f.AirportCodeOrig = apo.AirportCode) AND (f.AirportCodeDest = apo2.AirportCode) AND (apo2.AirportName = '"+destination+"') ";
    
   
    ResultSet srs = stmt.executeQuery(sql);
    
   
        while (srs.next()){
              int flnr = srs.getInt("FlightNr");
              java.sql.Date depdate = srs.getDate("DepDate");
              DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
              String depdate1 = dateFormat.format(depdate);
              int anr = srs.getInt("AirplaneNr");        
              String aco = srs.getString("AirportCodeOrig");
              String acd = srs.getString("AirportCodeDest");
              String alc = srs.getString("AirlineName");
              Double pr = srs.getDouble("AirlinePrice");

              String fc = srs.getString("FlightCode");
              java.sql.Date arrdate = srs.getDate("ArrDate");
              String arrdate1 = dateFormat.format(arrdate);
              int plav = srs.getInt("PlacesAvailable");
              java.sql.Time deptime = srs.getTime("DepTime");
              DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
              Timestamp deptime2 = new Timestamp(deptime.getTime() + (1000 * 60 * 60 * (-6)));

              String deptime1 = timeFormat.format(deptime2);
              java.sql.Time arrtime = srs.getTime("ArrTime");
              Timestamp arrtime2 = new Timestamp(arrtime.getTime() + (1000 * 60 * 60 * (-6)));

              String arrtime1 = timeFormat.format(arrtime2);
                 boolean time = true;
                 int x = Integer.parseInt(deptime1.substring(0,2));
                 int y = Integer.parseInt(arrtime10.substring(0,2));
              

                        if (x < y+2) {
                            time = false;
                        }
              double lat_o =srs.getDouble("lat_o");
              double lon_o =srs.getDouble("lon_o");
              double lat_des =srs.getDouble("lat_des");
              double lon_des =srs.getDouble("lon_des");
              double distance = distance_in_km(lat_o, lon_o, lat_des, lon_des);
              double price = calculate_price(distance, pr);
              double co2 = calculate_co2_emission(distance); 
              
              totp = price + price2;
              
              totc = co2 + co22;
                      
              if (totp<=high1 && totc<= mxco2 && time == true){
              
                Flight flight  = new Flight(flnr, depdate1, anr, aco, acd, alc, arrdate1, plav, deptime1, arrtime1, distance, co2, price,fc);
                flights1.add(fl.get(i));
                flights1.add(flight);  
                
                }                                     
        }  
            return flights1 ;

        }         
    DBConnect.closeConnection(con);

    return null;
    }catch (DBException | SQLException ex) {
          DBConnect.closeConnection(con);
          throw new DBException(ex);
    
    }
    }
     
    
    public static void reducePlacesFlight(int FlightNr,int aantal_personen) throws DBException{
        
        Connection con = null;
        try{
            con = DBConnect.getConnection();
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            int places_avb = 0;        
            String sql1 = "SELECT PlacesAvailable FROM flight WHERE FlightNr="+FlightNr;
            ResultSet srs = stmt.executeQuery(sql1);
            while (srs.next()){
                places_avb = srs.getInt("PlacesAvailable");                                    
                }
            DBConnect.closeConnection(con);
            
            con = DBConnect.getConnection();
            String sql2 = "UPDATE flight "
                        + " SET PlacesAvailable =?"
                        + " WHERE FlightNr = ?";
            
            PreparedStatement pstmt = con.prepareStatement(sql2,Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1,(places_avb-aantal_personen));
            pstmt.setInt(2,FlightNr);
            pstmt.execute();            
            DBConnect.closeConnection(con);

    
    
        }catch (DBException | SQLException ex) {
          DBConnect.closeConnection(con);
        }                      
        
    }  
    
    
    public static double TotalRevenue() {
        Connection con = null;
        double total_rev =0;
        try{
            con = DBConnect.getConnection();
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT SUM(TotalPrice) as result FROM booking" ;
            ResultSet srs = stmt.executeQuery(sql);
        
            
            while (srs.next()){
              total_rev = srs.getDouble("result");
                        
                }
            DBConnect.closeConnection(con);
            return total_rev;
        }catch (DBException | SQLException ex) {
          DBConnect.closeConnection(con);
        }                      
        return total_rev;
    } 
    
     public static double TotalProfit() {
        Connection con = null;
        double total_p = 0;
        double total_prof = 0;
        try{
            con = DBConnect.getConnection();
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT SUM(NrOfPeople) as result FROM booking" ;
            ResultSet srs = stmt.executeQuery(sql);
        
            
            while (srs.next()){
              total_p = srs.getDouble("result");
               total_prof = 50 * total_p;
                }
            DBConnect.closeConnection(con);
            return total_prof;
        }catch (DBException | SQLException ex) {
          DBConnect.closeConnection(con);
        }                      
        return total_prof;
    } 
    public static double TotalCO2() {
        
        double total_co2 =0;
        Connection con = null;
        try{
            con = DBConnect.getConnection();
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT b.BookingNr,f.FlightNr,b.NrOfPeople,apo1.Latitude as lat1,apo1.Longitude as lon1,apo2.Latitude as lat2,apo2.Longitude as lon2 " 
                     + " FROM flight as f,details as d, booking as b,airport as apo1, airport as apo2 " 
                     + " WHERE (f.FlightNr=d.FlightNr) AND (d.BookingNr=b.BookingNr) AND (apo1.AirportCode=f.AirportCodeOrig) AND (apo2.AirportCode=f.AirportCodeDest) ";
            ResultSet srs = stmt.executeQuery(sql);
            int booking_id,FlightNr,aant_pers ;
            double apo1_lat,apo2_lat,apo1_long,apo2_long,distance1,co2_emmissie;
            
    
            while (srs.next()){
                booking_id = srs.getInt("BookingNr");
                FlightNr = srs.getInt("FlightNr");
                aant_pers = srs.getInt("NrOfPeople");
                apo1_lat = srs.getDouble("lat1");
                apo1_long = srs.getDouble("lon1");
                apo2_lat = srs.getDouble("lat2");
                apo2_long = srs.getDouble("lon2");
                distance1 = distance_in_km(apo1_lat, apo1_long, apo2_lat, apo2_long);
                co2_emmissie = calculate_co2_emission(distance1);
                total_co2 = total_co2+(co2_emmissie*aant_pers);
                
                }
            DBConnect.closeConnection(con);
            return total_co2;

        }catch (DBException | SQLException ex) {
          DBConnect.closeConnection(con);
        }                      
        return total_co2;
    } 
      
    
    
    public static ArrayList<Flight> allFlightsCustomer(String PassportNr) throws DBException, SQLException{
        Connection con = null;
        try {
          con = DBConnect.getConnection();
          Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
          String sql ="SELECT f.FlightNr,f.FlightCode,f.DepDate,f.AirplaneNr,f.AirportCodeOrig,f.AirportCodeDest, f.ArrDate,f.PlacesAvailable,f.DepTime,f.ArrTime,aline.AirlineName, "
                  + "apo1.Latitude as lat_o, apo1.Longitude as lon_o, apo2.Latitude as lat_des, apo2.Longitude as lon_des, aline.AirlinePrice "
                  + "FROM flight as f, details as d, customer as c, airport as apo1, airport as apo2, airline as aline, books as boo, booking as b, airplane as fp "
                        + " WHERE  (f.FlightNr=d.FlightNr) AND (d.BookingNr =b.BookingNr) AND (c.PassportNr=boo.PassportNr) " 
                        + " AND (boo.BookingNr=b.BookingNr) AND (c.PassportNr= '"+PassportNr+"') " 
                        + " AND (apo1.AirportCode=f.AirportCodeOrig) AND "
                        + "(apo2.AirportCode=f.AirportCodeDest) AND (f.AirplaneNr=fp.AirplaneNr) AND (aline.AirlineCode = f.AirlineCode)";
           

          ResultSet srs = stmt.executeQuery(sql);

          ArrayList<Flight> res_flights = new ArrayList<>();      
          while (srs.next()){
              int flnr = srs.getInt("FlightNr");
              java.sql.Date depdate = srs.getDate("DepDate");
              DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
              String depdate1 = dateFormat.format(depdate);
              int anr = srs.getInt("AirplaneNr");        
              String aco = srs.getString("AirportCodeOrig");
              String acd = srs.getString("AirportCodeDest");
              String alc = srs.getString("AirlineName");
              double pr = srs.getDouble("AirlinePrice");
              String fc = srs.getString("FlightCode");
              java.sql.Date arrdate = srs.getDate("ArrDate");
              String arrdate1 = dateFormat.format(arrdate);
              int plav = srs.getInt("PlacesAvailable");
              java.sql.Time deptime = srs.getTime("DepTime");
              DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
              Timestamp deptime2 = new Timestamp(deptime.getTime() + (1000 * 60 * 60 * (-6)));

              String deptime1 = timeFormat.format(deptime2);
              java.sql.Time arrtime = srs.getTime("ArrTime");
              Timestamp arrtime2 = new Timestamp(arrtime.getTime() + (1000 * 60 * 60 * (-6)));

              String arrtime1 = timeFormat.format(arrtime2);
              
              double lat_o =srs.getDouble("lat_o");
              double lon_o =srs.getDouble("lon_o");
              double lat_des =srs.getDouble("lat_des");
              double lon_des =srs.getDouble("lon_des");
              double distance = distance_in_km(lat_o, lon_o, lat_des, lon_des);
              double price = calculate_price(distance, pr);
              double co2 = calculate_co2_emission(distance);  
              
              Flight flight  = new Flight(flnr, depdate1, anr, aco, acd, alc, arrdate1, plav, deptime1, arrtime1, distance, co2, price,fc);
              
              res_flights.add(flight);            
          }
          
          DBConnect.closeConnection(con);
          return res_flights;

        } catch (DBException | SQLException ex) {
          DBConnect.closeConnection(con);
          throw new DBException(ex);
        }
    }
     
     
    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;
    public static double distance_in_km(double lat1, double lng1, double lat2, double lng2) {
		
    double latDistance = Math.toRadians(lat1 - lat2);
    double lngDistance = Math.toRadians(lng1 - lng2);

    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
      + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
      * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c));
	}

    private static double calculate_price(double distance, double pr){
        double dist = (distance / 50 * pr) + 50;
        return dist;
    
        }
    
    private static double calculate_co2_emission(double distance){
        double co = distance * 115;
        return co;
    
        }            
    
}
