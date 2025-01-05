package com.payonefare.api.dbgw.trips.controller;

import com.payonefare.api.common.data.Trip;
import com.payonefare.api.common.dto.CompleteTripDto;
import com.payonefare.api.common.dto.CreateTripRequestDto;
import com.payonefare.api.dbgw.trips.service.TripService;
import com.payonefare.api.dbgw.utils.Utils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static io.micronaut.http.HttpHeaders.LOCATION;

@Controller(value="/trips")
public class TripController {

    private final TripService tripService;
    private final Utils utils;

    private final Logger LOG = LoggerFactory.getLogger(TripController.class);

    public TripController(TripService tripService, Utils utils) {
        LOG.info("Initialising Trip Controller");
        this.tripService = tripService;
        this.utils = utils;
        LOG.info("Initialised Trip Controller");
    }

    /**
     * POST Endpoint to create a new trip
     * @param createTripRequestDto
     * @return HttpResponse with Trip object
     */
    @Post
    public HttpResponse<Trip> createTrip(@Body @Valid CreateTripRequestDto createTripRequestDto) {
        LOG.debug("REQUEST: Create a new trip");
        Trip savedTrip = tripService.createTrip(createTripRequestDto);
        LOG.debug("RESPONSE: Created a new trip with id {}", savedTrip.getId());

        return HttpResponse
                .created(savedTrip)
                .header(LOCATION, utils.location(savedTrip.getId(), "trips"));
    }

    /**
     * PUT endpoint to mark a trip as complete
     * @param id
     * @param completeTripDto
     * @return HttpResponse with Trip object
     */
    @Put("/complete/{id}")
    public HttpResponse<Trip> completeTrip(@PathVariable Long id, @Body @Valid CompleteTripDto completeTripDto) {
        LOG.debug("REQUEST: Mark trip {} as complete", id);
        Trip trip = tripService.markTripAsComplete(id, completeTripDto);
        LOG.debug("RESPONSE: Returning Completed trip");

        return HttpResponse
                .ok(trip)
                .header(LOCATION, utils.location(trip.getId(), "trips"));
    }

    /**
     * GET endpoint to get a request by id
     * @param id
     * @return
     */
    @Get("/{id}")
    public HttpResponse<Trip> getTripById(@PathVariable Long id) {
        LOG.debug("REQUEST: Get trip by id {}", id);
        Trip trip = tripService.getTrip(id);
        LOG.debug("RESPONSE: Get trip by id {}", id);
        return null == trip ? HttpResponse.status(HttpStatus.NOT_FOUND) : HttpResponse.ok(trip);
    }

    /**
     * GET endpoint to return all pending trips for admin
     * @return HttpResponse with List of pending trips
     */
    @Get("/admin/pending")
    public HttpResponse<List<Trip>> getPendingTrips() {
        LOG.debug("REQUEST: Get all pending trips for admin");
        List<Trip> pendingTrips = tripService.getPendingTrips();
        LOG.debug("RESPONSE: Sending all pending trips");

        return HttpResponse.ok(pendingTrips);
    }

    /**
     * GET endpoint to return all confirmed trips which are not started for admin
     * @return HttpResponse with List of trips
     */
    @Get("/admin/confirmed")
    public HttpResponse<List<Trip>> getConfirmedNotStartedTrips() {
        LOG.debug("REQUEST: Get all confirmed trips which are not started yet for admin");
        List<Trip> confirmedNotStartedTrips = tripService.getConfirmedNotStartedTrips();
        LOG.debug("RESPONSE: Sending all confirmed trips which are not started yet trips");

        return HttpResponse.ok(confirmedNotStartedTrips);
    }

    /**
     * GET endpoint to return all confirmed trips which are not started for admin
     * @return HttpResponse with List of trips
     */
    @Get("/admin/completed")
    public HttpResponse<List<Trip>> getCompletedTrips() {
        LOG.debug("REQUEST: Get all completed trips for admin");
        List<Trip> completedTrips = tripService.getCompletedTrips();
        LOG.debug("RESPONSE: Sending all completed trips");

        return HttpResponse.ok(completedTrips);
    }
}
