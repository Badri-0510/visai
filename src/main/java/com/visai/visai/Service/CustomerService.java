package com.visai.visai.Service;

import com.visai.visai.Entity.Customer;
import com.visai.visai.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Get customer details by customer ID.
     *
     * @param customerId the ID of the customer
     * @return an Optional containing the Customer if found, or empty otherwise
     */
    public Optional<Customer> getCustomerById(int customerId) {
        return customerRepository.findById(customerId);
    }

    /**
     * Get all customers.
     *
     * @return a list of all customers
     */
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Create a new customer.
     *
     * @param customer the customer to create
     * @return the created customer
     */
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    /**
     * Update an existing customer.
     *
     * @param customerId the ID of the customer to update
     * @param customer the updated customer details
     * @return an Optional containing the updated Customer if found and updated, or empty if not found
     */
    public Optional<Customer> updateCustomerPoints(int customerId, Map<String, Object> pointsUpdate) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            // Update the points field
            if (pointsUpdate.containsKey("points")) {
                customer.setPoints((Integer) pointsUpdate.get("points"));
            }
            // Save the updated customer
            return Optional.of(customerRepository.save(customer));
        }
        return Optional.empty();
    }


    /**
     * Delete a customer by ID.
     *
     * @param customerId the ID of the customer to delete
     * @return true if the customer was deleted, false if not found
     */
    public boolean deleteCustomer(int customerId) {
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
            return true;
        }
        return false;
    }

    public Optional<Customer> getCustomerByphoneNumber(String phone) {
        return customerRepository.findCustomerByPhoneNumber(phone);
    }
}
