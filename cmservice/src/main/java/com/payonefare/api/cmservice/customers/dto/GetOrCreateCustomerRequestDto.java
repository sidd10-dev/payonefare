package com.payonefare.api.cmservice.customers.dto;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Serdeable
public class GetOrCreateCustomerRequestDto {
    @NotNull
    private String name;

    @NotNull
    @Pattern(regexp="(^$|[0-9]{10})")
    private String phone;

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @NotNull @Pattern(regexp = "(^$|[0-9]{10})") String getPhone() {
        return phone;
    }

    public void setPhone(@NotNull @Pattern(regexp = "(^$|[0-9]{10})") String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "GetOrCreateCustomerRequestDto{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
