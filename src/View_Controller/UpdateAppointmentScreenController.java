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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author micha
 */
public class UpdateAppointmentScreenController implements Initializable {
    
    Stage stage;
    Parent scene;
    CustomerInterface customerInterface = new CustomerImpl();
    UserInterface userInterface = new UserImpl();
    ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    ObservableList<User> allUsers = FXCollections.observableArrayList();
        
    @FXML
    private ComboBox<User> userComboBox;

    @FXML
    private ComboBox<Customer> customerComboBox;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField locationTextField;

    @FXML
    private TextField contactTextField;

    @FXML
    private ChoiceBox<String> typeChoiceBox;

    @FXML
    private TextField urlTextField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ChoiceBox<String> startHourChoiceBox;

    @FXML
    private ChoiceBox<String> startMinuteChoiceBox;

    @FXML
    private ChoiceBox<String> endHourChoiceBox;

    @FXML
    private ChoiceBox<String> endMinuteChoiceBox;

    @FXML
    private TextField appointmentIdTextField;

    @FXML
    void returnToAppointmentManagementScreen(ActionEvent event) throws IOException {
        
                
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AppointmentManagementScreen.fxml"));
        stage.setTitle("Appointment Management Screen");
        stage.setScene(new Scene(scene));
        stage.show();


    }

    @FXML
    void updateAppointment(ActionEvent event) throws IOException, SQLException {
        
        AppointmentInterface appointmentInterface = new AppointmentImpl();
        Appointment appointment = new Appointment();
        Boolean isAppointmentCreatable = false;
        Alert alert = null;
        LocalDateTime startLDT = null;
        LocalDateTime endLDT = null;
        
        appointment.setAppointmentId(Integer.parseInt(appointmentIdTextField.getText()));
        
        if(customerComboBox.getValue() == null){
            
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Creation Error");
            alert.setHeaderText("Appointment Creation Error");
            alert.setContentText("Please select a customer.");
            alert.showAndWait();
        }
        else{
            appointment.setCustomerId(customerComboBox.getValue().getCustomerId());
        }
        if(userComboBox.getValue() == null){
            
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Creation Error");
            alert.setHeaderText("Appointment Creation Error");
            alert.setContentText("Please select a user.");
            alert.showAndWait();
        }
        else{        
            appointment.setUserId(userComboBox.getValue().getUserId());
        }
        if(titleTextField.getText().trim().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Creation Error");
            alert.setHeaderText("Appointment Creation Error");
            alert.setContentText("Title field is required. Enter NOT NEEDED if field should be empty.");
            alert.showAndWait();
        }
        else{
            appointment.setTitle(titleTextField.getText());
        }
        if(descriptionTextField.getText().trim().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Creation Error");
            alert.setHeaderText("Appointment Creation Error");
            alert.setContentText("Description field is required. Enter NOT NEEDED if field should be empty.");
            alert.showAndWait();
        }
        else{
            appointment.setDescription(descriptionTextField.getText());
        }
        if(locationTextField.getText().trim().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Creation Error");
            alert.setHeaderText("Appointment Creation Error");
            alert.setContentText("Location field is required. Enter NOT NEEDED if field should be empty.");
            alert.showAndWait();
        }
        else{        
            appointment.setLocation(locationTextField.getText());
        }
        if(contactTextField.getText().trim().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Creation Error");
            alert.setHeaderText("Appointment Creation Error");
            alert.setContentText("Contact field is required. Enter NOT NEEDED if field should be empty.");
            alert.showAndWait();
        }
        else{        
            appointment.setContact(contactTextField.getText());
        }
        if(typeChoiceBox.getValue() == null){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Creation Error");
            alert.setHeaderText("Appointment Creation Error");
            alert.setContentText("Please select an appointment type.");
            alert.showAndWait();
        }
        else{        
            appointment.setType(typeChoiceBox.getValue());
        }
        if(urlTextField.getText().trim().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Creation Error");
            alert.setHeaderText("Appointment Creation Error");
            alert.setContentText("URL field is required. Enter NOT NEEDED if field should be empty.");
            alert.showAndWait();
        }
        else{        
            appointment.setUrl(urlTextField.getText());
        }
        if((startDatePicker.getValue() == null) || (startHourChoiceBox.getValue() == null) || (startMinuteChoiceBox.getValue() == null)){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Creation Error");
            alert.setHeaderText("Appointment Creation Error");
            alert.setContentText("Please select a start date and time for the appointment");
            alert.showAndWait();
        }
        else{
            LocalDate startDate = startDatePicker.getValue();
            String startHour = startHourChoiceBox.getValue();
            int startHourInt = 0;
            startHourInt = Integer.parseInt(startHour);
            String startMinute = startMinuteChoiceBox.getValue();
            startLDT = LocalDateTime.of(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth(), startHourInt, Integer.parseInt(startMinute));
            appointment.setStart(startLDT);
        }
        if((endDatePicker.getValue() == null) || (endHourChoiceBox.getValue() == null) || (endMinuteChoiceBox.getValue() == null)){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Creation Error");
            alert.setHeaderText("Appointment Creation Error");
            alert.setContentText("Please select a start date and time for the appointment");
            alert.showAndWait();
        }
        else{
            LocalDate endDate = endDatePicker.getValue();
            String endHour = endHourChoiceBox.getValue();
            int endHourInt = 0;
            endHourInt = Integer.parseInt(endHour);
            String endMinute = endMinuteChoiceBox.getValue();
            endLDT = LocalDateTime.of(endDate.getYear(), endDate.getMonthValue(), endDate.getDayOfMonth(), endHourInt, Integer.parseInt(endMinute));
            appointment.setEnd(endLDT);

            if(appointment.getEnd().isBefore(appointment.getStart())){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Appointment Creation Error");
                alert.setHeaderText("Appointment Creation Error");
                alert.setContentText("Appointments cannot end before they begin.");
                alert.showAndWait();
            }
            else{
                ObservableList<Appointment> allAppointments = appointmentInterface.getAllAppointments();
                List<Appointment> appointmentsByUser = new ArrayList();
                
                //Filters appointments by User
                allAppointments.stream().filter((appointmentCheck) -> (appointmentCheck.getUserId() == userComboBox.getValue().getUserId())).forEachOrdered((appointmentCheck) -> {
                appointmentsByUser.add(appointmentCheck);
                });
                boolean launchAlert = false;
                for(Appointment appointmentOverlap : appointmentsByUser){
                    if((appointmentOverlap.getStart().isBefore(endLDT)) && appointmentOverlap.getEnd().isAfter(startLDT)){
                         launchAlert = true;
                }
                                }
                if(launchAlert){
                    
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Appointment Creation Error");
                alert.setHeaderText("Appointment Creation Error");
                alert.setContentText("New Appointment overlaps an existing appointment.");
                alert.showAndWait();
                }
                else{
                isAppointmentCreatable = true;
                }
            }

        }
        
        appointment.setCreateDate(LocalDateTime.now());        
        appointment.setCreatedBy(MainScreenController.getCurrentUser());
        appointment.setLastUpdateBy(MainScreenController.getCurrentUser());
        
        
        if(isAppointmentCreatable){
        
            appointmentInterface.updateAppointment(appointment);
       
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View_Controller/AppointmentManagementScreen.fxml"));
            stage.setTitle("Main Screen");
            stage.setScene(new Scene(scene));
            stage.show();
        
        }

    }
    
    void sendAppointment(Appointment appointment) throws SQLException{
        
        appointmentIdTextField.setText(String.valueOf(appointment.getAppointmentId()));
        int userid = appointment.getUserId();
        Optional<User> user = userInterface.getUser(userid);
        userComboBox.setValue(user.get());
        int customerId = appointment.getCustomerId();
        Optional<Customer> customer = customerInterface.getCustomer(customerId);
        customerComboBox.setValue(customer.get());
        titleTextField.setText(appointment.getTitle());
        descriptionTextField.setText(appointment.getDescription());
        locationTextField.setText(appointment.getLocation());
        contactTextField.setText(appointment.getContact());
        typeChoiceBox.setValue(appointment.getType());
        urlTextField.setText(appointment.getUrl());
        LocalDateTime appointmentStart = appointment.getStart();
        LocalDate startDate = LocalDate.of(appointmentStart.getYear(), appointmentStart.getMonthValue(), appointmentStart.getDayOfMonth());
        Integer startHour = appointmentStart.getHour();
        Integer startMinute = appointmentStart.getMinute();
        startDatePicker.setValue(startDate);
        startHourChoiceBox.setValue(startHour.toString());
        if(startMinute == 0){
            startMinuteChoiceBox.setValue("00");
        }
        else{
            startMinuteChoiceBox.setValue(startMinute.toString());
        }
        LocalDateTime appointmentEnd = appointment.getEnd();
        LocalDate endDate = LocalDate.of(appointmentEnd.getYear(), appointmentEnd.getMonthValue(), appointmentEnd.getDayOfMonth());
        Integer endHour = appointmentEnd.getHour();
        Integer endMinute = appointmentEnd.getMinute();
        endDatePicker.setValue(endDate);
        endHourChoiceBox.setValue(endHour.toString());
        if(endMinute == 0){
            endMinuteChoiceBox.setValue("00");
        }
        else{
            endMinuteChoiceBox.setValue(endMinute.toString());
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        startHourChoiceBox.getItems().addAll("8","9","10","11","12","13","14","15","16","17");
        startMinuteChoiceBox.getItems().addAll("15","30","45","00");
        endHourChoiceBox.getItems().addAll("8","9","10","11","12","13","14","15","16","17");
        endMinuteChoiceBox.getItems().addAll("15","30","45","00");
        

        try{
            allCustomers = customerInterface.getAllCustomers();
            allUsers = userInterface.getAllUsers();
            
        }
        catch(Exception e){
            
        }

        customerComboBox.setItems(allCustomers);
        userComboBox.setItems(allUsers);
        typeChoiceBox.getItems().addAll("Initial Consult", "Customer Onboarding", "Quarterly Check-in", "Customer Offboarding");
    }   
       
}
