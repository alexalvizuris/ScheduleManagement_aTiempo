package database;

import javafx.collections.ObservableList;
import model.Customer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public abstract class CustomerDAO {

    protected final static String LAST_VAL = "SELECT last_value FROM ";

    public CustomerDAO() {
        super();

    }

    public abstract Customer getCustomer(int customerID);
    public abstract ObservableList<Customer> allCustomers();
    public abstract Customer update(Customer customer);
    public abstract Customer create(Customer customer);
    public abstract void delete(int customerID);

    protected int getLastNum(String sequence) {
        Connection conn = DBConnection.startConnection();
        int key = 0;
        String sql = LAST_VAL + sequence;
        try (Statement statement = conn.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                key = resultSet.getInt(1);
            }
            return key;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return key;
    }

}
