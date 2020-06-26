/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Model.City;
import java.sql.SQLException;
import java.util.Optional;
import javafx.collections.ObservableList;

/**
 *
 * @author micha
 */
public interface CityInterface {

    //Create City
    int createCity(City city) throws SQLException;;

    //Delete City
    void deleteCity(int cityId);

    //Read all Cities
    ObservableList<City> getAllCities() throws SQLException;

    //Read City
    Optional<City> getCity(int cityId) throws SQLException;

    Optional<City> getCity(String cityParam) throws SQLException;

    //Update City
    void updateCity(City city);
    
}
