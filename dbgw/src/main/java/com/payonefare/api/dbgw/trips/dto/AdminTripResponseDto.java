package com.payonefare.api.dbgw.trips.dto;

import com.payonefare.api.dbgw.trips.data.Trip;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

/*
Description: DTO to return all trips for admin page
 */
@Introspected
@Serdeable
public class AdminTripResponseDto {

    public AdminTripResponseDto(List<Trip> pending, List<Trip> allotted, List<Trip> completed) {
        this.pending = pending;
        this.allotted = allotted;
        this.completed = completed;
    }

    /*
    Trips that are yet to be assigned a driver
     */
    private List<Trip> pending;

    /*
    Trips that are assigned a driver but not completed yet
     */
    private List<Trip> allotted;

    /*
    Trips that are completed
     */
    private List<Trip> completed;

    public List<Trip> getPending() {
        return pending;
    }

    public void setPending(List<Trip> pending) {
        this.pending = pending;
    }

    public List<Trip> getAllotted() {
        return allotted;
    }

    public void setAllotted(List<Trip> allotted) {
        this.allotted = allotted;
    }

    public List<Trip> getCompleted() {
        return completed;
    }

    public void setCompleted(List<Trip> completed) {
        this.completed = completed;
    }
}
