package database;

import model.Customer;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomerImpl extends CustomerDAO{

    // Initializes Insert string
    private static final String INSERT = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_by, Division_ID)" +
            " Values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // Initializes Read string
    private static final String GET_CUSTOMER = "SELECT * FROM customers WHERE customerID = ?";

    // Initializes Update string
    private static final String UPDATE = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_code = ?, Create_Date = ?, " +
            "Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";

    //Initializes Delete string
    private static final String DELETE = "DELETE FROM customers WHERE Customer_ID = ?";


    @Override
    public Customer getCustomer(int customerID) {
        // Establish connection to the database
        Connection conn = DBConnection.startConnection();
        Customer customer = null;
        try {
            // Establish PreparedStatement
            PreparedStatement statement = conn.prepareStatement(GET_CUSTOMER);
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                customer.setCustomerID(resultSet.getInt("Customer_ID"));
                customer.setCustomerName(resultSet.getString("Customer_Name"));
                customer.setAddress(resultSet.getString("Address"));
                customer.setPostalCode(resultSet.getString("Postal_Code"));
                customer.setPhoneNumber(resultSet.getString("Phone"));
                LocalDate dateCreated = resultSet.getDate("Create_Date").toLocalDate();
                LocalTime timeCreated = resultSet.getTime("Create_Date").toLocalTime();
                LocalDateTime creation = LocalDateTime.of(dateCreated, timeCreated);
                customer.setCreateDate(creation);
                customer.setCreatedBy(resultSet.getString("Created_By"));
                customer.setLastUpdated(resultSet.getTimestamp("Last_Update"));
                customer.setLastUpdatedBy(resultSet.getString("Last_Updated_By"));
                customer.setDivisionID(resultSet.getInt("Division_ID"));
                return customer;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return null;
    }

    @Override
    public List<Customer> allCustomers() {
        return null;
    }

    @Override
    public Customer update(Customer customer) {
        Customer tempCus = null;
        Connection conn = DBConnection.startConnection();
        try (PreparedStatement statement = conn.prepareStatement(UPDATE);) {

            //statement.setInt(1, customer.getCustomerID());
            statement.setString(2, customer.getCustomerName());
            statement.setString(3, customer.getAddress());
            statement.setString(4, customer.getPostalCode());
            statement.setString(5, customer.getPhoneNumber());
            LocalTime rightNow = LocalTime.now();
            LocalDate today = LocalDate.now();
            LocalDateTime now = LocalDateTime.of(today, rightNow);
            //statement.setTimestamp(6, Timestamp.valueOf(now));
            //statement.setString(7, customer.getCreatedBy());
            statement.setTimestamp(8, Timestamp.valueOf(now));
            statement.setString(9, "admin");
            statement.setInt(10, customer.getDivisionID());
            statement.execute();
            tempCus = this.getCustomer(customer.getCustomerID());


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
        return tempCus;
    }

    @Override
    public Customer create(Customer customer) {
        Connection conn = DBConnection.startConnection();
        try (PreparedStatement statement = conn.prepareStatement(INSERT);) {

            statement.setString(2, customer.getCustomerName());
            statement.setString(3, customer.getAddress());
            statement.setString(4, customer.getPostalCode());
            statement.setString(5, customer.getPhoneNumber());
            LocalTime rightNow = LocalTime.now();
            LocalDate today = LocalDate.now();
            LocalDateTime now = LocalDateTime.of(today, rightNow);
            statement.setTimestamp(6, Timestamp.valueOf(now));
            statement.setString(7, customer.getCreatedBy());
            statement.setTimestamp(8, Timestamp.valueOf(now));
            statement.setString(9, customer.getLastUpdatedBy());
            statement.setInt(10, customer.getDivisionID());
            statement.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        DBConnection.closeConnection();
       return customer;
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

    }

}
