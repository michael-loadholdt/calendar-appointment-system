/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOImpl;

import Database.DatabaseConnection;
import Interfaces.AppointmentInterface;
import Model.Appointment;
import Utilities.DateTimeUtility;
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
public class AppointmentImpl implements AppointmentInterface {
    
    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    
    //Create Appointment
    @Override
    public void createAppointment(Appointment appointment){

        Connection connection = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(AppointmentImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        String sqlStatement = "INSERT INTO appointment(customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdateBy)"
                             + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, appointment.getCustomerId());
            preparedStatement.setInt(2, appointment.getUserId());
            preparedStatement.setString(3,appointment.getTitle());
            preparedStatement.setString(4, appointment.getDescription());
            preparedStatement.setString(5, appointment.getLocation());
            preparedStatement.setString(6, appointment.getContact());
            preparedStatement.setString(7, appointment.getType());
            preparedStatement.setString(8, appointment.getUrl());
            LocalDateTime start = appointment.getStart();
            Timestamp startTimestamp = DateTimeUtility.localDateTimeToUTC(start);
            preparedStatement.setTimestamp(9, startTimestamp);
            LocalDateTime end = appointment.getEnd();
            Timestamp endTimestamp = DateTimeUtility.localDateTimeToUTC(end);
            preparedStatement.setTimestamp(10, endTimestamp);
            LocalDateTime createDate = appointment.getCreateDate();
            Timestamp createDateTimestamp = DateTimeUtility.localDateTimeToUTC(createDate);
            preparedStatement.setTimestamp(11, createDateTimestamp);
            preparedStatement.setString(12, appointment.getCreatedBy());
            preparedStatement.setString(13, appointment.getLastUpdateBy());
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
    
    //Read Appointment
    @Override
    public Optional<Appointment> getAppointment(int appointmentIdParam) throws SQLException{
        
        Connection connection = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(AppointmentImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        Appointment appointment = null;
        String sqlStatement = "SELECT * FROM appointment WHERE appointmentId = ?";
        ResultSet result = null;
        
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setInt(1, appointmentIdParam);
        result = preparedStatement.executeQuery();
        
        while(result.next()){
            int appointmentId = result.getInt("appointmentId");
            int customerId = result.getInt("customerId");
            int userId = result.getInt("userId");
            String title = result.getString("title");
            String description = result.getString("description");
            String location = result.getString("location");
            String contact = result.getString("contact");
            String type = result.getString("type");
            String url = result.getString("url");
            Timestamp start = result.getTimestamp("start");
            Timestamp end = result.getTimestamp("end");
            Timestamp createDate = result.getTimestamp("createDate");
            String createdBy = result.getString("createdBy");
            Timestamp lastUpdate = result.getTimestamp("lastUpdate");
            String lastUpdateBy = result.getString("lastUpdateBy");
            LocalDateTime convertedStart = DateTimeUtility.UTCToLocalDateTime(start);
            LocalDateTime convertedEnd = DateTimeUtility.UTCToLocalDateTime(end);
            LocalDateTime convertedCreateDate = DateTimeUtility.UTCToLocalDateTime(createDate);
            LocalDateTime convertedLastUpdate =DateTimeUtility.UTCToLocalDateTime(lastUpdate);
            
            appointment = new Appointment(appointmentId, customerId, userId, title, description, location, contact, type, url, convertedStart, convertedEnd, convertedCreateDate, createdBy, convertedLastUpdate, lastUpdateBy);
        }
        connection.close();
        return Optional.ofNullable(appointment);
    }
    
    //Read All Appointments
    @Override
    public ObservableList<Appointment> getAllAppointments() throws SQLException{
        
        Connection connection = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(AppointmentImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        Appointment appointment = null;
        String sqlStatement = "SELECT * FROM appointment";
        ResultSet result = null;
        
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        result = preparedStatement.executeQuery();
        
        while(result.next()){
            int appointmentId = result.getInt("appointmentId");
            int customerId = result.getInt("customerId");
            int userId = result.getInt("userId");
            String title = result.getString("title");
            String description = result.getString("description");
            String location = result.getString("location");
            String contact = result.getString("contact");
            String type = result.getString("type");
            String url = result.getString("url");
            Timestamp start = result.getTimestamp("start");
            Timestamp end = result.getTimestamp("end");
            Timestamp createDate = result.getTimestamp("createDate");
            String createdBy = result.getString("createdBy");
            Timestamp lastUpdate = result.getTimestamp("lastUpdate");
            String lastUpdateBy = result.getString("lastUpdateBy");
            LocalDateTime convertedStart = DateTimeUtility.UTCToLocalDateTime(start);
            LocalDateTime convertedEnd = DateTimeUtility.UTCToLocalDateTime(end);
            LocalDateTime convertedCreateDate = DateTimeUtility.UTCToLocalDateTime(createDate);
            LocalDateTime convertedLastUpdate =DateTimeUtility.UTCToLocalDateTime(lastUpdate);
            
            appointment = new Appointment(appointmentId, customerId, userId, title, description, location, contact, type, url, convertedStart, convertedEnd, convertedCreateDate, createdBy, convertedLastUpdate, lastUpdateBy);
            allAppointments.add(appointment);
        }
        connection.close();
        return allAppointments;
    }
    
    //Update Appointment
    @Override
    public void updateAppointment(Appointment appointment){
        
        Connection connection = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(AppointmentImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sqlStatement = "UPDATE appointment SET customerId = ?," +
                                                     "userId = ?," + 
                                                     "title = ?," +
                                                     "description = ?," +
                                                     "location = ?," +
                                                     "contact = ?," +
                                                     "type = ?," +
                                                     "url = ?," +
                                                     "start = ?," +
                                                     "end = ?," +
                                                     "lastUpdateBy  = ?" +
                                                     "WHERE appointmentId = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, appointment.getCustomerId());
            preparedStatement.setInt(2, appointment.getUserId());
            preparedStatement.setString(3,appointment.getTitle());
            preparedStatement.setString(4, appointment.getDescription());
            preparedStatement.setString(5, appointment.getLocation());
            preparedStatement.setString(6, appointment.getContact());
            preparedStatement.setString(7, appointment.getType());
            preparedStatement.setString(8, appointment.getUrl());
            LocalDateTime start = appointment.getStart();
            Timestamp startTimestamp = DateTimeUtility.localDateTimeToUTC(start);
            preparedStatement.setTimestamp(9, startTimestamp);
            LocalDateTime end = appointment.getEnd();
            Timestamp endTimestamp = DateTimeUtility.localDateTimeToUTC(end);
            preparedStatement.setTimestamp(10, endTimestamp);
            preparedStatement.setString(11, appointment.getLastUpdateBy());
            preparedStatement.setInt(12, appointment.getAppointmentId());
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
    
    //Delete Appointment
    @Override
    public void deleteAppointment(Appointment appointment){
        
        Connection connection = null;
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException ex) {
            Logger.getLogger(AppointmentImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sqlStatement = "DELETE FROM appointment WHERE appointmentId = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, appointment.getAppointmentId());
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
