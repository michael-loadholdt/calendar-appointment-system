/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOImpl;

import Database.DatabaseConnection;
import Model.Customer;
import Utilities.DateTimeUtility;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Interfaces.CustomerInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author micha
 */
public class CustomerImpl implements CustomerInterface {

    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    
    //Create Customer
    @Override
    public void createCustomer(Customer customer){
        
        Connection connection = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sqlStatement = "INSERT INTO customer(customerName, addressId, active, createDate, createdBy, lastUpdateBy)"
                             +"VALUES (?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, customer.getCustomerName());
            preparedStatement.setInt(2, customer.getAddressId());
            preparedStatement.setInt(3, customer.getActive());
            LocalDateTime createDate = customer.getCreateDate();
            Timestamp timestamp = DateTimeUtility.localDateTimeToUTC(createDate);
            preparedStatement.setTimestamp(4, timestamp);
            preparedStatement.setString(5, customer.getCreatedBy());
            preparedStatement.setString(6, customer.getLastUpdateBy());
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            
        }
        finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(CityImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //Read Customer
    @Override
    public Optional<Customer> getCustomer(String customerNameParam) throws SQLException{
        
        Customer customer = null;
        Connection connection = null;
        String sqlStatement = "SELECT * FROM customer WHERE customerName = ?";
        ResultSet result = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, customerNameParam);
            result = preparedStatement.executeQuery();
            
        while(result.next()){
            
            int customerId = result.getInt("customerId");
            String customerName = result.getString("customerName");
            int addressId = result.getInt("addressId");
            int active = result.getInt("active");
            Timestamp createDate = result.getTimestamp("createDate");
            String createdBy = result.getString("createdBy");
            Timestamp lastUpdate = result.getTimestamp("lastUpdate");
            String lastUpdateBy = result.getString("lastUpdateBy");
            LocalDateTime convertedCreateDate = DateTimeUtility.UTCToLocalDateTime(createDate);
            LocalDateTime convertedLastUpdate =DateTimeUtility.UTCToLocalDateTime(lastUpdate);
            
            customer = new Customer(customerId, customerName, addressId, active, convertedCreateDate, createdBy, convertedLastUpdate, lastUpdateBy);
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
        return Optional.ofNullable(customer);
    }
    
    @Override
    public Optional<Customer> getCustomer(int customerIdParam) throws SQLException{
        
        Customer customer = null;
        Connection connection = null;
        String sqlStatement = "SELECT * FROM customer WHERE customerId = ?";
        ResultSet result = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, customerIdParam);
            result = preparedStatement.executeQuery();
            
        while(result.next()){
            
            int customerId = result.getInt("customerId");
            String customerName = result.getString("customerName");
            int addressId = result.getInt("addressId");
            int active = result.getInt("active");
            Timestamp createDate = result.getTimestamp("createDate");
            String createdBy = result.getString("createdBy");
            Timestamp lastUpdate = result.getTimestamp("lastUpdate");
            String lastUpdateBy = result.getString("lastUpdateBy");
            LocalDateTime convertedCreateDate = DateTimeUtility.UTCToLocalDateTime(createDate);
            LocalDateTime convertedLastUpdate =DateTimeUtility.UTCToLocalDateTime(lastUpdate);
            
            customer = new Customer(customerId, customerName, addressId, active, convertedCreateDate, createdBy, convertedLastUpdate, lastUpdateBy);
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
        return Optional.ofNullable(customer);
    }
    
    //Read All Customers
    @Override
    public ObservableList<Customer> getAllCustomers() throws SQLException{
        Customer customer = null;
        String sqlStatement = "SELECT * FROM customer";
        Connection connection = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            ResultSet result = preparedStatement.executeQuery();
            
        while(result.next()){
            
            int customerId = result.getInt("customerId");
            String customerName = result.getString("customerName");
            int addressId = result.getInt("addressId");
            int active = result.getInt("active");
            Timestamp createDate = result.getTimestamp("createDate");
            String createdBy = result.getString("createdBy");
            Timestamp lastUpdate = result.getTimestamp("lastUpdate");
            String lastUpdateBy = result.getString("lastUpdateBy");
            LocalDateTime convertedCreateDate = DateTimeUtility.UTCToLocalDateTime(createDate);
            LocalDateTime convertedLastUpdate =DateTimeUtility.UTCToLocalDateTime(lastUpdate);
            
            customer = new Customer(customerId, customerName, addressId, active, convertedCreateDate, createdBy, convertedLastUpdate, lastUpdateBy);
            allCustomers.add(customer);
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
        return allCustomers;
    }
    
    //Update Customer
    @Override
    public void updateCustomer(Customer customer){
   
        Connection connection = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sqlStatement = "UPDATE customer SET customerName = ?,"+
                                                   "addressId = ?," +
                                                   "active = ?," +
                                                   "lastUpdateBy = ?"+
                                                   "WHERE customerId = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, customer.getCustomerName());
            preparedStatement.setInt(2, customer.getAddressId());
            preparedStatement.setInt(3, customer.getActive());
            preparedStatement.setString(4, customer.getLastUpdateBy());
            preparedStatement.setInt(5, customer.getCustomerId());
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
    //Delete Customer
    @Override
    public void deleteCustomer(int customerId){
        Connection connection = null;
            
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException e) {
            System.out.println(e.getMessage());
            
        }
        try{
        String sqlStatement = "DELETE * FROM customer WHERE customerId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setInt(1, customerId);
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