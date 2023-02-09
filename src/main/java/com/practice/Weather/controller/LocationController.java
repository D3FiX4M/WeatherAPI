package com.practice.Weather.controller;

import com.practice.Weather.payload.weather.WeatherResponse;
import com.practice.Weather.payload.location.LocationFilter;
import com.practice.Weather.payload.location.LocationResponse;
import com.practice.Weather.service.LocationService;
import com.practice.Weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;
    private final WeatherService weatherService;

    @GetMapping
    public List<LocationResponse> get(LocationFilter filter) throws IllegalAccessException {
        return locationService.get(filter);
    }

    @GetMapping("/{id}")
    public LocationResponse getById(
            @PathVariable Long id
    ) {
        return locationService.getById(id);
    }

    @GetMapping("/{id}/weather")
    public WeatherResponse getWeather(
            @PathVariable Long id
    ) throws URISyntaxException, IOException, InterruptedException {
        return weatherService.getWeather(id);
    }

    @PostMapping(headers = "action=coordinates")
    public LocationResponse createByCoordinates(
            Double latitude,
            Double longitude
    ) throws IOException, URISyntaxException, InterruptedException {
        return locationService.createByCord(latitude, longitude);
    }

    @PostMapping(headers = "action=name")
    public LocationResponse createByName(
            String name
    ) throws URISyntaxException, IOException, InterruptedException {
        return locationService.createByName(name);
    }

    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable Long id
    ) {
        return locationService.delete(id);
    }


}

