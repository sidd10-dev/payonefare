package com.payonefare.api.amservice.controller;

import com.payonefare.api.amservice.Service.AdminService;
import com.payonefare.api.common.data.Trip;
import com.payonefare.api.common.dto.AdminTripResponseDto;
import com.payonefare.api.common.dto.AllotDriverDto;
import com.payonefare.api.common.dto.CompleteTripDto;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/trips")
@ExecuteOn(TaskExecutors.BLOCKING)
public class AdminController {

    private final Logger LOG = LoggerFactory.getLogger(AdminController.class);
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * GET endpoint to get trips for all admin
     * @return
     */
    @Get("/admin")
    public HttpResponse<AdminTripResponseDto> getAllTripsForAdmin() {
        LOG.debug("IN : AdminController::getAllTripsForAdmin");
        AdminTripResponseDto responseBody = adminService.getAdminTrips();
        LOG.debug("OUT : AdminController::getAllTripsForAdmin");
        return HttpResponse.ok(responseBody);
    }

    /**
     * PUT endpoint to assign a driver to trip
     * @param id
     * @param allotDriverDto
     * @return
     */
    @Put("/{id}")
    public HttpResponse<Long> assignDriverToTrip(@PathVariable Long id, @Body @Valid AllotDriverDto allotDriverDto) {
        LOG.debug("IN : AdminController::assignDriverToTrip");
        Trip updatedTrip = adminService.assignDriverToTrip(id, allotDriverDto);
        LOG.debug("OUT : AdminController::assignDriverToTrip");
        return HttpResponse.ok(updatedTrip.getId());
    }

    /**
     * PUT endpoint to mark a trip as complete
     * @param id
     * @param completeTripDto
     * @return
     */
    @Put("/complete/{id}")
    public HttpResponse<Long> markTripAsCompleted(@PathVariable Long id, @Body @Valid CompleteTripDto completeTripDto) {
        LOG.debug("IN : AdminController::markTripAsCompleted");
        Trip completedTrip = adminService.markTripAsComplete(id, completeTripDto);
        LOG.debug("OUT : AdminController::markTripAsCompleted");
        return HttpResponse.ok(completedTrip.getId());
    }
}
