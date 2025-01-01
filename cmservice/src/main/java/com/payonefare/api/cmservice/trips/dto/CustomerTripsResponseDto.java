package com.payonefare.api.cmservice.trips.dto;

import com.payonefare.api.common.data.Trip;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

@Serdeable
public class CustomerTripsResponseDto {
    private List<Trip> past;
    private List<Trip> upcoming;

    public CustomerTripsResponseDto(List<Trip> past, List<Trip> upcoming) {
        this.past = past;
        this.upcoming = upcoming;
    }

    public List<Trip> getUpcoming() {
        return upcoming;
    }

    public void setUpcoming(List<Trip> upcoming) {
        this.upcoming = upcoming;
    }

    public List<Trip> getPast() {
        return past;
    }

    public void setPast(List<Trip> past) {
        this.past = past;
    }
}
