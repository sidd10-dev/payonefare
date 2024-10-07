package com.payonefare.api.dbgw.customers.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.payonefare.api.dbgw.trips.data.Trip;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

/*
Name: User Entity
Description: This Entity is used to store Customer Data
*/
@Serdeable
@Entity
public class Customer {
    /*
    Description: Primary Key - ID
    Type: Long
    */
    @Id
    @GeneratedValue
    private Long id;

    /*
    Description: Name of the user
    Type: String
    */
    @Column
    @Size(min=2)
    private String name;

    /*
    Description: User mobile number
    Type: String
    */
    @Pattern(regexp="(^$|[0-9]{10})")
    @Column(unique = true)
    private String phone;

    /*
    Description: List of all trips taken by the user
    Type: List[Trip]
    */
    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Trip> trips = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public @Size(min = 2) String getName() {
        return name;
    }

    public @Pattern(regexp = "(^$|[0-9]{10})") String getPhone() {
        return phone;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(@Size(min = 2) String name) {
        this.name = name;
    }

    public void setPhone(@Pattern(regexp = "(^$|[0-9]{10})") String phone) {
        this.phone = phone;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
