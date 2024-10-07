package com.payonefare.api.dbgw.trips.controller;

import com.payonefare.api.dbgw.trips.data.Trip;
import com.payonefare.api.dbgw.trips.dto.AdminTripResponseDto;
import com.payonefare.api.dbgw.trips.dto.CompleteTripDto;
import com.payonefare.api.dbgw.trips.enums.Status;
import com.payonefare.api.dbgw.trips.repository.TripRepository;
import com.payonefare.api.dbgw.trips.utils.Utils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.validation.Valid;

import java.net.URI;
import java.time.LocalDateTime;

import static io.micronaut.http.HttpHeaders.LOCATION;

@Controller(value="/trips")
public class TripController {
    /*
    TripRepository Instance
     */
    private TripRepository tripRepository;

    /*
    Utils Instance
     */
    private Utils utils;

    public TripController(TripRepository tripRepository, Utils utils) {
        this.tripRepository = tripRepository;
        this.utils = utils;
    }

    /*
    Request Type: POST
    Request Details: Create a New Trip
     */
    @Post
    public HttpResponse<Trip> createTrip(@Body @Valid Trip trip) {
        try {
            Trip savedTrip = tripRepository.save(trip);
            return HttpResponse
                    .created(savedTrip)
                    .header(LOCATION, utils.location(savedTrip.getId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
    Request Type: PUT
    Request Details: Mark a trip as complete
     */
    @Put("/complete/{id}")
    public HttpResponse<?> completeTrip(@PathVariable Long id, @Body @Valid CompleteTripDto completeTripDto) {
        try {
            Trip trip = tripRepository.findById(id).orElseThrow(() -> new RuntimeException("Invalid trip id"));
            trip.setStatus(Status.COMPLETED);
            trip.setAmount(completeTripDto.getAmount());
            tripRepository.save(trip);
            return HttpResponse
                    .noContent()
                    .header(LOCATION, utils.location(trip.getId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
    Request Type: GET
    Request Details: Get all trips for admin
     */
    @Get("/admin")
    public AdminTripResponseDto getAdminTrip() {
        return new AdminTripResponseDto(
                /*
                Pending Trips sorted by descending Order
                 */
                tripRepository.findByStatusOrderByPickupTimeDesc(Status.PENDING),
                /*
                Driver allotted trips that are yet to start
                 */
                tripRepository.findByStatusAndPickupTimeGreaterThan(Status.CONFIRMED, LocalDateTime.now()),
                /*
                All completed trips
                 */
                tripRepository.findByStatus(Status.COMPLETED)
        );
    }
}
