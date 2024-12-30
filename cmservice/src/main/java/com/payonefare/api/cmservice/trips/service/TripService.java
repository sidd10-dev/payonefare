package com.payonefare.api.cmservice.trips.service;

import com.payonefare.api.cmservice.clients.DbgwClient;
import io.micronaut.http.HttpResponse;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class TripService {

    private final Logger LOG = LoggerFactory.getLogger(TripService.class);

    private final DbgwClient httpClient;

    public TripService(DbgwClient httpClient) {
        this.httpClient = httpClient;
    }

    public HttpResponse<Trip>
}
