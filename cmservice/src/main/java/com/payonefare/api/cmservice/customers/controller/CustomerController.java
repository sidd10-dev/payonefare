package com.payonefare.api.cmservice.customers.controller;

import com.payonefare.api.cmservice.customers.dto.request.GetOrCreateCustomerRequestDto;
import com.payonefare.api.cmservice.customers.service.CustomerService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/customers")
public class CustomerController {

    private final CustomerService customerService;

    private final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService) {
        LOG.debug("INIT Start: CustomerController");
        this.customerService = customerService;
        LOG.debug("INIT End: CustomerController");
    }

    /**
     * Post request to create customer if he does not exist
     * @param getOrCreateCustomerRequestDto
     * @return
     */
    @Post
    public HttpResponse<Long> getOrCreateCustomer(@Valid @Body GetOrCreateCustomerRequestDto getOrCreateCustomerRequestDto) {
        LOG.debug("REQUEST : Get Or Create Customer, {}", getOrCreateCustomerRequestDto);
        Long id = customerService.createCustomerIfNotExist(getOrCreateCustomerRequestDto);
        LOG.debug("RESPONSE : Customer ID, {}", id);
        return HttpResponse.ok(id);
    }
}
