/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import static Database.DatabaseConnection.connection;
import java.sql.*;
        
/**
 *
 * @author micha
 */
public class Query {
    
    private static String query;
    private static Statement statement;
    private static ResultSet result;
    
    public static void makeQuery(String sqlStatement){
        query = sqlStatement;
        try{
      
            statement = connection.createStatement();
            if(query.toLowerCase().startsWith("select")){
                result = statement.executeQuery(query);
            }
            if(query.toLowerCase().startsWith("insert") || query.toLowerCase().startsWith("update") || query.toLowerCase().startsWith("delete")){
                statement.executeUpdate(query);
            }
                
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public static ResultSet getResult(){
        return result;
    }
    
    public static String getLoginPassword(String username) throws SQLException{
        
        Query.makeQuery("SELECT password FROM user WHERE userName = '" + username +"'");
        
        String resultS = null;
        
        while(result.next()){
            
        resultS = result.getString("password");
        
        }
        return resultS;
        
        
        
        
        
    }
    
}
