package com.payonefare.api.dbgw.drivers.dto;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * DTO to allot a driver to a trip
 */
@Serdeable
public class AllotDriverDto {
    @NotEmpty
    private Long driverId;

    @Size(min=4)
    private String vehicleNo;

    public @NotEmpty Long getDriverId() {
        return driverId;
    }

    public void setDriverId(@NotEmpty Long driverId) {
        this.driverId = driverId;
    }

    public @Size(min = 4) String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(@Size(min = 4) String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }
}
