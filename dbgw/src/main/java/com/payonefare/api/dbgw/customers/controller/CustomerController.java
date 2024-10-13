package com.payonefare.api.dbgw.customers.controller;

import com.payonefare.api.dbgw.customers.data.Customer;
import com.payonefare.api.dbgw.customers.dto.CreateCustomerDTO;
import com.payonefare.api.dbgw.customers.service.CustomerService;
import com.payonefare.api.dbgw.trips.data.Trip;
import com.payonefare.api.dbgw.utils.Utils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static io.micronaut.http.HttpHeaders.LOCATION;

@Controller("/customers")
public class CustomerController {

    private final Utils utils;
    private final CustomerService customerService;

    private final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService, Utils utils) {
        LOG.info("Initialising CustomerController");
        this.customerService = customerService;
        this.utils = utils;
        LOG.info("Initialised CustomerController");
    }

    /**
     * GET request endpoint to find a customer with phone number
     * @param phone
     * @return Customer Object
     */
    @Get("/{phone}")
    public HttpResponse<Customer> findCustomerByPhone(@PathVariable String phone) {

        LOG.debug("REQUEST: Find Customer By Phone {}", phone);
        Customer customer = customerService.findCustomerByPhone(phone);
        LOG.debug("RESPONSE: Returning Customer with Phone {}", phone);

        return HttpResponse.ok(customer);
    }

    /**
     * GET request endpoint to get past trips of a customer
     * @param phone
     * @return List<Trip>
     */
    @Get("/{phone}/trips/past")
    public HttpResponse<List<Trip>> findCustomerPastTrips(@PathVariable String phone) {

        LOG.debug("REQUEST: Find All Past trips of customer {}", phone);
        List<Trip> pastTrips = customerService.findCustomerPastTrips(phone);
        LOG.debug("RESPONSE: Returning All Past trips");

        return HttpResponse.ok(pastTrips);
    }

    @Get("/{phone}/trips/future")
    public HttpResponse<List<Trip>> findCustomerFutureTrips(@PathVariable String phone) {

        LOG.debug("REQUEST: Find All Future trips of customer {}", phone);
        List<Trip> futureTrips = customerService.findCustomerFutureTrips(phone);
        LOG.debug("RESPONSE: Returning All Future trips");

        return HttpResponse.ok(futureTrips);
    }

    /**
     * POST request endpoint to
     * @param createCustomerDTO
     * @return
     */
    @Post
    public HttpResponse<Customer> createCustomer(@Body @Valid CreateCustomerDTO createCustomerDTO) {

        LOG.debug("REQUEST: Create New Customer");
        Customer savedCustomer = customerService.createCustomer(createCustomerDTO);
        LOG.debug("RESPONSE: Newly created Customer Object with ID {}", savedCustomer.getId());

        return HttpResponse
                .created(savedCustomer)
                .header(LOCATION, utils.location(savedCustomer.getId(), "customers"));
    }
}
