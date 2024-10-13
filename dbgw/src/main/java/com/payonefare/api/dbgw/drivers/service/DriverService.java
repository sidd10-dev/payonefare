package com.payonefare.api.dbgw.drivers.service;

import com.payonefare.api.dbgw.consts.CommonConsts;
import com.payonefare.api.dbgw.drivers.data.Driver;
import com.payonefare.api.dbgw.drivers.dto.AllotDriverDto;
import com.payonefare.api.dbgw.drivers.dto.CreateDriverDto;
import com.payonefare.api.dbgw.drivers.repository.DriverRepository;
import com.payonefare.api.dbgw.trips.data.Trip;
import com.payonefare.api.dbgw.trips.enums.Status;
import com.payonefare.api.dbgw.trips.repository.TripRepository;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Singleton
public class DriverService {
    private final DriverRepository driverRepository;
    private final ModelMapper modelMapper;
    private final TripRepository tripRepository;

    private final Logger LOG = LoggerFactory.getLogger(DriverService.class);

    public DriverService(DriverRepository driverRepository, ModelMapper modelMapper, TripRepository tripRepository) {
        LOG.info("Initialising DriverService");

        this.driverRepository = driverRepository;
        this.modelMapper = modelMapper;
        this.tripRepository = tripRepository;

        LOG.info("Initialised DriverService");
    }

    /**
     * Service function to search driver with the given mobile number
     * @param phone
     * @return Driver
     */
    public Driver getDriverByPhone(String phone) {
        try {
            LOG.debug("IN: DriverService::getDriverByPhone");

            Optional<Driver> optionalDriver = driverRepository.findByPhone(phone);
            if (optionalDriver.isEmpty()) {
                throw new RuntimeException(CommonConsts.DRIVER_NOT_FOUND);
            }

            LOG.debug("Found Driver with Phone {}", optionalDriver.get().getPhone());
            LOG.debug("OUT: DriverService::getDriverByPhone");
            return optionalDriver.get();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Service function to create new driver
     * @param createDriverDto
     * @return Driver
     */
    public Driver createDriver(CreateDriverDto createDriverDto) {
        try {
            LOG.debug("IN: DriverService::createDriver");

            boolean isDuplicateDriver = driverRepository.findByPhone(createDriverDto.getPhone()).isPresent();
            if (isDuplicateDriver) {
                throw new RuntimeException(CommonConsts.DRIVER_FOUND);
            }
            Driver driver = modelMapper.map(createDriverDto, Driver.class);

            LOG.debug("Created New Driver Object {}", driver);
            LOG.debug("OUT: DriverService::createDriver");
            return driverRepository.save(driver);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Allot a particular driver to a trip
     * @param allotDriverDto
     * @return Trip
     */
    public Trip assignDriverToTrip(Long tripId, AllotDriverDto allotDriverDto) {
        try {
            LOG.debug("IN: DriverService::assignDriverToTrip");

            // Get Trip Object
            Optional<Trip> optionalTrip = tripRepository.findById(tripId);
            if (optionalTrip.isEmpty()) {
                throw new RuntimeException(CommonConsts.INVALID_TRIP_ID);
            }
            Trip trip = optionalTrip.get();
            LOG.debug("Got Trip: {}", trip);

            // Get Driver Object
            Optional<Driver> optionalDriver = driverRepository.findById(allotDriverDto.getDriverId());
            if (optionalDriver.isEmpty()) {
                throw new RuntimeException(CommonConsts.DRIVER_NOT_FOUND);
            }
            Driver driver = optionalDriver.get();
            LOG.debug("Got Driver: {}", driver);

            // Assign Driver to Trip
            trip.setDriver(driver);
            trip.setVehicleNo(allotDriverDto.getVehicleNo());
            trip.setStatus(Status.CONFIRMED);

            LOG.debug("Driver {} assigned to Trip {}", driver.getId(), trip.getId());
            LOG.debug("OUT: DriverService::assignDriverToTrip");
            return tripRepository.update(trip);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
