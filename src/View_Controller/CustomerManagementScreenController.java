/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import DAOImpl.AddressImpl;
import DAOImpl.AppointmentImpl;
import DAOImpl.CustomerImpl;
import Interfaces.AddressInterface;
import Interfaces.AppointmentInterface;
import Interfaces.CustomerInterface;
import Model.Appointment;
import Model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author micha
 */
public class CustomerManagementScreenController implements Initializable {
    
    Stage stage;
    Parent scene;
    ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    CustomerInterface customerInterface = new CustomerImpl();
    AddressInterface addressInterface = new AddressImpl();
    AppointmentInterface appointmentInterface = new AppointmentImpl();
    
    @FXML
    private TableView<Customer> custTableView;
    
    @FXML
    private TableColumn<Customer, Integer> custIDCol;

    @FXML
    private TableColumn<Customer, String> custNameCol;

    @FXML
    private TableColumn<Customer, Integer> custActiveCol;
    
    @FXML
    private Button updateCustomerButton;
    
    @FXML
    private Button deleteCustomerButton;


    @FXML
    void addCustomerButton(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddCustomerScreen.fxml"));
        stage.setTitle("Customer Details Screen");
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void deleteCustomerButton(ActionEvent event) throws SQLException {
        Customer customer = custTableView.getSelectionModel().getSelectedItem();
        int customerId = customer.getCustomerId();
        List<Appointment> allAppointments = appointmentInterface.getAllAppointments();
        allAppointments.stream().filter((appointment) -> (appointment.getCustomerId() == customerId)).forEachOrdered((appointment) -> {
            appointmentInterface.deleteAppointment(appointment);
        });
        allCustomers.remove(customer);
        customerInterface.deleteCustomer(customerId);
        
        
        
        
    }

    @FXML
    void returnToMainScreen(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(scene));
        stage.show();

    }
    
    @FXML
    void updateCustomerButton(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View_Controller/UpdateCustomerScreen.fxml"));
        loader.load();
        
        UpdateCustomerScreenController updateCustomerScreenController = loader.getController();
        updateCustomerScreenController.sendCustomer(custTableView.getSelectionModel().getSelectedItem());
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }
    
    @FXML
    public void customerSelected(){
        
        updateCustomerButton.setDisable(false);
        deleteCustomerButton.setDisable(false);
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        updateCustomerButton.setDisable(true);
        deleteCustomerButton.setDisable(true);

        try {
            allCustomers = customerInterface.getAllCustomers();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerManagementScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        custTableView.setItems(allCustomers);
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        custActiveCol.setCellValueFactory(new PropertyValueFactory<>("active"));
        
    }    
    
}
