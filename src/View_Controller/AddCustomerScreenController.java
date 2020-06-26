/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import DAOImpl.AddressImpl;
import DAOImpl.CityImpl;
import DAOImpl.CountryImpl;
import DAOImpl.CustomerImpl;
import Interfaces.AddressInterface;
import Interfaces.CityInterface;
import Interfaces.CountryInterface;
import Interfaces.CustomerInterface;
import Model.Address;
import Model.City;
import Model.Country;
import Model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author micha
 */
public class AddCustomerScreenController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    CustomerInterface customerInterface = new CustomerImpl();
    AddressInterface addressInterface = new AddressImpl();
    CityInterface cityInterface = new CityImpl();
    CountryInterface countryInterface = new CountryImpl();
    
    
    @FXML
    private TextField nameTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField address2TextField;

    @FXML
    private TextField cityTextField;
    
    @FXML
    private TextField countryTextField;

    @FXML
    private TextField postalCodeTextField;


    @FXML
    private TextField phoneTextField;
    
    @FXML
    private RadioButton activeRadioButton;

    @FXML
    private ToggleGroup activeToggleGroup;

    @FXML
    private RadioButton inactiveRadioButton;


    @FXML
    private Button cancelButton;

    @FXML
    private Button addCustomerButton;

    @FXML
    void addCustomer(ActionEvent event) throws IOException, SQLException{
        
        boolean customerCreateable = false;
        Customer customer = new Customer();
        if(nameTextField.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Customer name is required.");
            alert.showAndWait();
        }
        else{
            customer.setCustomerName(nameTextField.getText());
        }
        int active = 0;
        if(activeRadioButton.isSelected()){
          active = 1;  
        }
        if(inactiveRadioButton.isSelected()){
           active = 0;
        }
        customer.setActive(active);
        customer.setCreateDate(LocalDateTime.now());
        customer.setCreatedBy(MainScreenController.getCurrentUser());
        customer.setLastUpdateBy(MainScreenController.getCurrentUser());
        
        
        
        Address address = new Address();
        if(addressTextField.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Address is required.");
            alert.showAndWait();
        }
        else{
            address.setAddress(addressTextField.getText());
        }
        if(address2TextField.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Address2 is required.");
            alert.showAndWait();
        }
        else{
            address.setAddress2(address2TextField.getText());
        }
        if(phoneTextField.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Phone is required."); 
            alert.showAndWait();
        }
        else{
            address.setPhone(phoneTextField.getText());
        }
        if(postalCodeTextField.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Postal Code is required.");
            alert.showAndWait();
        }
        else{
            address.setPostalCode(postalCodeTextField.getText());
        }
        address.setCreateDate(LocalDateTime.now());
        address.setCreatedBy(MainScreenController.getCurrentUser());
        address.setLastUpdateBy(MainScreenController.getCurrentUser());
        
        City city = new City();
        if(cityTextField.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("City is required."); 
            alert.showAndWait();
        }
        else{
            city.setCity(cityTextField.getText());
        }
        city.setCreateDate(LocalDateTime.now());
        city.setCreatedBy(MainScreenController.getCurrentUser());
        city.setLastUpdateBy(MainScreenController.getCurrentUser());
        
        Country country = new Country();
        if(countryTextField.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Country is required."); 
            alert.showAndWait();
        }
        else{
            country.setCountry(countryTextField.getText());
            customerCreateable = true;
        }
        country.setCreateDate(LocalDateTime.now());
        country.setCreatedBy(MainScreenController.getCurrentUser());
        country.setLastUpdateBy(MainScreenController.getCurrentUser());
        
        if(customerCreateable){
        int countryId = countryInterface.createCountry(country);
        city.setCountryId(countryId);
        int cityId = cityInterface.createCity(city);
        address.setCityId(cityId);
        int addressId = addressInterface.createAddress(address);
        customer.setAddressId(addressId);
        customerInterface.createCustomer(customer);
        
   
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("CustomerManagementScreen.fxml"));
        stage.setTitle("Customer Management Screen");
        stage.setScene(new Scene(scene));
        stage.show();
        }
        
    }

    @FXML
    void returnToCustomerManagementScreen(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("CustomerManagementScreen.fxml"));
        stage.setTitle("Customer Management Screen");
        stage.setScene(new Scene(scene));
        stage.show();

    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
 
    }
}
