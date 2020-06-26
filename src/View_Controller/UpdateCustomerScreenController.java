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
import java.util.Optional;
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
public class UpdateCustomerScreenController implements Initializable {
    
    Stage stage;
    Parent scene;
    CustomerInterface customerInterface = new CustomerImpl();
    AddressInterface addressInterface = new AddressImpl();
    CityInterface cityInterface = new CityImpl();
    CountryInterface countryInterface = new CountryImpl();
    
    @FXML
    private TextField customerIdTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField address2TextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField postalCodeTextField;

    @FXML
    private TextField phoneTextField;
    
    @FXML
    private TextField countryTextField;

    @FXML
    private RadioButton activeRadioButton;

    @FXML
    private ToggleGroup activeToggleGroup;

    @FXML
    private RadioButton inactiveRadioButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button updateCustomerButton;

    @FXML
    void updateCustomer(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        
        Optional<Country> countryIdGetter = countryInterface.getCountry(countryTextField.getText());
        Country country = new Country();
        int countryId = 0;
        
        Optional<City> cityIdGetter = cityInterface.getCity(cityTextField.getText());
        City city = new City();
        int cityId = 0;
        
        Optional<Address> addressIdGetter = addressInterface.getAddress(addressTextField.getText());
        Address address = new Address();
        int addressId = 0;
        
        int customerUpdateable = 0;
        Customer customer = new Customer();
        customer.setCustomerId(Integer.parseInt(customerIdTextField.getText()));
        if(nameTextField.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Customer name is required.");
            alert.showAndWait();
        }
        else{
            customer.setCustomerName(nameTextField.getText());
            customerUpdateable++;
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


        if(addressTextField.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Address is required.");
            alert.showAndWait();
        }
        else{
            address.setAddress(addressTextField.getText());
            customerUpdateable++;
        }
        if(address2TextField.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Address2 is required.");
            alert.showAndWait();
        }
        else{
            address.setAddress2(address2TextField.getText());
            customerUpdateable++;
        }
        if(phoneTextField.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Phone is required."); 
            alert.showAndWait();
        }
        else{
            address.setPhone(phoneTextField.getText());
            customerUpdateable++;
        }
        if(postalCodeTextField.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Postal Code is required.");
            alert.showAndWait();
        }
        else{
            address.setPostalCode(postalCodeTextField.getText());
            customerUpdateable++;
        }
        address.setCreateDate(LocalDateTime.now());
        address.setCreatedBy(MainScreenController.getCurrentUser());
        address.setLastUpdateBy(MainScreenController.getCurrentUser());


        if(cityTextField.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("City is required."); 
            alert.showAndWait();
        }
        else{
            city.setCity(cityTextField.getText());
            customerUpdateable++;
        }
        city.setCreateDate(LocalDateTime.now());
        city.setCreatedBy(MainScreenController.getCurrentUser());
        city.setLastUpdateBy(MainScreenController.getCurrentUser());
  

        if(countryTextField.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Country is required."); 
            alert.showAndWait();
        }
        else{
            country.setCountry(countryTextField.getText());
            customerUpdateable++;
        }
        country.setCreateDate(LocalDateTime.now());
        country.setCreatedBy(MainScreenController.getCurrentUser());
        country.setLastUpdateBy(MainScreenController.getCurrentUser());
        if(customerUpdateable == 7){
        if(countryIdGetter.isPresent() && cityIdGetter.isPresent() && addressIdGetter.isPresent()){
            countryId = countryIdGetter.get().getCountryId();
            country.setCountryId(countryId);
            city.setCountryId(countryId);
            cityId = cityIdGetter.get().getCityId();
            city.setCityId(cityId);
            address.setCityId(cityId);
            addressId = addressIdGetter.get().getAddressId();
            address.setAddressId(addressId);
            customer.setAddressId(addressId);
            
            countryInterface.updateCountry(country);
            cityInterface.updateCity(city);
            addressInterface.updateAddress(address);
            customerInterface.updateCustomer(customer);
        }
        else if((!countryIdGetter.isPresent()) && (cityIdGetter.isPresent()) && (addressIdGetter.isPresent())){
            cityId = cityIdGetter.get().getCityId();
            city.setCityId(cityId);
            address.setCityId(cityId);
            addressId = addressIdGetter.get().getAddressId();
            address.setAddressId(addressId);
            customer.setAddressId(addressId);
            
            countryId = countryInterface.createCountry(country);
            city.setCountryId(countryId);
            cityInterface.updateCity(city);
            addressInterface.updateAddress(address);
            customerInterface.updateCustomer(customer);
            
        }
        else if((countryIdGetter.isPresent()) && (!cityIdGetter.isPresent()) && (addressIdGetter.isPresent())){
            countryId = countryIdGetter.get().getCountryId();
            country.setCountryId(countryId);
            addressId = addressIdGetter.get().getAddressId();
            address.setAddressId(addressId);
            customer.setAddressId(addressId);
            
            countryInterface.updateCountry(country);
            city.setCountryId(countryId);
            cityId = cityInterface.createCity(city);
            address.setCityId(cityId);
            addressInterface.updateAddress(address);
            customerInterface.updateCustomer(customer);
        }
        else if((countryIdGetter.isPresent()) && (cityIdGetter.isPresent()) && (!addressIdGetter.isPresent())){
            countryId = countryIdGetter.get().getCountryId();
            country.setCountryId(countryId);
            city.setCountryId(countryId);
            cityId = cityIdGetter.get().getCityId();
            city.setCityId(cityId);
            
            countryInterface.updateCountry(country);
            cityInterface.updateCity(city);
            address.setCityId(cityId);
            addressId = addressInterface.createAddress(address);
            customer.setAddressId(addressId);
            customerInterface.updateCustomer(customer);
        }
        else if((!countryIdGetter.isPresent()) && (!cityIdGetter.isPresent()) && (addressIdGetter.isPresent())){
            addressId = addressIdGetter.get().getAddressId();
            address.setAddressId(addressId);

            countryId = countryInterface.createCountry(country);
            city.setCountryId(countryId);
            cityId = cityInterface.createCity(city);
            address.setCityId(cityId);
            addressInterface.updateAddress(address);
            customer.setAddressId(addressId);
            customerInterface.updateCustomer(customer);
        }
        else if((countryIdGetter.isPresent()) && (!cityIdGetter.isPresent()) && (!addressIdGetter.isPresent())){
            countryId = countryIdGetter.get().getCountryId();
            country.setCountryId(countryId);

            countryInterface.updateCountry(country);
            city.setCountryId(countryId);
            cityId = cityInterface.createCity(city);
            address.setCityId(cityId);
            addressId = addressInterface.createAddress(address);
            customer.setAddressId(addressId);
            customerInterface.updateCustomer(customer);
            
        }
        else if((!countryIdGetter.isPresent()) && (cityIdGetter.isPresent()) && (!addressIdGetter.isPresent())){
            cityId = cityIdGetter.get().getCityId();
            city.setCityId(cityId);

            countryId = countryInterface.createCountry(country);
            city.setCountryId(countryId);
            cityInterface.updateCity(city);
            address.setCityId(cityId);
            addressId = addressInterface.createAddress(address);
            customer.setAddressId(addressId);
            customerInterface.updateCustomer(customer);
        }
        else{
            countryId = countryInterface.createCountry(country);
            city.setCountryId(countryId);
            cityId = cityInterface.createCity(city);
            address.setCityId(cityId);
            addressId = addressInterface.createAddress(address);
            customer.setAddressId(addressId);
            customerInterface.updateCustomer(customer);
        }

        
        
        
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
    
    
    void sendCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        
        customerIdTextField.setText(String.valueOf(customer.getCustomerId()));
        nameTextField.setText(String.valueOf(customer.getCustomerName()));
        if(customer.getActive() == 1){
            activeRadioButton.setSelected(true);
        }
        if(customer.getActive() == 0){
            inactiveRadioButton.setSelected(true);
        }
        
        int addressId = customer.getAddressId();
        Optional<Address> address = addressInterface.getAddress(addressId);
        addressTextField.setText(String.valueOf(address.get().getAddress()));
        if(address.get().getAddress2() == null){
            address2TextField.setText("N/A");
        }
        else{
        address2TextField.setText(String.valueOf(address.get().getAddress2()));
        }
        postalCodeTextField.setText(String.valueOf(address.get().getPostalCode()));
        phoneTextField.setText(String.valueOf(address.get().getPhone()));
        
        int cityId = address.get().getCityId();
        Optional<City> city = cityInterface.getCity(cityId);
        cityTextField.setText(String.valueOf(city.get().getCity()));
        
        int countryId = city.get().getCountryId();
        Optional<Country> country = countryInterface.getCountry(countryId);
        countryTextField.setText(String.valueOf(country.get().getCountry()));
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    

    
}
