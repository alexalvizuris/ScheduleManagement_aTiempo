package database.impl;

import database.DBConnection;
import database.DBQuery;
import database.interfaces.ContactDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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


// fix all this shit okay

    public Contact getContact(int contactID) {
        Connection conn = DBConnection.startConnection();
        Contact contact = null;

        try (PreparedStatement statement = conn.prepareStatement(GET_CONTACT)) {
            ResultSet resultSet = DBQuery.getPreparedStatement().getResultSet();
            statement.setInt(1, contactID);

            contact.setContactID(resultSet.getInt("Contact_ID"));
            contact.setContactName(resultSet.getString("Contact_Name"));
            contact.setContactEmail(resultSet.getString("Email"));


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return contact;
    }




    public ObservableList<Contact> getAllContacts() {

        Connection conn = DBConnection.startConnection();
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        try {
            Contact contact = null;
            DBQuery.setPreparedStatement(conn, GET_ALL_CONTACTS);
            ResultSet resultSet = DBQuery.getPreparedStatement().getResultSet();

            contact.setContactID(resultSet.getInt("Contact_ID"));
            contact.setContactName(resultSet.getString("Contact_Name"));
            contact.setContactEmail(resultSet.getString("Email"));

            contactList.add(contact);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return contactList;
    }




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
