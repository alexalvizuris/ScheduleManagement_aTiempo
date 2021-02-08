package database.impl;

import database.DBConnection;
import database.DBQuery;
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


public class CustomerImpl extends CustomerDAO {

    // Initializes Insert string
    private static final String INSERT = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_by, Division_ID)" +
            " Values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // Initializes Read string
    private static final String GET_CUSTOMER = "SELECT * FROM customers WHERE Customer_ID = ?";

    //Initializes Read All string
    private static final String GET_ALL = "SELECT * FROM customers";

    // Initializes Update string
    private static final String UPDATE = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_code = ?, Create_Date = ?, " +
            "Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";

    //Initializes Delete string
    private static final String DELETE = "DELETE FROM customers WHERE Customer_ID = ?";


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




    @Override
    public Customer getCustomer(int customerID) {
        // Establish connection to the database
        Connection conn = DBConnection.startConnection();
        Customer customer = null;
        try (PreparedStatement statement = conn.prepareStatement(GET_CUSTOMER)) {
            ResultSet resultSet = DBQuery.getPreparedStatement().getResultSet();
            statement.setInt(1, customerID);

            while (resultSet.next()) {
                customer.setCustomerID(resultSet.getInt("Customer_ID"));
                customer.setCustomerName(resultSet.getString("Customer_Name"));
                customer.setAddress(resultSet.getString("Address"));
                customer.setPostalCode(resultSet.getString("Postal_Code"));
                customer.setPhoneNumber(resultSet.getString("Phone"));
                customer.setCreateDate(resultSet.getObject(6, LocalDateTime.class));
                customer.setCreatedBy(resultSet.getString("Created_By"));
                customer.setLastUpdated(resultSet.getTimestamp("Last_Update"));
                customer.setLastUpdatedBy(resultSet.getString("Last_Updated_By"));
                customer.setDivisionID(resultSet.getInt("Division_ID"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return customer;
    }



    @Override
    public ObservableList<Customer> allCustomers() {
        ObservableList<Customer> allCust = FXCollections.observableArrayList();
        Connection conn = DBConnection.startConnection();
        try {
            Customer customer = null;
            DBQuery.setPreparedStatement(conn, GET_ALL);
            ResultSet resultSet = DBQuery.getPreparedStatement().getResultSet();
            while (resultSet.next()) {
                customer.setCustomerID(resultSet.getInt("Customer_ID"));
                customer.setCustomerName(resultSet.getString("Customer_Name"));
                customer.setAddress(resultSet.getString("Address"));
                customer.setPostalCode(resultSet.getString("Postal_Code"));
                customer.setPhoneNumber(resultSet.getString("Phone"));
                customer.setCreateDate(resultSet.getObject(6, LocalDateTime.class));
                customer.setCreatedBy(resultSet.getString("Created_By"));
                customer.setLastUpdated(resultSet.getTimestamp("Last_Update"));
                customer.setLastUpdatedBy(resultSet.getString("Last_Updated_By"));
                customer.setDivisionID(resultSet.getInt("Division_ID"));
                allCust.add(customer);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return allCust;
    }


    @Override
    public Customer update(Customer customer) {
        Customer tempCus = null;
        Connection conn = DBConnection.startConnection();
        try (PreparedStatement statement = conn.prepareStatement(UPDATE)) {

            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getAddress());
            statement.setString(3, customer.getPostalCode());
            statement.setString(4, customer.getPhoneNumber());
            statement.setTimestamp(5, Timestamp.valueOf(customer.getCreateDate()));
            statement.setString(6, customer.getCreatedBy());
            statement.setTimestamp(7, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            statement.setString(8, customer.getLastUpdatedBy());
            statement.setInt(9, customer.getDivisionID());
            statement.setInt(10, customer.getCustomerID());
            statement.execute();
            tempCus = this.getCustomer(customer.getCustomerID());


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return tempCus;
    }



    @Override
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
