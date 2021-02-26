package database.impl;

import database.DBConnection;

import database.interfaces.ContactDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Contact implementation class of Contact DAO
 */
public class ContactImpl implements ContactDAO {

    // Initializes Create string
    private static final String INSERT = "INSERT INTO contacts (Contact_Name, Email) VALUES (?, ?, ?)";

    // Initializes Read string
    private static final String GET_CONTACT = "SELECT * FROM contacts WHERE Contact_ID = ?";

    // Initializes Read All string
    private static final String GET_ALL_CONTACTS = "SELECT * FROM contacts";

    // Initializes Update string
    private static final String UPDATE = "UPDATE contacts SET Contact_Name = ?, Email = ? WHERE Contact_ID = ?";

    // Initializes Delete string
    private static final String DELETE = "DELETE FROM contacts WHERE Contact_ID = ?";


    /**
     * Insert statement for contact table.
     * @param contact references new contact object
     * @return new contact
     * @throws SQLException when issues occur with reading from the database
     */
    public Contact create(Contact contact) {
        Connection conn = DBConnection.startConnection();
        try (PreparedStatement statement = conn.prepareStatement(INSERT)) {

            statement.setInt(1, contact.getContactID());
            statement.setString(2, contact.getContactName());
            statement.setString(3, contact.getContactEmail());
            statement.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return contact;
    }


    /**
     * Read statement for contact table
     * @param contactID references contact being read
     * @return contact
     * @throws SQLException when issues occur with reading from the database
     */
    public Contact getContact(int contactID) {
        Connection conn = DBConnection.startConnection();


        try (PreparedStatement statement = conn.prepareStatement(GET_CONTACT)) {
            statement.setInt(1, contactID);
            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {

                int id = resultSet.getInt("Contact_ID");
                String name = resultSet.getString("Contact_Name");
                String email = resultSet.getString("Email");

                Contact contact = new Contact(name, email);
                contact.setContactID(id);

                return contact;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return null;
    }


    /**
     * Read all statement statement of contact table
     * @return all contacts
     * @throws SQLException when issues occur with reading from the database
     */
    public ObservableList<Contact> getAllContacts() {

        Connection conn = DBConnection.startConnection();
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        try (PreparedStatement statement = conn.prepareStatement(GET_ALL_CONTACTS)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("Contact_ID");
                String name = resultSet.getString("Contact_Name");
                String email = resultSet.getString("Email");

                Contact contact = new Contact(name, email);
                contact.setContactID(id);

                contactList.add(contact);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return contactList;
    }


    /**
     * Update statement for contact table
     * @param contact being updated
     * @throws SQLException when issues occur with reading from the database
     */
    public void update(Contact contact) {
        Connection conn = DBConnection.startConnection();

        try (PreparedStatement statement = conn.prepareStatement(UPDATE)) {

            int id = contact.getContactID();
            String name = contact.getContactName();
            String email = contact.getContactEmail();


            statement.setString(1, name);
            statement.setString(2, email);
            statement.setInt(3, id);
            statement.execute();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
    }


    /**
     * Delete statement for contact table
     * @param contactID references contact being deleted
     * @throws SQLException when issues occur with reading from the database
     */
    public void delete(int contactID) {
        Connection conn = DBConnection.startConnection();
        try (PreparedStatement statement = conn.prepareStatement(DELETE)) {
            statement.setInt(1, contactID);
            statement.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
    }

}
