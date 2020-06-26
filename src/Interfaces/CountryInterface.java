/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Model.Country;
import java.sql.SQLException;
import java.util.Optional;
import javafx.collections.ObservableList;

/**
 *
 * @author micha
 */
public interface CountryInterface {

    //Create Country
    int createCountry(Country country) throws SQLException;

    //Delete Country
    void deleteCountry(int countryId);

    //Read all Countries
    ObservableList<Country> getAllCountries() throws SQLException;

    //Read Country
    Optional<Country> getCountry(int countryId) throws SQLException;

    Optional<Country> getCountry(String countryParam) throws SQLException;

    //Update Country
    void updateCountry(Country country);
    
}
