package com.payonefare.api.dbgw.trips.dto;

import com.payonefare.api.dbgw.trips.enums.CarType;
import com.payonefare.api.dbgw.trips.enums.TripType;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * DTO used for Trip creation
 */
@Serdeable
public class CreateTripDto {

    @Size(min=5, max=1000)
    private String pickupAddress;

    @Size(min=5, max=1000)
    private String destinationAddress;

    @Enumerated(EnumType.STRING)
    private TripType type;

    @Future
    private LocalDateTime pickupTime;

    @Enumerated(EnumType.STRING)
    private CarType car;

    @NotEmpty
    private Long customerId;

    public @Size(min = 5, max = 1000) String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(@Size(min = 5, max = 1000) String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public @Size(min = 5, max = 1000) String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(@Size(min = 5, max = 1000) String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public TripType getType() {
        return type;
    }

    public void setType(TripType type) {
        this.type = type;
    }

    public @Future LocalDateTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(@Future LocalDateTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public CarType getCar() {
        return car;
    }

    public void setCar(CarType car) {
        this.car = car;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
