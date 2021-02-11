package database.interfaces;

import javafx.collections.ObservableList;
import model.Appointment;

public interface AppointmentDAO {


    Appointment create(Appointment appointment);
    Appointment getAppt(int appointmentID);
    ObservableList<Appointment> getAllAppt();
    void update(Appointment appointment);
    void delete(int appointmentID);

}
