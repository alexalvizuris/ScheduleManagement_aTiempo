package database.impl;

import database.DBConnection;
import database.DBQuery;
import database.interfaces.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.*;
import java.time.LocalDateTime;


public class AppointmentImpl implements AppointmentDAO {

    // Initializes Insert string
    private static final String INSERT = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, " +
            "Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // Initializes Read string
    private static final String GET_APPT = "SELECT * FROM appointments WHERE Appointment_ID = ?";

    // Initializes Read All string
    private static final String GET_ALL = "SELECT * FROM appointments";

    // Initializes Update string
    private static final String UPDATE = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
            "Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_Id = ?, Contact_ID = ? WHERE Appointment_ID = ?";

    //Initializes Delete string
    private static final String DELETE = "DELETE FROM appointments WHERE Appointment_ID = ?";



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




    public Appointment getAppt(int appointmentID) {
        Connection conn = DBConnection.startConnection();


        try (PreparedStatement statement = conn.prepareStatement(GET_APPT)) {
            statement.setInt(1, appointmentID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                LocalDateTime start = resultSet.getObject("Start", LocalDateTime.class);
                LocalDateTime end = resultSet.getObject("End", LocalDateTime.class);
                LocalDateTime createDate = resultSet.getObject("Create_Date", LocalDateTime.class);
                String createdBy = resultSet.getString("Created_By");
                Timestamp update = resultSet.getTimestamp("Last_Update");
                String updatedBy = resultSet.getString("Last_Updated_By");
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");

                Appointment appt = new Appointment(title, description, location, type, start, end);
                appt.setCreateDate(createDate);
                appt.setCreatedBy(createdBy);
                appt.setLastUpdate(update);
                appt.setLastUpdatedBy(updatedBy);
                appt.setCustomerID(customerId);
                appt.setUserID(userId);
                appt.setContactID(contactId);

                return appt;

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return null;
    }




    public ObservableList<Appointment> getAllAppt() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Connection conn = DBConnection.startConnection();

        try (PreparedStatement statement = conn.prepareStatement(GET_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                int id = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                LocalDateTime start = resultSet.getObject("Start", LocalDateTime.class);
                LocalDateTime end = resultSet.getObject("End", LocalDateTime.class);
                LocalDateTime createDate = resultSet.getObject("Create_Date", LocalDateTime.class);
                String createdBy = resultSet.getString("Created_By");
                Timestamp update = resultSet.getTimestamp("Last_Update");
                String updatedBy = resultSet.getString("Last_Updated_By");
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");

                Appointment appt = new Appointment(title, description, location, type, start, end);
                appt.setCreateDate(createDate);
                appt.setCreatedBy(createdBy);
                appt.setLastUpdate(update);
                appt.setLastUpdatedBy(updatedBy);
                appt.setCustomerID(customerId);
                appt.setUserID(userId);
                appt.setContactID(contactId);

                appointments.add(appt);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return appointments;
    }





    public void update(Appointment appointment) {
        Connection conn = DBConnection.startConnection();

        try (PreparedStatement statement = conn.prepareStatement(UPDATE)) {

            int id = appointment.getAppointmentID();
            String title = appointment.getTitle();
            String description = appointment.getDescription();
            String location = appointment.getLocation();
            String type = appointment.getType();
            LocalDateTime start = appointment.getStart();
            LocalDateTime end = appointment.getEnd();
            LocalDateTime createDate = appointment.getCreateDate();
            String createdBy = appointment.getCreatedBy();
            Timestamp update = appointment.getLastUpdate();
            String updatedBy = appointment.getLastUpdatedBy();
            int customerId = appointment.getCustomerID();
            int userId = appointment.getUserID();
            int contactId = appointment.getContactID();


            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, location);
            statement.setString(4, type);
            statement.setTimestamp(5, Timestamp.valueOf(start));
            statement.setTimestamp(6, Timestamp.valueOf(end));
            statement.setTimestamp(7, Timestamp.valueOf(createDate));
            statement.setString(8, createdBy);
            statement.setTimestamp(9, update);
            statement.setString(10, updatedBy);
            statement.setInt(11, customerId);
            statement.setInt(12, userId);
            statement.setInt(13, contactId);
            statement.setInt(14, id);
            statement.execute();



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();

    }




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
