package database.dao;

import javafx.collections.ObservableList;
import model.Appointment;

public abstract class AppointmentDAO {


    public AppointmentDAO() {

        super();
    }

    public abstract Appointment create(Appointment appointment);
    public abstract Appointment getAppt(int appointmentID);
    public abstract ObservableList<Appointment> getAllAppt();
    public abstract Appointment update(Appointment appointment);
    public abstract void delete(int appointmentID);

}
