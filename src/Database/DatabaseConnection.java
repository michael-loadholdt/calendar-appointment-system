/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 *
 * @author micha
 */
public class DatabaseConnection {
    
    //Make Database connection
    private static final String databaseName = "U05zoo";
    private static final String databaseURL = "jdbc:mysql://3.227.166.251/" + databaseName; 
    private static final String username = "U05zoo";
    private static final String password = "53688654311";
    private static final String driver = "com.mysql.jdbc.Driver";
    static Connection connection;
    
    public static Connection makeConnection() throws ClassNotFoundException, SQLException{
//        
//        Properties p = new Properties();
//        FileInputStream i = null;
//        MysqlDataSource mysqlDS = null;
//        
//        try{
//            i = new FileInputStream("/Utilites/DatabaseProperties.properties");
//            p.load(i);
//            mysqlDS = new MysqlDataSource();
//            mysqlDS.setURL(p.getProperty("MYSQL_DB_URL"));
//            mysqlDS.setUser(p.getProperty("MYSQL_DB_USERNAME"));
//            mysqlDS.setPassword(p.getProperty("MYSQL_DB_PASSWORD"));
//        }
//        catch(IOException e){
//            System.out.println(e.getMessage());
//        }
        
        Class.forName(driver);
        connection = DriverManager.getConnection(databaseURL, username, password);
        
        return connection;
        
    }
    
    public static void closeConnection() throws SQLException{
        
        connection.close();

    }
    
//    public static MysqlDataSource getDataSource(){
//        
//        ResourceBundle bundle = ResourceBundle.getBundle("DatabaseProperties.properties");
//        MysqlDataSource mySQLDataSource = null;
//        
//            mySQLDataSource = new MysqlDataSource();
//            mySQLDataSource.setURL(bundle.getString("DatabaseURL"));
//            mySQLDataSource.setUser(bundle.getString("DatabaseUsername"));
//            mySQLDataSource.setPassword(bundle.getString("DatabasePassword"));
//            
//        return mySQLDataSource;
//
//    }
}    
    