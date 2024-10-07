package com.payonefare.api.dbgw.trips.dto;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class CompleteTripDto {
    /*
    Final Fare amount got from the driver
     */
    private float amount;

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
