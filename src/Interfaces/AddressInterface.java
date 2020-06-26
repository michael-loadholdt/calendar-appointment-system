/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Model.Address;
import java.sql.SQLException;
import java.util.Optional;
import javafx.collections.ObservableList;

/**
 *
 * @author micha
 */
public interface AddressInterface {

    //Create Address
    int createAddress(Address address)throws SQLException;

    //Delete Address
    void deleteAddress(int addressId);

    //Read Address
    Optional<Address> getAddress(int addressParam) throws SQLException;

    Optional<Address> getAddress(String addressParam) throws SQLException;

    //Read all Addresses
    ObservableList<Address> getAllAddresses() throws SQLException;

    //Update Address
    void updateAddress(Address address);
    
}
