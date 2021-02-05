package database;

import model.Customer;

import java.util.List;

public abstract class CustomerDAO {

    public CustomerDAO() {
        super();

    }

    public abstract Customer getCustomer(int customerID);
    public abstract List<Customer> allCustomers();
    public abstract Customer update(Customer customer);
    public abstract Customer create(Customer customer);
    public abstract void delete(int customerID);



}
