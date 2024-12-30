package com.payonefare.api.cmservice.trips.service;

import com.payonefare.api.cmservice.clients.DbgwClient;
import com.payonefare.api.cmservice.trips.dto.CustomerTripsResponseDto;
import com.payonefare.api.common.data.Trip;
import com.payonefare.api.common.dto.CreateTripRequestDto;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class TripService {

    private final Logger LOG = LoggerFactory.getLogger(TripService.class);

    private final DbgwClient httpClient;

    public TripService(DbgwClient httpClient) {
        LOG.debug("INIT Start: TripService");
        this.httpClient = httpClient;
        LOG.debug("INIT End: TripService");
    }

    /**
     * Service function to create trip
     * @param createTripRequestDto
     * @return
     */
    public Long createTrip(CreateTripRequestDto createTripRequestDto) {
        try {
            LOG.debug("IN : TripService::createTrip");

            HttpResponse<Trip> tripResponse = httpClient.createTrip(createTripRequestDto);
            if (tripResponse.status() != HttpStatus.CREATED) {
                throw new RuntimeException(tripResponse.getBody().toString());
            }

            LOG.debug("OUT : TripService::createTrip");
            return tripResponse.getBody().isPresent() ? tripResponse.body().getId() : null;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public CustomerTripsResponseDto getCustomerTrips(String phone) {
        try {
            LOG.debug("IN : TripService::getCustomerTrips");

            // Get past trips of customer
            HttpResponse<List<Trip>> pastTripsResponse = httpClient.findPastTripsOfCustomer(phone);
            if (pastTripsResponse.status() != HttpStatus.OK) {
                throw new RuntimeException(pastTripsResponse.body().toString());
            }
            LOG.debug("FETCHED Past trips of Customer");

            // Get future trips of customer
            HttpResponse<List<Trip>> upcomingTripsResponse = httpClient.findUpcomingTripsOfCustomer(phone);
            if (upcomingTripsResponse.status() != HttpStatus.OK) {
                throw new RuntimeException(upcomingTripsResponse.body().toString());
            }
            LOG.debug("FETCHED Upcoming trips of Customer");

            LOG.debug("OUT : TripService::getCustomerTrips");
            return new CustomerTripsResponseDto(
                    pastTripsResponse.getBody().isPresent() ? pastTripsResponse.body() : new ArrayList<Trip>(),
                    upcomingTripsResponse.getBody().isPresent() ? upcomingTripsResponse.body() : new ArrayList<Trip>()
            );
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
