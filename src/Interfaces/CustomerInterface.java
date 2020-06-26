/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Model.Customer;
import java.sql.SQLException;
import java.util.Optional;
import javafx.collections.ObservableList;

/**
 *
 * @author micha
 */
public interface CustomerInterface {

    //Create Customer
    void createCustomer(Customer customer);

    //Delete Customer
    void deleteCustomer(int customerId);

    //Read All Customers
    ObservableList<Customer> getAllCustomers() throws SQLException;

    //Read Customer
    Optional<Customer> getCustomer(String customerNameParam) throws SQLException;
    Optional<Customer> getCustomer(int customerIdParam) throws SQLException;

    //Update Customer
    void updateCustomer(Customer customer);
    
}
