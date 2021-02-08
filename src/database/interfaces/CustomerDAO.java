package database.interfaces;

import javafx.collections.ObservableList;
import model.Customer;



public abstract class CustomerDAO {



    public CustomerDAO() {
        super();

    }

    public abstract Customer create(Customer customer);
    public abstract Customer getCustomer(int customerID);
    public abstract ObservableList<Customer> allCustomers();
    public abstract Customer update(Customer customer);
    public abstract void delete(int customerID);



}
