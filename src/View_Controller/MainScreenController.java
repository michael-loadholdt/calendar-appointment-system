/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import DAOImpl.AppointmentImpl;
import DAOImpl.CustomerImpl;
import Database.DatabaseConnection;
import Database.Query;
import Interfaces.AppointmentInterface;
import Interfaces.CustomerInterface;
import Model.Appointment;
import Model.Customer;
import Model.User;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author micha
 */
public class MainScreenController implements Initializable {
    
    Stage stage;
    Parent scene;

    @FXML
    void exitApplication(ActionEvent event) {
        
        System.exit(0);

    }

    @FXML
    void goToAppointmentManagmentScreen(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AppointmentManagementScreen.fxml"));
        stage.setTitle("Appointment Management");
        stage.setScene(new Scene(scene));
        stage.show();        


    }

    @FXML
    void goToCustomerManagmentScreen(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("CustomerManagementScreen.fxml"));
        stage.setTitle("Customer Management");
        stage.setScene(new Scene(scene));
        stage.show(); 
    }

    @FXML
    void goToReportsScreen(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("ReportsScreen.fxml"));
        stage.setTitle("Reports Screen");
        stage.setScene(new Scene(scene));
        stage.show(); 

    }

    @FXML
    void openLogFile(ActionEvent event) throws IOException {
        
        File file = new File("./logintracker.txt");
        if(!Desktop.isDesktopSupported()){
            System.out.println("Desktop is not supported.");
            return;
        }
        Desktop desktop = Desktop.getDesktop();
        if(file.exists()){
            desktop.open(file);
        }

    }
    
    public static String getCurrentUser(){
        
        String currentUser = null;
        
        Path logger = Paths.get("./logintracker.txt");
        try(BufferedReader reader = Files.newBufferedReader(logger, Charset.forName("UTF-8"))){
            String currentLine = null;
            String lastLine = null;
            while((currentLine = reader.readLine()) != null){
                lastLine = currentLine;
                currentUser = lastLine.substring(0, lastLine.indexOf(" "));
            }
        }
        catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }
        
    
    return currentUser;
    }
    
    public static void appointmentAlert(){
                
        AppointmentInterface appointmentInterface = new AppointmentImpl();
        CustomerInterface customerInterface = new CustomerImpl();
        List<Appointment> allAppointments = new ArrayList<>();

        try {
            allAppointments = appointmentInterface.getAllAppointments();           
        } catch (SQLException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
            try {
                DatabaseConnection.makeConnection();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }       
            
            Query.makeQuery("SELECT * FROM user WHERE userName = '" + getCurrentUser() +"'");
            
                User user = new User();
                ResultSet result = Query.getResult();
                
            try {
                while(result.next()){
                    user.setUserId(result.getInt("userId"));
                    user.setUserName(result.getString("userName"));

                }
                            } catch (SQLException ex) {
                Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        for(Appointment appointment:allAppointments){
            int userId = appointment.getUserId();


            if(user.getUserId() == userId){
                Customer customer = null;
                try {
                    customer = customerInterface.getCustomer(appointment.getCustomerId()).get();
                } catch (SQLException ex) {
                    Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
                LocalDateTime start = appointment.getStart();
                long timeDiff = ChronoUnit.MINUTES.between(LocalDateTime.now(), start);
                if((timeDiff <= 15) && (timeDiff > 0)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment Alert");
                    alert.setHeaderText("Appointment within 15 minutes.");
                    alert.setContentText(appointment.getType() + " appointment in " + timeDiff + " minutes with " + customer);
                    alert.showAndWait();
                }
            }
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
}
