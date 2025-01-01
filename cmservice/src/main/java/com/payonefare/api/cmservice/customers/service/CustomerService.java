package com.payonefare.api.cmservice.customers.service;

import com.payonefare.api.cmservice.clients.DbgwClient;
import com.payonefare.api.common.dto.CreateCustomerRequestDto;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class CustomerService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final DbgwClient httpClient;

    public CustomerService(DbgwClient httpClient) {
        LOG.debug("INIT Start: CustomerService");
        this.httpClient = httpClient;
        LOG.debug("INIT End: CustomerService");
    }

    /**
     * Service function to check if customer exists. If not create new customer
     * @param getOrCreateCustomerRequestDto
     * @return
     */
    public Long createCustomerIfNotExist(CreateCustomerRequestDto createCustomerRequestDto) {
        try {
            LOG.debug("IN: CustomerService::createCustomerIfNotExist");
            HttpResponse<Long> response = httpClient.findCustomerByPhone(createCustomerRequestDto.getPhone());

            // Create new customer in DB
            if (response.status() == HttpStatus.NOT_FOUND) {
                LOG.debug("Customer does not exist in DB. Create new customer");
                response = httpClient.createCustomer(createCustomerRequestDto);
            } else if (response.status() != HttpStatus.OK) {
                throw new RuntimeException(response.body().toString());
            }

            LOG.debug("OUT: CustomerService::createCustomerIfNotExist");
            return response.body();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }
}
