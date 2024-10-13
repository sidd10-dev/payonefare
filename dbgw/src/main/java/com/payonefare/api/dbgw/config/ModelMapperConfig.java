package com.payonefare.api.dbgw.config;

import com.payonefare.api.dbgw.trips.data.Trip;
import com.payonefare.api.dbgw.trips.dto.CreateTripDto;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Property;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

/**
 * Model Mapper used to MAP Entity to DTO and vice-versa
 */
@Factory
public class ModelMapperConfig {
    @Singleton
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }
}
