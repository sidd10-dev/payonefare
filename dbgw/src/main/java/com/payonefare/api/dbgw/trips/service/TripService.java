package com.payonefare.api.dbgw.trips.service;

import com.payonefare.api.common.data.Customer;
import com.payonefare.api.common.data.Trip;
import com.payonefare.api.common.dto.CompleteTripDto;
import com.payonefare.api.common.dto.CreateTripRequestDto;
import com.payonefare.api.common.enums.Status;
import com.payonefare.api.dbgw.consts.CommonConsts;
import com.payonefare.api.dbgw.customers.repository.CustomerRepository;
import com.payonefare.api.dbgw.trips.repository.TripRepository;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Singleton
public class TripService {

    private final CustomerRepository customerRepository;
    private final TripRepository tripRepository;
    private final ModelMapper modelMapper;

    private final Logger LOG = LoggerFactory.getLogger(TripService.class);

    public TripService(TripRepository repository, ModelMapper modelMapper, CustomerRepository customerRepository) {
        LOG.info("Initialising Trip Service");
        this.tripRepository = repository;
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
        LOG.info("Initialised Trip Service");
    }

    /**
     * Service function to query a trip based on ID
     * @param id
     * @return
     */
    public Trip getTrip(Long id) {
        try {
            LOG.debug("IN : TripService::getTrip");
            LOG.debug("OUT : TripService::getTrip");
            return tripRepository.findById(id).orElse(null);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    /**
     * This function creates a new trip
     * @param createTripDto Object
     * @return Trip Object
     */
    public Trip createTrip(CreateTripRequestDto createTripDto) {
        try {
            LOG.debug("IN: TripService::createTrip");
            Trip trip = modelMapper.map(createTripDto, Trip.class);

            // Get the customer
            Optional<Customer> customer = customerRepository.findById(createTripDto.getCustomerId());
            if (customer.isEmpty()) {
                throw new RuntimeException(CommonConsts.USER_NOT_FOUND);
            }
            LOG.debug("Got Customer: {}", customer.get());
            // Set the customer to trip object
            trip.setCustomer(customer.get());

            // Check if pickupTime is in future
            if (trip.getPickupTime().isBefore(LocalDateTime.now())) {
                throw new RuntimeException(CommonConsts.INVALID_PICKUP_TIME);
            }

            LOG.debug("Created Trip: {}", trip);
            LOG.debug("OUT: TripService::createTrip");
            return tripRepository.save(trip);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Function to mark a trip as complete
     * @param tripId
     * @param completeTripDto
     * @return Trip
     */
    public Trip markTripAsComplete(Long tripId, CompleteTripDto completeTripDto) {
        try {
            LOG.debug("IN: TripService::markTripAsComplete");

            // Check if trip exists
            Optional<Trip> optionalTrip = tripRepository.findById(tripId);
            if (optionalTrip.isEmpty()) {
                throw new RuntimeException(CommonConsts.INVALID_TRIP_ID);
            }
            LOG.debug("Got Trip: {}", optionalTrip.get());

            // Get trip object and mark trip as complete
            Trip trip = optionalTrip.get();
            trip.setStatus(Status.COMPLETED);
            trip.setAmount(completeTripDto.getAmount());

            LOG.debug("Marked Trip {} as complete", tripId);
            LOG.debug("OUT: TripService::markTripAsComplete");
            return tripRepository.update(trip);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Function to get all pending trips from db
     * @return List<Trip>
     */
    public List<Trip> getPendingTrips() {
        try {
            LOG.debug("IN: TripService::getPendingTrips");
            LOG.debug("OUT: TripService::getPendingTrips");
            return tripRepository.findByStatusOrderByPickupTimeDesc(Status.PENDING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Function to get all pending trips from db
     * @return List<Trip>
     */
    public List<Trip> getConfirmedNotStartedTrips() {
        try {
            LOG.debug("IN: TripService::getConfirmedNotStartedTrips");
            LOG.debug("OUT: TripService::getConfirmedNotStartedTrips");
            return tripRepository.findByStatusAndPickupTimeGreaterThan(Status.CONFIRMED, LocalDateTime.now());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Function to get all pending trips from db
     * @return List<Trip>
     */
    public List<Trip> getCompletedTrips() {
        try {
            LOG.debug("IN: TripService::getCompletedTrips");
            LOG.debug("OUT: TripService::getCompletedTrips");
            return tripRepository.findByStatus(Status.COMPLETED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
