package service;

import model.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static validation.CustomerValidation.validateCustomer;

public class CustomerService {
    private static final List<Customer> allCustomers = new ArrayList<>();

    private static CustomerService customerServiceInstance;

    private CustomerService() {}

    public static CustomerService getInstance() {
        if(customerServiceInstance == null) {
            customerServiceInstance = new CustomerService();
        }
        return customerServiceInstance;
    }

    public void addCustomer(String email, String firstName, String lastName) {
        validateCustomer(email, firstName, lastName);
        allCustomers.add(new Customer(email, firstName, lastName));
    }

    public Customer getCustomer(String email) {

        if(email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        for(Customer customer : allCustomers) {
            if(customer.getEmail().equals(email)){
                return customer;
            }
        }
        return null;
    };

    public Collection<Customer> getAllCustomers() {
        return allCustomers;
    }
}
