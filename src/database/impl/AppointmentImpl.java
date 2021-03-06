package database.impl;

import database.DBConnection;
import database.DBQuery;
import database.interfaces.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * SQL implementation class for appointment DAO
 */
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
            "Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_Id = ?, Contact_ID = ? WHERE Appointment_ID = ?";

    // Initializes Delete string
    private static final String DELETE = "DELETE FROM appointments WHERE Appointment_ID = ?";

    // Initializes Read All of User
    private static final String ALL_OF_USER = "SELECT * FROM appointments WHERE User_ID = ?";

    // Initializes Read All Week
    private static final String ALL_WEEK = "SELECT * FROM appointments WHERE YEARWEEK(Start) = YEARWEEK(CURDATE()) AND User_ID = ?";

    // Initializes Read All Month
    private static final String ALL_MONTH = "SELECT * FROM appointments WHERE MONTH(Start) = MONTH(CURDATE()) AND User_ID = ?";

    // Initializes Read All of Customer
    private static final String ALL_CUSTOMER = "SELECT * FROM appointments WHERE Customer_ID = ?";

    // Initializes Read All of Contact
    private static final String ALL_CONTACT = "SELECT * FROM appointments WHERE Contact_ID = ?";

    // Initializes Read All of Type
    private static final String ALL_TYPE = "SELECT * FROM appointments WHERE Type = ?";

    /**
     * Insert statement for appointment table
     * @param appointment references appointment object being created
     * @return appointment being inserted
     * @throws SQLException when issues occur with reading from the database
     */
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


    /**
     * Read statement for the appointment table
     * @param appointmentID references selected appointment
     * @return appointment being read
     * @throws SQLException when issues occur with reading from the database
     */
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
                Timestamp startTS = Timestamp.valueOf(start);
                LocalDateTime end = resultSet.getObject("End", LocalDateTime.class);
                Timestamp endTS = Timestamp.valueOf(end);
                LocalDateTime createDate = resultSet.getObject("Create_Date", LocalDateTime.class);
                String createdBy = resultSet.getString("Created_By");
                Timestamp update = resultSet.getTimestamp("Last_Update");
                String updatedBy = resultSet.getString("Last_Updated_By");
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");

                Appointment appt = new Appointment(title, description, location, type, startTS.toLocalDateTime(), endTS.toLocalDateTime());
                appt.setAppointmentID(id);
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


    /**
     * Read All statement from appointment table
     * @return all appointments
     * @throws SQLException when issues occur with reading from the database
     */
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
                Timestamp startTS = Timestamp.valueOf(start);
                LocalDateTime end = resultSet.getObject("End", LocalDateTime.class);
                Timestamp endTS = Timestamp.valueOf(end);
                LocalDateTime createDate = resultSet.getObject("Create_Date", LocalDateTime.class);
                String createdBy = resultSet.getString("Created_By");
                Timestamp update = resultSet.getTimestamp("Last_Update");
                String updatedBy = resultSet.getString("Last_Updated_By");
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");

                Appointment appt = new Appointment(title, description, location, type, startTS.toLocalDateTime(), endTS.toLocalDateTime());
                appt.setAppointmentID(id);
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

    /**
     * Read All appointments from single User statement
     * @param userID references selected User
     * @return all appointments from selected user
     * @throws SQLException when issues occur with reading from the database
     */
    public ObservableList<Appointment> allFromUser(int userID) {

        ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
        Connection conn = DBConnection.startConnection();

        try (PreparedStatement statement = conn.prepareStatement(ALL_OF_USER)) {
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                Timestamp startTS = resultSet.getTimestamp("Start");
                Timestamp endTS = resultSet.getTimestamp("End");
                LocalDateTime createDate = resultSet.getObject("Create_Date", LocalDateTime.class);
                String createdBy = resultSet.getString("Created_By");
                Timestamp update = resultSet.getTimestamp("Last_Update");
                String updatedBy = resultSet.getString("Last_Updated_By");
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");

                Appointment appt = new Appointment(title, description, location, type, startTS.toLocalDateTime(), endTS.toLocalDateTime());
                appt.setAppointmentID(id);
                appt.setCreateDate(createDate);
                appt.setCreatedBy(createdBy);
                appt.setLastUpdate(update);
                appt.setLastUpdatedBy(updatedBy);
                appt.setCustomerID(customerId);
                appt.setUserID(userId);
                appt.setContactID(contactId);

                allAppts.add(appt);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return allAppts;
    }


    /**
     * Read All appointments this week from appointment table
     * @param userID references selected user
     * @return all appointments this week
     * @throws SQLException when issues occur with reading from the database
     */
    public ObservableList<Appointment> weekAppointments(int userID) {

        ObservableList<Appointment> weeklyAppts = FXCollections.observableArrayList();
        Connection conn = DBConnection.startConnection();

        try (PreparedStatement statement = conn.prepareStatement(ALL_WEEK)) {

            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                LocalDateTime start = resultSet.getObject("Start", LocalDateTime.class);
                Timestamp startTS = Timestamp.valueOf(start);
                LocalDateTime end = resultSet.getObject("End", LocalDateTime.class);
                Timestamp endTS = Timestamp.valueOf(end);
                LocalDateTime createDate = resultSet.getObject("Create_Date", LocalDateTime.class);
                String createdBy = resultSet.getString("Created_By");
                Timestamp update = resultSet.getTimestamp("Last_Update");
                String updatedBy = resultSet.getString("Last_Updated_By");
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");

                Appointment appt = new Appointment(title, description, location, type, startTS.toLocalDateTime(), endTS.toLocalDateTime());
                appt.setAppointmentID(id);
                appt.setCreateDate(createDate);
                appt.setCreatedBy(createdBy);
                appt.setLastUpdate(update);
                appt.setLastUpdatedBy(updatedBy);
                appt.setCustomerID(customerId);
                appt.setUserID(userId);
                appt.setContactID(contactId);

                weeklyAppts.add(appt);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return weeklyAppts;
    }


    /**
     * Read all appointments this month from appointment table
     * @param userID references selected user
     * @return appointments this month
     * @throws SQLException when issues occur with reading from the database
     */
    public ObservableList<Appointment> monthAppointments(int userID) {

        ObservableList<Appointment> monthlyAppts = FXCollections.observableArrayList();
        Connection conn = DBConnection.startConnection();

        try (PreparedStatement statement = conn.prepareStatement(ALL_MONTH)) {


            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                LocalDateTime start = resultSet.getObject("Start", LocalDateTime.class);
                Timestamp startTS = Timestamp.valueOf(start);
                LocalDateTime end = resultSet.getObject("End", LocalDateTime.class);
                Timestamp endTS = Timestamp.valueOf(end);
                LocalDateTime createDate = resultSet.getObject("Create_Date", LocalDateTime.class);
                String createdBy = resultSet.getString("Created_By");
                Timestamp update = resultSet.getTimestamp("Last_Update");
                String updatedBy = resultSet.getString("Last_Updated_By");
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");

                Appointment appt = new Appointment(title, description, location, type, startTS.toLocalDateTime(), endTS.toLocalDateTime());
                appt.setAppointmentID(id);
                appt.setCreateDate(createDate);
                appt.setCreatedBy(createdBy);
                appt.setLastUpdate(update);
                appt.setLastUpdatedBy(updatedBy);
                appt.setCustomerID(customerId);
                appt.setUserID(userId);
                appt.setContactID(contactId);

                monthlyAppts.add(appt);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return monthlyAppts;
    }

    /**
     * Read all appointments by selected Customer
     * @param customerID references selected customer
     * @return list of appointments of customer
     * @throws SQLException when issues occur with reading from the database
     */
    public ArrayList<Appointment> allCustomerAppt(int customerID) {
        ArrayList<Appointment> apptList = new ArrayList<>();
        Connection conn = DBConnection.startConnection();

        try(PreparedStatement statement = conn.prepareStatement(ALL_CUSTOMER)) {
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                Timestamp startTS = resultSet.getTimestamp("Start");
                Timestamp endTS = resultSet.getTimestamp("End");
                LocalDateTime createDate = resultSet.getObject("Create_Date", LocalDateTime.class);
                String createdBy = resultSet.getString("Created_By");
                Timestamp update = resultSet.getTimestamp("Last_Update");
                String updatedBy = resultSet.getString("Last_Updated_By");
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");

                Appointment appt = new Appointment(title, description, location, type, startTS.toLocalDateTime(), endTS.toLocalDateTime());
                appt.setAppointmentID(id);
                appt.setCreateDate(createDate);
                appt.setCreatedBy(createdBy);
                appt.setLastUpdate(update);
                appt.setLastUpdatedBy(updatedBy);
                appt.setCustomerID(customerId);
                appt.setUserID(userId);
                appt.setContactID(contactId);

                apptList.add(appt);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return apptList;
    }


    /**
     * Update statement for appointment table
     * @param appointment references appointment being updated
     * @throws SQLException when issues occur with reading from the database
     */
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
            Timestamp update = java.sql.Timestamp.valueOf(java.time.LocalDateTime.now());
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
            statement.setTimestamp(7, update);
            statement.setString(8, updatedBy);
            statement.setInt(9, customerId);
            statement.setInt(10, userId);
            statement.setInt(11, contactId);
            statement.setInt(12, id);
            statement.execute();



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();

    }


    /**
     * delete statement for appointment table
     * @param appointmentID references appointment being deleted
     * @throws SQLException when issues occur with reading from the database
     */
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


    /**
     * Read All statement of appointments by type
     * @param types references type being displayed
     * @return list of appointments
     * @throws SQLException when issues occur with reading from the database
     */
    public ObservableList<Appointment> ofType(String types) {
        ObservableList<Appointment> allAppt = FXCollections.observableArrayList();
        Connection conn = DBConnection.startConnection();

        try (PreparedStatement statement = conn.prepareStatement(ALL_TYPE)) {
            statement.setString(1, types);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                LocalDateTime start = resultSet.getObject("Start", LocalDateTime.class);
                Timestamp startTS = Timestamp.valueOf(start);
                LocalDateTime end = resultSet.getObject("End", LocalDateTime.class);
                Timestamp endTS = Timestamp.valueOf(end);
                LocalDateTime createDate = resultSet.getObject("Create_Date", LocalDateTime.class);
                String createdBy = resultSet.getString("Created_By");
                Timestamp update = resultSet.getTimestamp("Last_Update");
                String updatedBy = resultSet.getString("Last_Updated_By");
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");

                Appointment appt = new Appointment(title, description, location, type, startTS.toLocalDateTime(), endTS.toLocalDateTime());
                appt.setAppointmentID(id);
                appt.setCreateDate(createDate);
                appt.setCreatedBy(createdBy);
                appt.setLastUpdate(update);
                appt.setLastUpdatedBy(updatedBy);
                appt.setCustomerID(customerId);
                appt.setUserID(userId);
                appt.setContactID(contactId);

                allAppt.add(appt);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return allAppt;

    }

}
