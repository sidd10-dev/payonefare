package com.payonefare.api.dbgw.utils;

import jakarta.inject.Singleton;

import java.net.URI;

@Singleton
public class Utils {
    /*
    Function to return an object location
     */
    public String location(Long id, String entityName) {
        return URI.create("/" + entityName + "/" + id).getPath();
    }
}
