package com.payonefare.api.dbgw.customers.controller;

import com.payonefare.api.dbgw.customers.data.Customer;
import com.payonefare.api.dbgw.customers.repository.CustomerRepository;
import com.payonefare.api.dbgw.utils.Utils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.validation.Valid;

import static io.micronaut.http.HttpHeaders.LOCATION;

@Controller("/customers")
public class CustomerController {
    private final Utils utils;
    /*
        CustomerRepository Instance
         */
    private CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository, Utils utils) {
        this.customerRepository = customerRepository;
        this.utils = utils;
    }

    /*
    Request Type: GET
    Request Details: Find customer by id or return null
     */
    @Get("/{phone}")
    public Customer findCustomerById(@PathVariable String phone) {
        return customerRepository.findByPhone(phone).orElse(null);
    }

    /*
    Request Type: POST
    Request Details: Create a new Customer
     */
    @Post
    public HttpResponse<Customer> createCustomer(@Body @Valid Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        return HttpResponse
                .created(savedCustomer)
                .header(LOCATION, utils.location(savedCustomer.getId(), "customers"));
    }
}
