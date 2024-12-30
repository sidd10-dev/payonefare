package com.payonefare.api.cmservice.clients;

import com.payonefare.api.common.data.Trip;
import com.payonefare.api.common.dto.CreateCustomerRequestDto;
import com.payonefare.api.common.dto.CreateTripRequestDto;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import jakarta.validation.Valid;

import java.util.List;

@Client(id = "dbgw")
public interface DbgwClient {

    // Customer Requests
    @Get("/customers/{phone}")
    HttpResponse<Long> findCustomerByPhone(@PathVariable String phone);

    @Get("/customers/{phone}/trips/past")
    HttpResponse<List<Trip>> findPastTripsOfCustomer(@PathVariable String phone);

    @Get("/customers/{phone}/trips/future")
    HttpResponse<List<Trip>> findUpcomingTripsOfCustomer(@PathVariable String phone);

    @Post("/customers")
    HttpResponse<Long> createCustomer(@Valid @Body CreateCustomerRequestDto createCustomerRequestDto);

    // Trip Requests
    @Post("/trips")
    HttpResponse<Trip> createTrip(@Valid @Body CreateTripRequestDto createTripRequestDto);
}
