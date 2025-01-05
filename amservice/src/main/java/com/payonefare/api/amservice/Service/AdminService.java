package com.payonefare.api.amservice.Service;

import com.payonefare.api.amservice.clients.DbgwClient;
import com.payonefare.api.common.data.Driver;
import com.payonefare.api.common.data.Trip;
import com.payonefare.api.common.dto.AdminTripResponseDto;
import com.payonefare.api.common.dto.AllotDriverDto;
import com.payonefare.api.common.dto.CompleteTripDto;
import com.payonefare.api.common.dto.CreateDriverDto;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Singleton
public class AdminService {

    private final Logger LOG = LoggerFactory.getLogger(AdminService.class);
    private final DbgwClient httpClient;

    private final String TRIP_NOT_FOUND = "Trip with given id not found!!";

    public AdminService(DbgwClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Service Function to assign a driver to trip
     * @param id
     * @param allotDriverDto
     * @return
     */
    public Trip assignDriverToTrip(Long id, AllotDriverDto allotDriverDto) {
        try {
            LOG.debug("IN : AdminService::assignDriverToTrip");

            // Get Or Create Driver
            HttpResponse<Driver> response = httpClient.getDriverByPhone(allotDriverDto.getPhone());
            if (response.status() == HttpStatus.NOT_FOUND) {
                // Driver not found. Create a new entry
                CreateDriverDto newDriver = new CreateDriverDto(allotDriverDto.getName(), allotDriverDto.getPhone());
                response = httpClient.createNewDriver(newDriver);
            }

            if (response.status() != HttpStatus.OK) {
                throw new RuntimeException(response.body().toString());
            }

            HttpResponse<Trip> tripResponse = httpClient.assignDriverToTrip(id, allotDriverDto);
            if (tripResponse.status() != HttpStatus.OK) {
                throw new RuntimeException(tripResponse.body().toString());
            }

            LOG.debug("OUT : AdminService::assignDriverToTrip");
            return tripResponse.body();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    /**
     * Service function to mark a trip as complete
     * @param id
     * @param completeTripDto
     * @return
     */
    public Trip markTripAsComplete(Long id, CompleteTripDto completeTripDto) {
        try {
            LOG.debug("IN : AdminService::markTripAsComplete");
            HttpResponse<Trip> completedTrip = httpClient.completeTrip(id, completeTripDto);

            if (completedTrip.status() != HttpStatus.OK) {
                throw new RuntimeException(completedTrip.body().toString());
            }

            LOG.debug("OUT : AdminService::markTripAsComplete");
            return completedTrip.body();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    /**
     * GET all trips for admin trips
     * @return
     */
    public AdminTripResponseDto getAdminTrips() {
        try {
            LOG.debug("IN : AdminService::getAdminTrips");
            HttpResponse<List<Trip>> pendingTrips = httpClient.getPendingTrips();
            if (pendingTrips.status() != HttpStatus.OK) {
                throw new RuntimeException(pendingTrips.body().toString());
            }
            LOG.debug("Got pending trips. Count : {}", pendingTrips.body().size());

            HttpResponse<List<Trip>> confirmedTrips = httpClient.getConfirmedNotStartedTrips();
            if (confirmedTrips.status() != HttpStatus.OK) {
                throw new RuntimeException(confirmedTrips.body().toString());
            }
            LOG.debug("Got confirmed trips. Count : {}", confirmedTrips.body().size());

            HttpResponse<List<Trip>> completedTrips = httpClient.getCompletedTrips();
            if (completedTrips.status() != HttpStatus.OK) {
                throw new RuntimeException(completedTrips.body().toString());
            }
            LOG.debug("Got completed trips. Count : {}", completedTrips.body().size());

            LOG.debug("OUT : AdminService::getAdminTrips");
            return new AdminTripResponseDto(pendingTrips.body(), confirmedTrips.body(), completedTrips.body());
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }
}
