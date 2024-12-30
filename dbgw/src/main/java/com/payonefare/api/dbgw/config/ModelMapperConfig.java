package com.payonefare.api.dbgw.config;

import io.micronaut.context.annotation.Factory;
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
