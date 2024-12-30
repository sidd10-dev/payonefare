package com.payonefare.api.cmservice.customers.service;

import com.payonefare.api.cmservice.clients.DbgwClient;
import com.payonefare.api.cmservice.customers.dto.request.GetOrCreateCustomerRequestDto;
import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
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
    public Long createCustomerIfNotExist(GetOrCreateCustomerRequestDto getOrCreateCustomerRequestDto) {
        try {
            LOG.debug("IN: CustomerService::createCustomerIfNotExist");
            HttpResponse<Long> response = httpClient.findCustomerByPhone(getOrCreateCustomerRequestDto.getPhone());

            // Create new customer in DB
            if (null == response) {
                LOG.debug("Customer does not exist in DB. Create new customer");
                response = httpClient.createCustomer(getOrCreateCustomerRequestDto);
            }

            LOG.debug("OUT: CustomerService::createCustomerIfNotExist");
            return response.body();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }
}
