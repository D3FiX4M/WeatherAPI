package com.practice.Weather.service;

import com.practice.Weather.dto.CoordinateRequest;
import com.practice.Weather.dto.LocationResponse;
import com.practice.Weather.dto.NameRequest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface LocationService {

    List<LocationResponse> getAll();
    LocationResponse getByName(String name);
    LocationResponse getByCord(CoordinateRequest request);
    LocationResponse createByName(NameRequest request) throws URISyntaxException, IOException, InterruptedException;
    LocationResponse createByCord(CoordinateRequest request) throws IOException, InterruptedException, URISyntaxException;
    String deleteByName(String name);
    String deleteByCord(CoordinateRequest request);
}
