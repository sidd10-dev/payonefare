package com.payonefare.api.dbgw.drivers.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.payonefare.api.dbgw.trips.data.Trip;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

/*
Name: Driver Entity
Description: This Entity is used to store Drivers Data
*/
@Serdeable
@Entity
public class Driver {
    /*
    Description: Primary Key - ID
    Type: Long
    */
    @Id
    @GeneratedValue
    private Long id;

    /*
    Description: Driver Name
    Type: String
     */
    @Column
    @Size(min=2)
    private String name;

    /*
    Description: Driver Phone
    Type: String
     */
    @Column
    @Pattern(regexp="(^$|[0-9]{10})")
    private String driverPhone;

    /*
    Description: Trips taken by the driver
    Type: List[Trip]
     */
    @JsonIgnore
    @OneToMany(mappedBy = "driver")
    private List<Trip> trips = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Size(min = 2) String getName() {
        return name;
    }

    public void setName(@Size(min = 2) String name) {
        this.name = name;
    }

    public @Pattern(regexp = "(^$|[0-9]{10})") String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(@Pattern(regexp = "(^$|[0-9]{10})") String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", driverPhone='" + driverPhone + '\'' +
                '}';
    }
}
