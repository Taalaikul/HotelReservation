package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    public static final CustomerService CUSTOMER_SERVICE= new CustomerService();

    Map<String, Customer> customerMap = new HashMap<>();


    public void addCustomer(String email, String firstName, String lastName){
        Customer customer = new Customer(firstName, lastName, email);
        customerMap.put(email, customer);
    }


    public Customer getCustomer(String customerEmail){
        return customerMap.get(customerEmail);
    }


    public Collection<Customer> getAllCustomers(){
        return customerMap.values();
    };
}
