package com.payonefare.api.dbgw.drivers.controller;

import com.payonefare.api.common.data.Driver;
import com.payonefare.api.common.data.Trip;
import com.payonefare.api.common.dto.AllotDriverDto;
import com.payonefare.api.common.dto.CreateDriverDto;
import com.payonefare.api.dbgw.drivers.service.DriverService;
import com.payonefare.api.dbgw.utils.Utils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import static io.micronaut.http.HttpHeaders.LOCATION;

@Controller("/drivers")
public class DriverController {

    private final Utils utils;
    private final DriverService driverService;
    private final Logger LOG = LoggerFactory.getLogger(DriverController.class);

    public DriverController(Utils utils, DriverService driverService) {
        LOG.info("Initialising DriverController");
        this.utils = utils;
        this.driverService = driverService;
        LOG.info("DriverController initialized");
    }

    /**
     * GET Request to find driver by his phone number
     * @param phone
     * @return HttpResponse with Driver Object
     */
    @Get("/{phone}")
    public HttpResponse<Driver> getDriverByPhone(@PathVariable String phone) {

        LOG.debug("REQUEST: Get Driver With Phone {}", phone);
        Driver driver = driverService.getDriverByPhone(phone);
        LOG.debug("RESPONSE: Returning Driver with Phone {}", phone);

        return HttpResponse.
                ok(driver)
                .header(LOCATION, utils.location(driver.getId(), "drivers"));

    }

    /**
     * POST Request to create a new driver
     * @param createDriverDto
     * @return HttpResponse with Driver objetc
     */
    @Post
    public HttpResponse<Driver> createNewDriver(@Body @Valid CreateDriverDto createDriverDto) {

        LOG.debug("REQUEST: Create New Driver");
        Driver driver = driverService.createDriver(createDriverDto);
        LOG.debug("RESPONSE: Returning New Driver with ID {}", driver.getId());

        return HttpResponse
                .created(driver)
                .header(LOCATION, utils.location(driver.getId(), "drivers"));

    }

    /**
     * PUT request to allot a driver to a trip
     * @param tripId
     * @param allotDriverDto
     * @return HttpResponse with Trip Object
     */
    @Put("/assign/{tripId}")
    public HttpResponse<Trip> assignDriverToTrip(@PathVariable Long tripId, @Body @Valid AllotDriverDto allotDriverDto) {

        LOG.debug("REQUEST: Assign Driver {} to Trip {}", allotDriverDto.getName(), tripId);
        Trip trip = driverService.assignDriverToTrip(tripId, allotDriverDto);
        LOG.debug("RESPONSE: Returning Updated Trip Details");

        return HttpResponse
                .ok(trip)
                .header(LOCATION, utils.location(trip.getId(), "trips"));
        
    }
}
