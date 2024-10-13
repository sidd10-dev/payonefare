package com.payonefare.api.dbgw.trips.repository;

import com.payonefare.api.dbgw.trips.data.Trip;
import com.payonefare.api.dbgw.trips.enums.Status;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TripRepository extends CrudRepository<Trip, Long> {
    List<Trip> findByStatus(Status status);
    List<Trip> findByStatusAndPickupTimeGreaterThan(Status status, LocalDateTime pickupTime);
    List<Trip> findByStatusOrderByPickupTimeDesc(Status status);
    List<Trip> findByCustomerIdAndPickupTimeGreaterThan(Long customerId, LocalDateTime pickupTime);
    List<Trip> findByCustomerIdAndPickupTimeLessThan(Long customerId, LocalDateTime pickupTime);
}
