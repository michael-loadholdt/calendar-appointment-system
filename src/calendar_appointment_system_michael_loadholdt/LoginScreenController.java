/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendar_appointment_system_michael_loadholdt;

import Database.Query;
import View_Controller.MainScreenController;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.*;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author micha
 */
public class LoginScreenController implements Initializable {
    
    Stage stage;
    Parent scene;

    
    
    @FXML
    private Label label;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button exitButton;

    @FXML
    private Button loginButton;
    
    @FXML
    private Label errorLabel;
    
    ResourceBundle login;
    
    
    
    
    @FXML
    void exitApplication(ActionEvent event) throws SQLException {
        
        System.exit(0);

    }

   @FXML
    void loginToDatabase(ActionEvent event) throws IOException, SQLException {
        String loginPassword = null;
        //Getting Password for Username in Textfield
        if(usernameTextField.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(login.getString("usernameError"));
            alert.showAndWait();
        }
        else if(passwordTextField.getText().trim().isEmpty()){
           Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(login.getString("passwordError"));
            alert.showAndWait(); 
        }
        else{
            try {
               loginPassword = Query.getLoginPassword(usernameTextField.getText());
            } catch (SQLException ex) {
                System.out.println("Error: "+ex.getMessage());
            }
            //Validating Password matches what is stored for user with entered Username
            if (loginPassword.equals(passwordTextField.getText())){

                MainScreenController.appointmentAlert();    
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
            stage.setTitle("Main Screen");
            stage.setScene(new Scene(scene));
            stage.show();
            Path logger = Paths.get("./logintracker.txt");

                try (BufferedWriter writer = Files.newBufferedWriter(logger, 
                                                                     Charset.forName("UTF-8"),
                                                                     CREATE, WRITE, APPEND)){

                writer.write(usernameTextField.getText() + " " + ZonedDateTime.now() + "\n");

                }
                catch(Exception e){
                    System.out.println("Error: " + e.getMessage());

                }

            }
            //Alert if validation fails
            else{

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(login.getString("error"));
                alert.showAndWait();
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle login){

        this.login = login;
        
        label.setText(login.getString("login"));
        usernameLabel.setText(login.getString("username"));
        passwordLabel.setText(login.getString("password"));
        exitButton.setText(login.getString("exitButton"));
        loginButton.setText(login.getString("loginButton"));
        
    } 
    
}
