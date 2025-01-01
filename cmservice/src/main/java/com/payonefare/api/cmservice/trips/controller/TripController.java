package com.payonefare.api.cmservice.trips.controller;

import com.payonefare.api.cmservice.trips.dto.CustomerTripsResponseDto;
import com.payonefare.api.cmservice.trips.service.TripService;
import com.payonefare.api.common.dto.CreateTripRequestDto;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/trips")
@ExecuteOn(TaskExecutors.BLOCKING)
public class TripController {

    private final Logger LOG = LoggerFactory.getLogger(TripController.class);
    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    /**
     * POST Request endpoint to create a new trip
     * @param createTripRequestDto
     * @return
     */
    @Post
    public HttpResponse<Long> createTrip(@Valid @Body CreateTripRequestDto createTripRequestDto) {
        LOG.debug("IN : TripController::createTrip");
        Long tripId = tripService.createTrip(createTripRequestDto);
        LOG.debug("OUT : TripController::createTrip");
        return HttpResponse.created(tripId);
    }

    /**
     * GET Request endpoint to get customer's trips
     * @param phone
     * @return
     */
    @Get("/{phone}")
    public HttpResponse<CustomerTripsResponseDto> getCustomerTrips(@PathVariable String phone) {
        LOG.debug("IN : TripController::getCustomerTrips");
        CustomerTripsResponseDto customerTripsResponseDto = tripService.getCustomerTrips(phone);
        LOG.debug("OUT : TripController::getCustomerTrips");
        return HttpResponse.ok(customerTripsResponseDto);
    }
}
