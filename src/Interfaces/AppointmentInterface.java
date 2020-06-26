/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Model.Appointment;
import java.sql.SQLException;
import java.util.Optional;
import javafx.collections.ObservableList;

/**
 *
 * @author micha
 */
public interface AppointmentInterface {

    //Create Appointment
    void createAppointment(Appointment appointment);

    //Delete Appointment
    void deleteAppointment(Appointment appointment);

    //Read All Appointments
    ObservableList<Appointment> getAllAppointments() throws SQLException;

    //Read Appointment
    Optional<Appointment> getAppointment(int appointmentIdParam) throws SQLException;

    //Update Appointment
    void updateAppointment(Appointment appointment);
    
}
