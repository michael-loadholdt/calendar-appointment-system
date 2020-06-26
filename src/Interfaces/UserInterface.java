/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Model.User;
import java.sql.SQLException;
import java.util.Optional;
import javafx.collections.ObservableList;

/**
 *
 * @author micha
 */
public interface UserInterface {

    //Create User
    void createUser(User user);

    //Delete User
    void deleteUser(int userId)throws SQLException;

    //Read all Users
    ObservableList<User> getAllUsers() throws ClassNotFoundException, SQLException;

    //Read User
    Optional<User> getUser(int userId) throws SQLException;

    //Update User
    void updateUser(User user) throws SQLException;
    
}
