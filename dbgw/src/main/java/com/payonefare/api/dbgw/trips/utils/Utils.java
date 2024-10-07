package com.payonefare.api.dbgw.trips.utils;

import jakarta.inject.Singleton;

import java.net.URI;

@Singleton
public class Utils {
    /*
    Function to return an object location
     */
    public String location(Long id) {
        return URI.create("/trips/" + id).getPath();
    }
}
