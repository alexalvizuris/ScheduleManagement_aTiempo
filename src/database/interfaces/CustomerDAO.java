package database.interfaces;

import javafx.collections.ObservableList;
import model.Customer;



public interface CustomerDAO {


    Customer create(Customer customer);
    Customer getCustomer(int customerID);
    ObservableList<Customer> allCustomers();
    void update(Customer customer);
    void delete(int customerID);



}
