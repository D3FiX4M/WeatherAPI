package com.practice.Weather.service;

import com.practice.Weather.payload.location.LocationFilter;
import com.practice.Weather.payload.location.LocationResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface LocationService {

    List<LocationResponse> get(LocationFilter filter) throws IllegalAccessException;
    LocationResponse getById(Long id);
    LocationResponse createByName(String name) throws URISyntaxException, IOException, InterruptedException;
    LocationResponse createByCord(Double latitude, Double longitude) throws IOException, InterruptedException, URISyntaxException;
    String delete(Long id);
}
