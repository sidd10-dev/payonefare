package com.payonefare.api.dbgw.drivers.dto;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO to create a new driver
 */
@Serdeable
public class CreateDriverDto {
    @Column
    @Size(min=2)
    private String name;

    @Column
    @Pattern(regexp="(^$|[0-9]{10})")
    private String phone;

    public @Size(min = 2) String getName() {
        return name;
    }

    public void setName(@Size(min = 2) String name) {
        this.name = name;
    }

    public @Pattern(regexp = "(^$|[0-9]{10})") String getPhone() {
        return phone;
    }

    public void setPhone(@Pattern(regexp = "(^$|[0-9]{10})") String phone) {
        this.phone = phone;
    }
}
