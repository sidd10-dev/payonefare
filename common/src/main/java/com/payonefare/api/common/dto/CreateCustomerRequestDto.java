package com.payonefare.api.common.dto;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO used to create a new customer
 */
@Serdeable
public class CreateCustomerRequestDto {
    @Size(min=2)
    public String name;

    @Pattern(regexp="(^$|[0-9]{10})")
    public String phone;

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
