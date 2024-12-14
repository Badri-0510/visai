package com.visai.visai.Repository;



import com.visai.visai.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    /**
     * Fetch customer details by their phone number.
     *
     * @param phoneNumber the phone number of the customer to retrieve
     * @return an Optional containing the Customer if found, or empty otherwise
     */
    @Query("SELECT c FROM Customer c WHERE c.phoneNumber = :phoneNumber")
    Optional<Customer> findCustomerByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    /**
     * Fetch all customers with their details.
     *
     * @return a list of all customers
     */
    @Query("SELECT c FROM Customer c")
    List<Customer> findAllCustomers();

    /**
     * Update customer details. This is handled by the JpaRepository's save method.
     * The save method will check if the customer exists based on their ID,
     * and if it exists, it will update the customer. Otherwise, it will insert a new customer.
     *
     * @param customer the updated customer entity
     * @return the updated customer entity
     */
    // No query needed; JpaRepository's save() method handles this.

    /**
     * Delete a customer by ID.
     *
     * @param customerId the ID of the customer to delete
     */
    @Query("DELETE FROM Customer c WHERE c.id = :customerId")
    void deleteCustomerById(@Param("customerId") int customerId);

    /**
     * Check if a customer exists by their phone number.
     *
     * @param phoneNumber the phone number of the customer
     * @return true if the customer exists, false otherwise
     */
    @Query("SELECT COUNT(c) > 0 FROM Customer c WHERE c.phoneNumber = :phoneNumber")
    boolean existsByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    /**
     * Fetch a customer by their ID.
     *
     * @param customerId the ID of the customer
     * @return an Optional containing the Customer if found, or empty otherwise
     */
    Optional<Customer> findById(int customerId);
}
