/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOImpl;

import Database.DatabaseConnection;
import Interfaces.AddressInterface;
import Model.Address;
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
public class AddressImpl implements AddressInterface {
    
    private ObservableList<Address> allAddresses = FXCollections.observableArrayList();
    
    //Create Address
    @Override
    public int createAddress(Address address) throws SQLException{
        
        Connection connection = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sqlStatement = "INSERT INTO address(address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdateBy)"+
                              "VALUES(?,?,?,?,?,?,?,?)";
        Optional<Address> compareAddress = getAddress(address.getAddress());
        int addressId = 0;
        if(compareAddress.isPresent()){
            if(address.getAddress().equals(compareAddress.get().getAddress())){
                addressId = compareAddress.get().getAddressId();

            }
        }
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, address.getAddress());
            preparedStatement.setString(2, address.getAddress2());
            preparedStatement.setInt(3, address.getCityId());
            preparedStatement.setString(4, address.getPostalCode());
            preparedStatement.setString(5, address.getPhone());
            LocalDateTime createDate = address.getCreateDate();
            Timestamp timestamp = DateTimeUtility.localDateTimeToUTC(createDate);
            preparedStatement.setTimestamp(6, timestamp);
            preparedStatement.setString(7, address.getCreatedBy());
            preparedStatement.setString(8, address.getLastUpdateBy());
            preparedStatement.executeUpdate();
            
            ResultSet result = preparedStatement.getGeneratedKeys();
            if(result.next()){
                
                addressId = result.getInt(1);
               
            }
            else{
                System.out.println("Error getting key");
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
                finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(CityImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return addressId;                      
    }
    
    //Read Address
    @Override
    public Optional<Address> getAddress(int addressParam) throws SQLException{
        
        Connection connection = null;
        Address address = new Address();
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sqlStatement = "SELECT * FROM address WHERE addressId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setInt(1, addressParam);
        ResultSet result = preparedStatement.executeQuery();
        
        while(result.next()){
            int addressId = result.getInt("addressId");
            String addressR = result.getString("address");
            String address2 = null;
            if(result.getString("address2").isEmpty()){
                address2 = "N/A";
            }
            address2 = result.getString("address2");
            int cityId = result.getInt("cityId");
            String postalCode = result.getString("postalCode");
            String phone = result.getString("phone");
            Timestamp createDate = result.getTimestamp("createDate");
            String createdBy = result.getString("createdBy");
            Timestamp lastUpdate = result.getTimestamp("lastUpdate");
            String lastUpdateBy = result.getString("lastUpdateBy");
            LocalDateTime convertedCreateDate = DateTimeUtility.UTCToLocalDateTime(createDate);
            LocalDateTime convertedLastUpdate =DateTimeUtility.UTCToLocalDateTime(lastUpdate);
            
            address = new Address(addressId, addressR, address2, cityId, postalCode, phone, convertedCreateDate, createdBy, convertedLastUpdate, lastUpdateBy);
        }
        connection.close();
        return Optional.ofNullable(address);
    }
    
    @Override
    public Optional<Address> getAddress(String addressParam) throws SQLException{
        
        Connection connection = null;
        Address address = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sqlStatement = "SELECT * FROM address WHERE address = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setString(1, addressParam);
        ResultSet result = preparedStatement.executeQuery();
        
        while(result.next()){
            int addressId = result.getInt("addressId");
            String addressR = result.getString("address");
            String address2 = null;
            if(result.getString("address2").isEmpty()){
                address2 = "N/A";
            }
            address2 = result.getString("address2");
            int cityId = result.getInt("cityId");
            String postalCode = result.getString("postalCode");
            String phone = result.getString("phone");
            Timestamp createDate = result.getTimestamp("createDate");
            String createdBy = result.getString("createdBy");
            Timestamp lastUpdate = result.getTimestamp("lastUpdate");
            String lastUpdateBy = result.getString("lastUpdateBy");
            LocalDateTime convertedCreateDate = DateTimeUtility.UTCToLocalDateTime(createDate);
            LocalDateTime convertedLastUpdate =DateTimeUtility.UTCToLocalDateTime(lastUpdate);
            
            address = new Address(addressId, addressR, address2, cityId, postalCode, phone, convertedCreateDate, createdBy, convertedLastUpdate, lastUpdateBy);
        }
        connection.close();
        return Optional.ofNullable(address);
    }
    
    //Read all Addresses
    @Override
    public ObservableList<Address> getAllAddresses() throws SQLException{
        
        Connection connection = null;
        Address address = null;
        String sqlStatement = "SELECT * FROM address";
        ResultSet result = null;
        
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        result = preparedStatement.executeQuery();
        
        while(result.next()){
            int addressId = result.getInt("addressId");
            String addressR = result.getString("address");
            String address2 = result.getString("address2");
            int cityId = result.getInt("cityId");
            String postalCode = result.getString("postalCode");
            String phone = result.getString("phone");
            Timestamp createDate = result.getTimestamp("createDate");
            String createdBy = result.getString("createdBy");
            Timestamp lastUpdate = result.getTimestamp("lastUpdate");
            String lastUpdateBy = result.getString("lastUpdateBy");
            LocalDateTime convertedCreateDate = DateTimeUtility.UTCToLocalDateTime(createDate);
            LocalDateTime convertedLastUpdate =DateTimeUtility.UTCToLocalDateTime(lastUpdate);
            
            address = new Address(addressId, addressR, address2, cityId, postalCode, phone, convertedCreateDate, createdBy, convertedLastUpdate, lastUpdateBy);
            allAddresses.add(address);
            
        }
        connection.close();
        return allAddresses;
    }
    
    //Update Address
    @Override
    public void updateAddress(Address address){
        
        Connection connection = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sqlStatement = "UPDATE address SET address = ?," 
                                                +" address2 = ?,"
                                                +" cityId = ?,"
                                                +" postalCode = ?,"
                                                +" phone = ?,"
                                                +" lastUpdateBy = ?"
                                                +" WHERE addressId = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, address.getAddress());
            preparedStatement.setString(2, address.getAddress2());
            preparedStatement.setInt(3, address.getCityId());
            preparedStatement.setString(4, address.getPostalCode());
            preparedStatement.setString(5, address.getPhone());
            preparedStatement.setString(6,address.getLastUpdateBy());
            preparedStatement.setInt(7, address.getAddressId());
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
    
    //Delete Address
    @Override
    public void deleteAddress(int addressId){
        
        Connection connection = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sqlStatement = "DELETE FROM address WHERE addressId = ?";
        try{
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setInt(1, addressId);
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
