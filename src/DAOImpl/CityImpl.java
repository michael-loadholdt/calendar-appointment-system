/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOImpl;

import Database.DatabaseConnection;
import Interfaces.CityInterface;
import Model.City;
import Utilities.DateTimeUtility;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author micha
 */
public class CityImpl implements CityInterface {
    
    private ObservableList<City> allCities = FXCollections.observableArrayList();
    
    //Create City
    @Override
    public int createCity(City city) throws SQLException{
        Connection connection = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(CityImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sqlStatement = "INSERT INTO city(city, countryId, createDate, createdBy, lastUpdateBy)"
                              +"VALUES (?,?,?,?,?)";
        Optional<City> compareCity = getCity(city.getCity());
        int cityId = 0;
        if(compareCity.isPresent()){
            if(city.getCity().equals(compareCity.get().getCity())){
                cityId = compareCity.get().getCityId();
            }
        }    
            else{
                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, city.getCity());
                    preparedStatement.setInt(2, city.getCountryId());
                    LocalDateTime createDate = city.getCreateDate();
                    Timestamp timestamp = DateTimeUtility.localDateTimeToUTC(createDate);
                    preparedStatement.setTimestamp(3, timestamp);
                    preparedStatement.setString(4, city.getCreatedBy());
                    preparedStatement.setString(5, city.getLastUpdateBy());
                    preparedStatement.executeUpdate();
                    
                    ResultSet result = preparedStatement.getGeneratedKeys();
                    if(result.next()){
                
                        cityId = result.getInt(1);

                    }
                    else{
                        System.out.println("Error getting key");
                    }
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
    
       return cityId; 
    }
    
    //Read City
    @Override
    public Optional<City> getCity(int cityId) throws SQLException{
        City city = null;
        Connection connection = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(CityImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sqlStatement = "SELECT * FROM city WHERE cityId = ?";

        ResultSet result = null;
        
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setInt(1, cityId);
        result = preparedStatement.executeQuery();
        
        while(result.next()){
            int cityIdR = result.getInt("cityId");
            String cityName = result.getString("city");
            int countryId = result.getInt("countryId");
            Timestamp createDate = result.getTimestamp("createDate");
            String createdBy = result.getString("createdBy");
            Timestamp lastUpdate = result.getTimestamp("lastUpdate");
            String lastUpdateBy = result.getString("lastUpdateBy");
            LocalDateTime convertedCreateDate = DateTimeUtility.UTCToLocalDateTime(createDate);
            LocalDateTime convertedLastUpdate =DateTimeUtility.UTCToLocalDateTime(lastUpdate);
            
            city = new City(cityIdR, cityName, countryId, convertedCreateDate, createdBy, convertedLastUpdate, lastUpdateBy);
            
        }
        
        connection.close();
        return Optional.ofNullable(city);
    }
    
    @Override
        public Optional<City> getCity(String cityParam) throws SQLException{
        City city = null;
        Connection connection = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(CityImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sqlStatement = "SELECT * FROM city WHERE city = ?";
        ResultSet result =null;
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setString(1, cityParam);
        result = preparedStatement.executeQuery();
        
        while(result.next()){
            int cityIdR = result.getInt("cityId");
            String cityName = result.getString("city");
            int countryId = result.getInt("countryId");
            Timestamp createDate = result.getTimestamp("createDate");
            String createdBy = result.getString("createdBy");
            Timestamp lastUpdate = result.getTimestamp("lastUpdate");
            String lastUpdateBy = result.getString("lastUpdateBy");
            LocalDateTime convertedCreateDate = DateTimeUtility.UTCToLocalDateTime(createDate);
            LocalDateTime convertedLastUpdate =DateTimeUtility.UTCToLocalDateTime(lastUpdate);
            
            city = new City(cityIdR, cityName, countryId, convertedCreateDate, createdBy, convertedLastUpdate, lastUpdateBy);
            
        }
        
        connection.close();
        return Optional.ofNullable(city);
    }
    
    //Read all Cities
    @Override
    public ObservableList<City> getAllCities() throws SQLException{
        
        City city = null;
        
        Connection connection = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(CityImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sqlStatement = "SELECT * FROM city";
        ResultSet result = null;
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        result = preparedStatement.executeQuery();
        
        while(result.next()){
            int cityId = result.getInt("cityId");
            String cityNameR = result.getString("city");
            int countryId = result.getInt("countryId");
            Timestamp createDate = result.getTimestamp("createDate");
            String createdBy = result.getString("createdBy");
            Timestamp lastUpdate = result.getTimestamp("lastUpdate");
            String lastUpdateBy = result.getString("lastUpdateBy");
            LocalDateTime convertedCreateDate = DateTimeUtility.UTCToLocalDateTime(createDate);
            LocalDateTime convertedLastUpdate =DateTimeUtility.UTCToLocalDateTime(lastUpdate);
            
            city = new City(cityId, cityNameR, countryId, convertedCreateDate, createdBy, convertedLastUpdate, lastUpdateBy);
            allCities.add(city);
        }
        connection.close();
        return allCities;
        
    }
    
    //Update City
    @Override
    public void updateCity(City city){
        
        Connection connection = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(CityImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sqlStatement = "UPDATE city SET city = ?," +
                                              "countryId = ?," +
                                              "lastUpdateBy = ?" +
                                              "WHERE cityId = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1,city.getCity());
            preparedStatement.setInt(2, city.getCountryId());
            preparedStatement.setString(3, city.getLastUpdateBy());
            preparedStatement.setInt(4, city.getCityId());

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
    //Delete City
    @Override
    public void deleteCity(int cityId){
        
        Connection connection = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(CityImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sqlStatement = "DELETE * FROM city WHERE cityId = ?";
        
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, cityId);
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
