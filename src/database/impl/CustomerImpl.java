package database.impl;

import database.DBConnection;
import database.interfaces.CustomerDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import java.sql.*;
import java.time.LocalDateTime;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Customer implementation class for customer DAO
 */
public class CustomerImpl implements CustomerDAO {

    // Initializes Insert string
    private static final String INSERT = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_by, Division_ID)" +
            " Values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // Initializes Read string
    private static final String GET_CUSTOMER = "SELECT * FROM customers WHERE Customer_ID = ?";

    //Initializes Read All string
    private static final String GET_ALL = "SELECT * FROM customers";

    // Initializes Update string
    private static final String UPDATE = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_code = ?, Phone = ?, " +
            "Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";

    //Initializes Delete string
    private static final String DELETE = "DELETE FROM customers WHERE Customer_ID = ?";


    /**
     * Insert statement for customer table
     * @param customer references customer object being created
     * @return new customer
     * @throws SQLException when issues occur with reading from the database
     */
    @Override
    public Customer create(Customer customer) {
        Connection conn = DBConnection.startConnection();
        try (PreparedStatement statement = conn.prepareStatement(INSERT)) {

            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getAddress());
            statement.setString(3, customer.getPostalCode());
            statement.setString(4, customer.getPhoneNumber());
            statement.setTimestamp(5, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            statement.setString(6, customer.getCreatedBy());
            statement.setTimestamp(7, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            statement.setString(8, customer.getLastUpdatedBy());
            statement.setInt(9, customer.getDivisionID());
            statement.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return customer;
    }


    /**
     * Read statement for customer table
     * @param customerID references customer being read
     * @return selected customer
     * @throws SQLException when issues occur with reading from the database
     */
    @Override
    public Customer getCustomer(int customerID) {
        // Establish connection to the database
        Connection conn = DBConnection.startConnection();

        try (PreparedStatement statement = conn.prepareStatement(GET_CUSTOMER)) {

            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("Customer_ID");
                String name = resultSet.getString("Customer_Name");
                String address = resultSet.getString("Address");
                String postalCode = resultSet.getString("Postal_Code");
                String phoneNum = resultSet.getString("Phone");
                LocalDateTime create = resultSet.getObject(6, LocalDateTime.class);
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
                String updatedBy = resultSet.getString("Last_Updated_By");
                int divisionID = resultSet.getInt("Division_ID");

                Customer customer = new Customer(name, address, postalCode, phoneNum);
                customer.setCustomerID(id);
                customer.setCreateDate(create);
                customer.setCreatedBy(createdBy);
                customer.setLastUpdated(lastUpdate);
                customer.setLastUpdatedBy(updatedBy);
                customer.setDivisionID(divisionID);

                return customer;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return null;
    }


    /**
     * Read All statement for customer table
     * @return all customers
     * @throws SQLException when issues occur with reading from the database
     */
    public ObservableList<Customer> allCustomers() {
        ObservableList<Customer> allCust = FXCollections.observableArrayList();
        Connection conn = DBConnection.startConnection();
        try (PreparedStatement statement = conn.prepareStatement(GET_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                int id = resultSet.getInt("Customer_ID");
                String name = resultSet.getString("Customer_Name");
                String address = resultSet.getString("Address");
                String postalCode = resultSet.getString("Postal_Code");
                String phoneNum = resultSet.getString("Phone");
                LocalDateTime create = resultSet.getObject(6, LocalDateTime.class);
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
                String updatedBy = resultSet.getString("Last_Updated_By");
                int divisionID = resultSet.getInt("Division_ID");

                Customer customer = new Customer(name, address, postalCode, phoneNum);
                customer.setCustomerID(id);
                customer.setCreateDate(create);
                customer.setCreatedBy(createdBy);
                customer.setLastUpdated(lastUpdate);
                customer.setLastUpdatedBy(updatedBy);
                customer.setDivisionID(divisionID);

                allCust.add(customer);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return allCust;
    }


    /**
     * Update statement for customer table
     * @param customer references customer being updated
     * @throws SQLException when issues occur with reading from the database
     */
    @Override
    public void update(Customer customer) {

        Connection conn = DBConnection.startConnection();
        try (PreparedStatement statement = conn.prepareStatement(UPDATE)) {

            int id = customer.getCustomerID();
            String name = customer.getCustomerName();
            String address = customer.getAddress();
            String postalCode = customer.getPostalCode();
            String phoneNum = customer.getPhoneNumber();
            Timestamp lastUpdate = java.sql.Timestamp.valueOf(java.time.LocalDateTime.now());
            String updatedBy = customer.getLastUpdatedBy();
            int divisionID = customer.getDivisionID();


            statement.setString(1, name);
            statement.setString(2, address);
            statement.setString(3, postalCode);
            statement.setString(4, phoneNum);
            statement.setTimestamp(5, lastUpdate);
            statement.setString(6, updatedBy);
            statement.setInt(7, divisionID);
            statement.setInt(8, id);
            statement.execute();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();

    }


    /**
     * Delete statement for customer table
     * @param customerID references customer being deleted from the database
     * @throws SQLException when issues occur with reading from the database
     */
    public void delete(int customerID) {
        Connection conn = DBConnection.startConnection();
        try (PreparedStatement statement = conn.prepareStatement(DELETE)) {
            statement.setInt(1, customerID);
            statement.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
    }

}
