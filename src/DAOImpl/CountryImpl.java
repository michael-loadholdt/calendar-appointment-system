/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOImpl;

import Database.DatabaseConnection;
import Model.Country;
import Utilities.DateTimeUtility;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Interfaces.CountryInterface;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author micha
 */
public class CountryImpl implements CountryInterface {
    
     private ObservableList<Country> allCountries = FXCollections.observableArrayList();
    
    //Create Country
    @Override
    public int createCountry(Country country) throws SQLException{
        
        Connection connection = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sqlStatement = "INSERT INTO country(country, createDate, createdBy, lastUpdateBy) VALUES (?,?,?,?)"; 
        Optional<Country> compareCountry = getCountry(country.getCountry());
        int countryId = 0;
        if(compareCountry.isPresent()){
            if(country.getCountry().equals(compareCountry.get().getCountry())){
                countryId = compareCountry.get().getCountryId();

            }
        }    
            else{
                try{

                    PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, country.getCountry());
                    LocalDateTime createDate = country.getCreateDate();
                    Timestamp timestamp = DateTimeUtility.localDateTimeToUTC(createDate);
                    preparedStatement.setTimestamp(2, timestamp);
                    preparedStatement.setString(3, country.getCreatedBy());
                    preparedStatement.setString(4, country.getLastUpdateBy());
                    preparedStatement.executeUpdate();

                    ResultSet result = preparedStatement.getGeneratedKeys();
                    if(result.next()){

                    countryId = result.getInt(1);

                    }
                    else{
                    System.out.println("Error getting key");
                    }

                }
                catch(SQLException e){
                    System.out.println(e.getMessage());
                }
            }
        connection.close();
        return countryId;
    }
    
    //Read Country
    @Override
    public Optional<Country> getCountry(int countryId) throws SQLException{
        
        Connection connection = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sqlStatement = "SELECT * FROM country WHERE countryId = ?";
        Country country = null;
        ResultSet result = null;
        
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, countryId);
            result = preparedStatement.executeQuery();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());         
        }
        
        while(result.next()){
            int countryIdR = result.getInt("countryId");
            String countryName = result.getString("country");
            Timestamp createDate = result.getTimestamp("createDate");
            String createdBy = result.getString("createdBy");
            Timestamp lastUpdate = result.getTimestamp("lastUpdate");
            String lastUpdateBy = result.getString("lastUpdateBy");
            LocalDateTime convertedCreateDate = DateTimeUtility.UTCToLocalDateTime(createDate);
            LocalDateTime convertedLastUpdate =DateTimeUtility.UTCToLocalDateTime(lastUpdate);
            
            country = new Country(countryIdR, countryName, convertedCreateDate, createdBy, convertedLastUpdate, lastUpdateBy);
            
        }
        connection.close();
        return Optional.ofNullable(country);
        
    }
    
    @Override
        public Optional<Country> getCountry(String countryParam) throws SQLException{
        
        Connection connection = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sqlStatement = "SELECT * FROM country WHERE country = ?";
        Country country = null;
        ResultSet result = null;
        
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, countryParam);
            result = preparedStatement.executeQuery();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());         
        }
        while(result.next()){
            int countryIdR = result.getInt("countryId");
            String countryName = result.getString("country");
            Timestamp createDate = result.getTimestamp("createDate");
            String createdBy = result.getString("createdBy");
            Timestamp lastUpdate = result.getTimestamp("lastUpdate");
            String lastUpdateBy = result.getString("lastUpdateBy");
            LocalDateTime convertedCreateDate = DateTimeUtility.UTCToLocalDateTime(createDate);
            LocalDateTime convertedLastUpdate =DateTimeUtility.UTCToLocalDateTime(lastUpdate);
            
            country = new Country(countryIdR, countryName, convertedCreateDate, createdBy, convertedLastUpdate, lastUpdateBy);
            
        }
        connection.close();
        return Optional.ofNullable(country);
        
    }
    
    //Read all Countries
    @Override
    public ObservableList<Country> getAllCountries() throws SQLException{
        
        Country country = null;
        String sqlStatement = "SELECT * FROM country";
        ResultSet result = null;
        Connection connection = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        result = preparedStatement.executeQuery();
        
        while(result.next()){
            int countryId = result.getInt("countryId");
            String countryNameR = result.getString("country");
            Timestamp createDate = result.getTimestamp("createDate");
            String createdBy = result.getString("createdBy");
            Timestamp lastUpdate = result.getTimestamp("lastUpdate");
            String lastUpdateBy = result.getString("lastUpdateBy");
            LocalDateTime convertedCreateDate = DateTimeUtility.UTCToLocalDateTime(createDate);
            LocalDateTime convertedLastUpdate =DateTimeUtility.UTCToLocalDateTime(lastUpdate);
            
            country = new Country(countryId, countryNameR, convertedCreateDate, createdBy, convertedLastUpdate, lastUpdateBy);
            allCountries.add(country);
        }
        connection.close();
        return allCountries;
    }
    
    //Update Country
    @Override
    public void updateCountry(Country country){
        
        Connection connection = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sqlStatement = "UPDATE country SET country = ?," +
                                               "lastUpdateBy = ?"
                                               + "WHERE countryId = ?";
        
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, country.getCountry());
            preparedStatement.setString(2, country.getLastUpdateBy());
            preparedStatement.setInt(3, country.getCountryId());
            preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            
        }
        finally{
            try {
                DatabaseConnection.closeConnection();
            } catch (SQLException ex) {
                Logger.getLogger(CityImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
        
    }
    
    //Delete Country
    @Override
    public void deleteCountry(int countryId){
        Connection connection = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sqlStatement = "DELETE * FROM country WHERE countryid = ?";
        try{
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
             preparedStatement.setInt(1, countryId);
             preparedStatement.executeUpdate();
        } 
        catch(SQLException e){
            
        }
        finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(CityImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    
    
}
