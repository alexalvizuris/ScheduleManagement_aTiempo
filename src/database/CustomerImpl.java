package database;

import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class CustomerImpl extends CustomerDAO{

    // Initializes READ string
    private static final String INSERT = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Division_ID)" +
            " Values (?, ?, ?, ?, ?, ?, ?)";

    private static final String GET_CUSTOMER = "SELECT * FROM customers WHERE customerID = ?";


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
        return null;
    }

    @Override
    public Customer create(Customer customer) {
        Connection conn = DBConnection.startConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(INSERT);

            statement.setInt(1, customer.getCustomerID());
            statement.setString(2, customer.getCustomerName());
            statement.setString(3, customer.getAddress());
            statement.setString(4, customer.getPostalCode());
            statement.setString(5, customer.getPhoneNumber());
            //Implement LocalDateTime Here!!!!



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public void delete(int customerID) {

    }

}
