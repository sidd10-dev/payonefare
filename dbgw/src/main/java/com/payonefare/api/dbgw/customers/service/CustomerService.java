package com.payonefare.api.dbgw.customers.service;

import com.payonefare.api.dbgw.consts.CommonConsts;
import com.payonefare.api.dbgw.customers.data.Customer;
import com.payonefare.api.dbgw.customers.dto.CreateCustomerDTO;
import com.payonefare.api.dbgw.customers.repository.CustomerRepository;
import com.payonefare.api.dbgw.trips.data.Trip;
import com.payonefare.api.dbgw.trips.repository.TripRepository;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Singleton
public class CustomerService {

    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private final TripRepository tripRepository;

    private final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    public CustomerService(CustomerRepository customerRepository, ModelMapper modelMapper, TripRepository tripRepository) {
        LOG.info("Initialising Customer Service");
        this.customerRepository = customerRepository;
        this.tripRepository = tripRepository;
        this.modelMapper = modelMapper;
        LOG.info("Initialised Custommer Service");
    }

    /**
     * This function checks if customer exists in the db
     * @param phone
     * @return customer or null object
     */
    public Customer findCustomerByPhone(String phone) {
        try {
            LOG.debug("IN: CustomerService::findCustomerByPhone");
            Optional<Customer> customer = customerRepository.findByPhone(phone);
            if (customer.isEmpty()) {
                throw new RuntimeException(CommonConsts.USER_NOT_FOUND);
            }
            LOG.debug("Got Customer: {}", customer.get());
            LOG.debug("OUT: CustomerService::findCustomerByPhone");
            return customer.get();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return null;
        }
    }

    /**
     * Service function to get all past trips of customer
     * @param phone
     * @return List<Trip>
     */
    public List<Trip> findCustomerPastTrips(String phone) {
        try {
            LOG.debug("IN: CustomerService::findCustomerPastTrips");
            Customer customer = findCustomerByPhone(phone);
            //Get trips
            List<Trip> pastTrips = tripRepository.findByCustomerIdAndPickupTimeLessThan(customer.getId(),
                    LocalDateTime.now());
            LOG.debug("Got Customer's Past Trips! Total {} trips found", pastTrips.size());
            LOG.debug("OUT: CustomerService::findCustomerPastTrips");
            return pastTrips;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Service function to get all future trips of customer
     * @param phone
     * @return List<Trip>
     */
    public List<Trip> findCustomerFutureTrips(String phone) {
        try {
            LOG.debug("IN: CustomerService::findCustomerFutureTrips");
            Customer customer = findCustomerByPhone(phone);
            //Get trips
            List<Trip> futureTrips = tripRepository.findByCustomerIdAndPickupTimeGreaterThan(customer.getId(),
                    LocalDateTime.now());
            LOG.debug("Got Customer's Future Trips! Total {} trips found", futureTrips.size());
            LOG.debug("OUT: CustomerService::findCustomerFutureTrips");
            return futureTrips;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     This function creates a new customer and returns the created object
     @param createCustomerDTO object
     @return customer object
     */
    public Customer createCustomer(CreateCustomerDTO createCustomerDTO) {
        try {
            LOG.debug("IN: CustomerService::createCustomer");
            /*
            Check if the customer exists in the db already
             */
            boolean isCustomerPresent = customerRepository.findByPhone(createCustomerDTO.getPhone()).isPresent();
            if (isCustomerPresent) {
                throw new RuntimeException(CommonConsts.USER_FOUND);
            }
            /*
            If customer does not exist, create new customer
             */
            Customer customer = modelMapper.map(createCustomerDTO, Customer.class);
            LOG.debug("Created new Customer Object, {}", customer);
            LOG.debug("OUT: CustomerService::createCustomer");
            return customerRepository.save(customer);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
