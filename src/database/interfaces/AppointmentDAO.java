package database.interfaces;

import javafx.collections.ObservableList;
import model.Appointment;

public interface AppointmentDAO {


    public Appointment create(Appointment appointment);
    public Appointment getAppt(int appointmentID);
    public ObservableList<Appointment> getAllAppt();
    public void update(Appointment appointment);
    public void delete(int appointmentID);

}
