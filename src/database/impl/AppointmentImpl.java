package database.impl;

import database.DBConnection;
import database.DBQuery;
import database.dao.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.*;
import java.time.LocalDateTime;


public class AppointmentImpl extends AppointmentDAO {

    // Initializes Insert string
    private static final String INSERT = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, " +
            "Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // Initializes Read string
    private static final String GET_APPT = "SELECT * FROM appointments WHERE appointmentID = ?";

    // Initializes Read All string
    private static final String GET_ALL = "SELECT * FROM appointments";

    // Initializes Update string
    private static final String UPDATE = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
            "Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_Id = ?, Contact_ID = ? WHERE appointment_ID = ?";

    //Initializes Delete string
    private static final String DELETE = "DELETE FROM appointments WHERE appointment_ID = ?";


    @Override
    public Appointment create(Appointment appointment) {
        Connection conn = DBConnection.startConnection();
        try(PreparedStatement statement = conn.prepareStatement(INSERT)) {
            statement.setString(1, appointment.getTitle());
            statement.setString(2, appointment.getDescription());
            statement.setString(3, appointment.getLocation());
            statement.setString(4, appointment.getType());
            statement.setTimestamp(5, java.sql.Timestamp.valueOf(appointment.getStart()));
            statement.setTimestamp(6, java.sql.Timestamp.valueOf(appointment.getEnd()));
            statement.setTimestamp(7, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            statement.setString(8, appointment.getCreatedBy());
            statement.setTimestamp(9, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            statement.setString(10, appointment.getLastUpdatedBy());
            statement.setInt(11, appointment.getCustomerID());
            statement.setInt(12, appointment.getUserID());
            statement.setInt(13, appointment.getContactID());
            statement.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return appointment;
    }



    @Override
    public Appointment getAppt(int appointmentID) {
        Connection conn = DBConnection.startConnection();
        Appointment appt = null;
        try {
            DBQuery.setPreparedStatement(conn, GET_APPT);
            ResultSet resultSet = DBQuery.getPreparedStatement().getResultSet();

            while (resultSet.next()) {

                appt.setAppointmentID(resultSet.getInt("Appointment_ID"));
                appt.setTitle(resultSet.getString("Title"));
                appt.setDescription(resultSet.getString("Description"));
                appt.setLocation(resultSet.getString("Location"));
                appt.setType(resultSet.getString("Type"));
                appt.setStart(resultSet.getObject("Start", LocalDateTime.class));
                appt.setEnd(resultSet.getObject("End", LocalDateTime.class));
                appt.setCreateDate(resultSet.getObject("Create_Date", LocalDateTime.class));
                appt.setCreatedBy(resultSet.getString("Created_By"));
                appt.setLastUpdate(resultSet.getTimestamp("Last_Update"));
                appt.setLastUpdatedBy(resultSet.getString("Last_Updated_By"));
                appt.setCustomerID(resultSet.getInt("Customer_ID"));
                appt.setUserID(resultSet.getInt("User_ID"));
                appt.setContactID(resultSet.getInt("Contact_ID"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return appt;
    }



    @Override
    public ObservableList<Appointment> getAllAppt() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Connection conn = DBConnection.startConnection();
        try {
            Appointment appt = null;
            DBQuery.setPreparedStatement(conn, GET_ALL);
            ResultSet resultSet = DBQuery.getPreparedStatement().getResultSet();
            while (resultSet.next()) {
                appt.setAppointmentID(resultSet.getInt("Appointment_ID"));
                appt.setTitle(resultSet.getString("Title"));
                appt.setDescription(resultSet.getString("Description"));
                appt.setLocation(resultSet.getString("Location"));
                appt.setType(resultSet.getString("Type"));
                appt.setStart(resultSet.getObject("Start", LocalDateTime.class));
                appt.setEnd(resultSet.getObject("End", LocalDateTime.class));
                appt.setCreateDate(resultSet.getObject("Create_Date", LocalDateTime.class));
                appt.setCreatedBy(resultSet.getString("Created_By"));
                appt.setLastUpdate(resultSet.getTimestamp("Last_Update"));
                appt.setLastUpdatedBy(resultSet.getString("Last_Updated_By"));
                appt.setCustomerID(resultSet.getInt("Customer_ID"));
                appt.setUserID(resultSet.getInt("User_ID"));
                appt.setContactID(resultSet.getInt("Contact_ID"));

                appointments.add(appt);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return appointments;
    }




    @Override
    public Appointment update(Appointment appointment) {
        Connection conn = DBConnection.startConnection();
        Appointment tempAppt = null;
        try (PreparedStatement statement = conn.prepareStatement(UPDATE)) {

            statement.setInt(1, appointment.getAppointmentID());
            statement.setString(2, appointment.getTitle());
            statement.setString(3, appointment.getDescription());
            statement.setString(4, appointment.getLocation());
            statement.setString(5, appointment.getType());
            statement.setTimestamp(6, Timestamp.valueOf(appointment.getStart()));
            statement.setTimestamp(7, Timestamp.valueOf(appointment.getEnd()));
            statement.setTimestamp(8, Timestamp.valueOf(appointment.getCreateDate()));
            statement.setString(9, appointment.getCreatedBy());
            statement.setTimestamp(10, appointment.getLastUpdate());
            statement.setString(11, appointment.getLastUpdatedBy());
            statement.setInt(12, appointment.getCustomerID());
            statement.setInt(13, appointment.getUserID());
            statement.setInt(14, appointment.getContactID());

            tempAppt = this.getAppt(appointment.getAppointmentID());

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return tempAppt;
    }



    @Override
    public void delete(int appointmentID) {
        Connection conn = DBConnection.startConnection();
        try (PreparedStatement statement = conn.prepareStatement(DELETE)) {
            statement.setInt(1, appointmentID);
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
    }
}
