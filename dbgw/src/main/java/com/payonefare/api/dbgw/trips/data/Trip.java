package com.payonefare.api.dbgw.trips.data;

import com.payonefare.api.dbgw.drivers.data.Driver;
import com.payonefare.api.dbgw.trips.enums.CarType;
import com.payonefare.api.dbgw.trips.enums.Status;
import com.payonefare.api.dbgw.trips.enums.TripType;
import com.payonefare.api.dbgw.users.data.Customer;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/*
Name: TripEntity
Description: This Entity is used to store Trip data
 */
@Serdeable
@Entity
public class Trip {
    /*
    Description: Primary Key - ID
    Type: Long
     */
    @Id
    @GeneratedValue
    private Long id;

    /*
    Description: Pickup Address of the trip
    Type: String
     */
    @Size(min=5, max=1000)
    @Column
    private String pickupAddress;

    /*
    Description: Destination Address of the trip
    Type: String
     */
    @Size(min=5, max=1000)
    @Column
    private String destinationAddress;

    /*
    Description: Trip Type
    Type: TripType
    Possible Values: ONEWAY, TRIPTYPE
     */
    @Column
    @Enumerated(EnumType.STRING)
    private TripType type;

    /*
    Description: Pickup time which is to be in future to the time of booking
    Type: LocalDateTime
     */
    @Future
    @Column
    private LocalDateTime pickupTime;

    /*
    Description: Type of the car desired by the customer
    Type: CarType
    Possible values: SUV, SEDAN, ETIOS, INNOVA
     */
    @Enumerated(EnumType.STRING)
    @Column
    private CarType car;

    /*
    Description: Vehicle Number, Populated once the trip is confirmed
    Type: String
     */
    @Size(min=4)
    @Column(nullable = true)
    private String vehicleNo;

    /*
    Description: Total fare for the trip
    Type: float
     */
    @Column(nullable = true)
    private float amount;

    /*
    Description: Used to mark the status of the trip
    Type: Stats
    PossibleValues: PENDING, CONFIRMED, COMPLETED
     */
    @Enumerated(EnumType.STRING)
    @Column
    private Status status;

    /*
    Description: User details of the customer
    Type: User
     */
    @ManyToOne
    private Customer customer;

    /*
    Description: Driver details for the trip, populated by admin
    Type: Driver
     */
    @Nullable
    @ManyToOne
    private Driver driver;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Size(min = 5, max = 1000) String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(@Size(min = 5, max = 1000) String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public @Size(min = 5, max = 1000) String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(@Size(min = 5, max = 1000) String to) {
        this.destinationAddress = to;
    }

    public TripType getType() {
        return type;
    }

    public void setType(TripType type) {
        this.type = type;
    }

    public @Future LocalDateTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(@Future LocalDateTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public CarType getCar() {
        return car;
    }

    public void setCar(CarType car) {
        this.car = car;
    }

    public @Size(min = 4) String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(@Size(min = 4) String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Customer getUser() {
        return customer;
    }

    public void setUser(Customer customer) {
        this.customer = customer;
    }

    @Nullable
    public Driver getDriver() {
        return driver;
    }

    public void setDriver(@Nullable Driver driver) {
        this.driver = driver;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", pickupAddress='" + pickupAddress + '\'' +
                ", destinationAddress='" + destinationAddress + '\'' +
                ", type=" + type +
                ", pickupTime=" + pickupTime +
                ", car=" + car +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", amount=" + amount +
                ", status=" + status +
                ", customer=" + customer +
                ", driver=" + driver +
                '}';
    }
}
