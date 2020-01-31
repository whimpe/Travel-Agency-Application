/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanflight.db;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @authors Willem Himpe & Thomas Mathijs 
 */
public class DBConnect {
    private static final String DB_NAME = "db2019_19";//vul hier uw databank naam in
    private static final String DB_PASS = "ejdhnfhh";//vul hier uw databank paswoord in
    
    public static java.sql.Connection getConnection() throws DBException {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String protocol = "jdbc";
            String subProtocol = "mysql";
            String myDatabase = "//pdbmbook.com/" + DB_NAME;
            String URL = protocol + ":" + subProtocol + ":" + myDatabase;

            con = DriverManager.getConnection(URL, DB_NAME, DB_PASS);
            return con;
            
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            closeConnection(con);
            throw new DBException(sqle);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            closeConnection(con);
            throw new DBException(cnfe);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            closeConnection(con);
            throw new DBException(ex);
        }
    }
    
    public static void closeConnection(Connection con) {
        try {
		 if(con != null)
            	con.close();
        } catch (SQLException sqle) {
            //do nothing
        }
    }
    
        
               
    }

