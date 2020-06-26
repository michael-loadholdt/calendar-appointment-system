/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import DAOImpl.AppointmentImpl;
import DAOImpl.CountryImpl;
import DAOImpl.UserImpl;
import Interfaces.AppointmentInterface;
import Interfaces.CountryInterface;
import Interfaces.UserInterface;
import Model.Appointment;
import Model.Country;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author micha
 */
public class ReportsScreenController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    @FXML
    private Label janLbl;

    @FXML
    private Label febLbl;

    @FXML
    private Label marLbl;

    @FXML
    private Label aprLbl;

    @FXML
    private Label mayLbl;

    @FXML
    private Label junLbl;

    @FXML
    private Label julLbl;

    @FXML
    private Label augLbl;

    @FXML
    private Label sepLbl;

    @FXML
    private Label octLbl;

    @FXML
    private Label novLbl;

    @FXML
    private Label decLbl;
    
    @FXML
    private TreeTableView<Object> scheduleTreeTableView;

    @FXML
    private TreeTableColumn<?, String> consultantCol;

    @FXML
    private TreeTableColumn<?, LocalDateTime> appointmentStartCol;

    @FXML
    private TableView<Country> countryTableView;

    @FXML
    private TableColumn<Country, Integer> countryIdCol;

    @FXML
    private TableColumn<Country, String> countryCol;

    
    @FXML
    void returnToMainScreen(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(scene));
        stage.show();

    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        AppointmentInterface appointmentInterface = new AppointmentImpl();
        List<Appointment> allAppointments = new ArrayList();
        
        //Sets Items in Appointment Type Tableview
        Integer janAppt = 0;
        Integer febAppt = 0;
        Integer marAppt = 0;
        Integer aprAppt = 0;
        Integer mayAppt = 0;
        Integer junAppt = 0;
        Integer julAppt = 0;
        Integer augAppt = 0;
        Integer sepAppt = 0;
        Integer octAppt = 0;
        Integer novAppt = 0;
        Integer decAppt = 0;
        
        try {
            allAppointments = appointmentInterface.getAllAppointments();
        } catch (SQLException ex) {
            Logger.getLogger(ReportsScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
            for(Appointment appointment: allAppointments){
            switch (appointment.getStart().getMonthValue()) {
                case 1:
                    janAppt++;
                    break;
                case 2:
                    febAppt++;
                    break;
                case 3:
                    marAppt++;
                    break;
                case 4:
                    aprAppt++;
                    break;
                case 5:
                    mayAppt++;
                    break;
                case 6:
                    junAppt++;
                    break;
                case 7:
                    julAppt++;
                    break;
                case 8:
                    augAppt++;
                    break;                    
                case 9:
                    sepAppt++;
                    break;
                case 10:
                    octAppt++;
                    break;
                case 11:
                    novAppt++;
                    break;
                case 12:
                    decAppt++;
                    break;
                default:
                    System.out.println("Invalid Appointment Type");
                    break;                    
            }
            }
        janLbl.setText(janAppt.toString());
        febLbl.setText(febAppt.toString());
        marLbl.setText(marAppt.toString());
        aprLbl.setText(aprAppt.toString());
        mayLbl.setText(mayAppt.toString());
        junLbl.setText(junAppt.toString());
        julLbl.setText(julAppt.toString());
        augLbl.setText(augAppt.toString());
        sepLbl.setText(sepAppt.toString());
        octLbl.setText(octAppt.toString());
        novLbl.setText(novAppt.toString());
        decLbl.setText(decAppt.toString());

        
        //Sets Items in the Consultant Schedule Tab

        UserInterface userInterface = new UserImpl();
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        try {
            allUsers = userInterface.getAllUsers();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ReportsScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        TreeItem<Object> dummyRoot = new TreeItem<>();
        scheduleTreeTableView.setRoot(dummyRoot);
        scheduleTreeTableView.setShowRoot(false);
        for(User user : allUsers){
            TreeItem<Object> itemUser = new TreeItem<>(user);
            dummyRoot.getChildren().add(itemUser);
            for(Appointment appointment: allAppointments){
                if(user.getUserId() == appointment.getUserId()){
                    TreeItem<Object> itemAppt = new TreeItem<>(appointment);
                    itemUser.getChildren().add(itemAppt);
                }
            }
            
        }
        consultantCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("userName"));
        appointmentStartCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("start"));
        
        
        
        //Sets Items in Country Report Tableview
        CountryInterface countryInterface = new CountryImpl();
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        try {
            allCountries = countryInterface.getAllCountries();
        } catch (SQLException ex) {
            Logger.getLogger(ReportsScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        countryTableView.setItems(allCountries);
        countryIdCol.setCellValueFactory(new PropertyValueFactory("countryId"));
        countryCol.setCellValueFactory(new PropertyValueFactory("country"));
    }    
    
}
