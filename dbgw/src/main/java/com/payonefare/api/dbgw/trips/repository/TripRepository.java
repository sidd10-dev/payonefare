package com.payonefare.api.dbgw.trips.repository;

import com.payonefare.api.dbgw.trips.data.Trip;
import com.payonefare.api.dbgw.trips.enums.Status;
import com.payonefare.api.dbgw.customers.data.Customer;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TripRepository extends CrudRepository<Trip, Long> {
    /*
    Description: Used to query and return corresponding trips with matching status value
    Usage: Used to get completed trips for admin
    Parameters: Status status
    Return Type: List[Trip]
     */
    List<Trip> findByStatus(Status status);

    /*
    Description: Get all the trips with the matching status and pickuptime greater than the time mentioned
    Usage: Used to Get confirmed trips that have not started yet
    Parameters: Trip status, query time
    Return Type: List[Trip]
     */
    List<Trip> findByStatusAndPickupTimeGreaterThan(Status status, LocalDateTime pickupTime);

    /*
    Description: Get all the trips with the matching status and sorted by pickup time
    Usage: Used to Get pending trips that have not been assigned a driver yet
    Parameters: Trip status, query time
    Return Type: List[Trip]
     */
    List<Trip> findByStatusOrderByPickupTimeDesc(Status status);

    /*
    Description: Get all the trips of customer that are yet to start
    Usage: Used to get customer's upcoming trips
    Parameters: Customer object, query time
    Return Type: List[Trip]
     */
//    List<Trip> findByCustomerAndPickupTimeGreaterThan(Long customerId, LocalDateTime pickupTime);

    /*
    Description: Get all the trips of customer that are completed
    Usage: Used to get customer's completed trips
    Parameters: Customer object, query time
    Return Type: List[Trip]
     */
//    List<Trip> findByCustomer_IdAndPickupTimeLessThan(Long customerId, LocalDateTime pickupTime);
}
