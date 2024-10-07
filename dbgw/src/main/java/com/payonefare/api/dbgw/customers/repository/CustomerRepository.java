package com.payonefare.api.dbgw.customers.repository;

import com.payonefare.api.dbgw.customers.data.Customer;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    /*
    Description: Used to query and return corresponding customer with the phone number
    Usage: Used to check if user with phone number exists or not
    Parameters: String phone number
    Return Type: Customer
     */
    Optional<Customer> findByPhone(String phone);
}
