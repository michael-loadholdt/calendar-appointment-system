/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOImpl;

import Database.DatabaseConnection;
import Model.User;
import Utilities.DateTimeUtility;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Interfaces.UserInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author micha
 */
public class UserImpl implements UserInterface {
    
    private ObservableList<User> allUsers = FXCollections.observableArrayList();
    
    //Create User
    @Override
    public void createUser(User user){
   
        Connection connection = null;
            
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException e) {
            System.out.println(e.getMessage());
            
        }

            String sqlStatement = "INSERT INTO user(userName, password, active, createDate, createdBy, lastUpdateBy) VALUES(?,?,?,?,?,?)";
            
            try {    
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getActive());
            
            LocalDateTime createDate = user.getCreateDate();
            Timestamp timestamp = DateTimeUtility.localDateTimeToUTC(createDate);
            preparedStatement.setTimestamp(4,timestamp);
            
            preparedStatement.setString(5, user.getCreatedBy());
            preparedStatement.setString(6, user.getLastUpdateBy());
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    //Read User
    @Override
    public Optional<User> getUser(int userId) throws SQLException{ 
        
        Connection connection = null;
            
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException e) {
            System.out.println(e.getMessage());
            
        }

        String sqlStatement = "SELECT * FROM user WHERE userId = ?";
    
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setInt(1, userId);
        ResultSet result = preparedStatement.executeQuery();
        User user = null;
        
        while(result.next()){
            int userIdResult = result.getInt("userId");
            String username = result.getString("userName");
            String password = result.getString("password");
            int active = result.getInt("active");
            Timestamp createDate = result.getTimestamp("createDate");
            String createdBy = result.getString("createdBy");
            Timestamp lastUpdate = result.getTimestamp("lastUpdate");
            String lastUpdateBy = result.getString("lastUpdateBy");
            LocalDateTime convertedCreateDate = DateTimeUtility.UTCToLocalDateTime(createDate);
            LocalDateTime convertedLastUpdate =DateTimeUtility.UTCToLocalDateTime(lastUpdate);
            
            user = new User(userIdResult, username, password, active, convertedCreateDate, createdBy, convertedLastUpdate, lastUpdateBy);
        }
        return Optional.ofNullable(user);
        
    }
    
    //Read all Users
    @Override
    public ObservableList<User> getAllUsers() throws ClassNotFoundException, SQLException{
        
        User user = null;
        
        String sqlStatement = "SELECT * FROM user";
         
        Connection connection = null;
            
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException e) {
            System.out.println(e.getMessage());
            
        }

        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();
        
        while(result.next()){
            int userId = result.getInt("userId");
            String username = result.getString("userName");
            String password = result.getString("password");
            int active = result.getInt("active");
            Timestamp createDate = result.getTimestamp("createDate");
            String createdBy = result.getString("createdBy");
            Timestamp lastUpdate = result.getTimestamp("lastUpdate");
            String lastUpdateBy = result.getString("lastUpdateBy");
            LocalDateTime convertedCreateDate = DateTimeUtility.UTCToLocalDateTime(createDate);
            LocalDateTime convertedLastUpdate =DateTimeUtility.UTCToLocalDateTime(lastUpdate);
        
            user = new User(userId, username, password, active, convertedCreateDate, createdBy, convertedLastUpdate, lastUpdateBy);
            allUsers.add(user);
        }
       
        return allUsers;
    }
    
    //Update User
    
    @Override
    public void updateUser(User user) throws SQLException{
        
        
        Connection connection = null;
            
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException e) {
            System.out.println(e.getMessage());
            
        }

        String sqlStatement = "UPDATE user SET userName = ?, password = ?, active = ?";
    
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setString(1, user.getUserName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setInt(3, user.getActive());
        preparedStatement.executeUpdate();
    }
        
        
    //Delete User
    @Override
    public void deleteUser(int userId) throws SQLException{

        Connection connection = null;
            
        try {
            connection = DatabaseConnection.makeConnection();
        } catch (ClassNotFoundException| SQLException e) {
            System.out.println(e.getMessage());
            
        }
        String sqlStatement = "DELETE * FROM user WHERE userId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setInt(1, userId);
        preparedStatement.executeUpdate();
    }
    
}
