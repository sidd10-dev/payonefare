package com.payonefare.api.common.dto;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO to allot a driver to a trip
 */
@Serdeable
public class AllotDriverDto {
    @NotNull
    @Pattern(regexp="(^$|[0-9]{10})")
    private String phone;

    @NotNull
    @Size(min=2)
    private String name;

    @Size(min=4)
    private String vehicleNo;

    public @NotNull @Pattern(regexp = "(^$|[0-9]{10})") String getPhone() {
        return phone;
    }

    public void setPhone(@NotNull @Pattern(regexp = "(^$|[0-9]{10})") String phone) {
        this.phone = phone;
    }

    public @NotNull @Size(min = 2) String getName() {
        return name;
    }

    public void setName(@NotNull @Size(min = 2) String name) {
        this.name = name;
    }

    public @Size(min = 4) String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(@Size(min = 4) String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }
}
