package com.payonefare.api.amservice.clients;

import com.payonefare.api.common.data.Driver;
import com.payonefare.api.common.data.Trip;
import com.payonefare.api.common.dto.AllotDriverDto;
import com.payonefare.api.common.dto.CompleteTripDto;
import com.payonefare.api.common.dto.CreateDriverDto;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import jakarta.validation.Valid;

import java.util.List;

@Client(id = "dbgw")
public interface DbgwClient {

    // Requests to Driver Controller
    @Get("/drivers/{phone}")
    HttpResponse<Driver> getDriverByPhone(@PathVariable String phone);

    @Post("/drivers")
    HttpResponse<Driver> createNewDriver(@Body @Valid CreateDriverDto createDriverDto);

    @Put("/drivers/assign/{tripId}")
    HttpResponse<Trip> assignDriverToTrip(@PathVariable Long tripId, @Body @Valid AllotDriverDto allotDriverDto);

    // Requests to Trip Controller
    @Get("trips/admin/pending")
    HttpResponse<List<Trip>> getPendingTrips();

    @Get("trips/admin/confirmed")
    HttpResponse<List<Trip>> getConfirmedNotStartedTrips();

    @Get("trips/admin/completed")
    HttpResponse<List<Trip>> getCompletedTrips();

    @Put("/trips/complete/{id}")
    HttpResponse<Trip> completeTrip(@PathVariable Long id, @Body @Valid CompleteTripDto completeTripDto);
}
