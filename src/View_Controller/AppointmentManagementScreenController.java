/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;


import DAOImpl.AppointmentImpl;
import DAOImpl.CustomerImpl;
import DAOImpl.UserImpl;
import Interfaces.AppointmentInterface;
import Interfaces.CustomerInterface;
import Interfaces.UserInterface;
import Model.Appointment;
import Model.Customer;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author micha
 */
public class AppointmentManagementScreenController implements Initializable {
    
    Stage stage;
    Parent scene;
    ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    ObservableList<User> allUsers = FXCollections.observableArrayList();
    ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    AppointmentInterface appointmentInterface = new AppointmentImpl();
    UserInterface userInterface = new UserImpl();
    CustomerInterface customerInterface = new CustomerImpl();
    
    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;

    @FXML
    private TableColumn<Appointment, Integer> userCol;

    @FXML
    private TableColumn<Appointment, Integer> customerCol;

    @FXML
    private TableColumn<Appointment, String> appointmentTypeCol;

    @FXML
    private TableColumn<Appointment, String> appointmentTimeCol;

    @FXML
    private RadioButton allRadioButton;

    @FXML
    private ToggleGroup calendarViewToggleGroup;

    @FXML
    private RadioButton weekRadioButton;

    @FXML
    private RadioButton monthRadioButton;
    
    @FXML
    private Button updateAppointmentButton;
    
    @FXML
    private Button deleteAppointmentButton;

    @FXML
    void addAppoinmentScreen(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/AddAppointmentScreen.fxml"));
        stage.setTitle("Add Appointment Screen");
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void deleteAppointment(ActionEvent event) {
        
        appointmentInterface.deleteAppointment(appointmentTableView.getSelectionModel().getSelectedItem());
        appointmentTableView.getItems().remove(appointmentTableView.getSelectionModel().getSelectedItem());
    }


    @FXML
    void updateAppointmentScreen(ActionEvent event) throws IOException, SQLException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("UpdateAppointmentScreen.fxml"));
        loader.load();
        
        UpdateAppointmentScreenController updateAppointmentScreenController = loader.getController();
        updateAppointmentScreenController.sendAppointment(appointmentTableView.getSelectionModel().getSelectedItem());
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
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
    public void appointmentSelected(){
                
        updateAppointmentButton.setDisable(false);
        deleteAppointmentButton.setDisable(false);
        
    }
    
    @FXML
    public void calendarViewChanged(){
        
        if(weekRadioButton.isSelected()){
            ObservableList<Appointment> appointmentWeekView = FXCollections.observableArrayList();

            allAppointments.stream().filter((appointment) -> ((appointment.getStart().isBefore(LocalDateTime.now().plusDays(7))) && appointment.getStart().isAfter(LocalDateTime.now()))).forEachOrdered((appointment) -> {
                appointmentWeekView.add(appointment);
                        });
            
            appointmentTableView.setItems(appointmentWeekView);
            appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            userCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
            customerCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            appointmentTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        }
        else if(monthRadioButton.isSelected()){
            ObservableList<Appointment> appointmentMonthView = FXCollections.observableArrayList();

            allAppointments.stream().filter((appointment) -> ((appointment.getStart().isBefore(LocalDateTime.now().plusMonths(1))) && appointment.getStart().isAfter(LocalDateTime.now()))).forEachOrdered((appointment) -> {
                appointmentMonthView.add(appointment);
                        });

            appointmentTableView.setItems(appointmentMonthView);
            appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            userCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
            customerCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            appointmentTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        }
        else{
            appointmentTableView.setItems(allAppointments);
            appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            userCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
            customerCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            appointmentTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));            
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        updateAppointmentButton.setDisable(true);
        deleteAppointmentButton.setDisable(true);
        
        try{
           allAppointments = appointmentInterface.getAllAppointments();
           allUsers = userInterface.getAllUsers();
           allCustomers = customerInterface.getAllCustomers();
        }
        catch(Exception e){
            
        }
        
        appointmentTableView.setItems(allAppointments);
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));

    }    
    
}
