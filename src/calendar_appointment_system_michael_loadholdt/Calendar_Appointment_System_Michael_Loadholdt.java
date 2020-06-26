/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendar_appointment_system_michael_loadholdt;

import Database.DatabaseConnection;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author micha
 */
public class Calendar_Appointment_System_Michael_Loadholdt extends Application {
    static Stage stage;
    
    @Override
    public void start(Stage stage){
        
        ResourceBundle login = ResourceBundle.getBundle("Utilities/login");
       
        Parent root = null;
        try{
            
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
        loader.setResources(login);
        root = loader.load();
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException{
            DatabaseConnection.makeConnection();
            launch(args);
            DatabaseConnection.closeConnection();

        }
    }
