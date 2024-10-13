package com.payonefare.api.dbgw.drivers.repository;

import com.payonefare.api.dbgw.drivers.data.Driver;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface DriverRepository extends CrudRepository<Driver, Long> {
    Optional<Driver> findByPhone(String phone);
}
