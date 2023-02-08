package com.practice.Weather.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.Weather.dto.*;
import com.practice.Weather.service.LocationService;
import com.practice.Weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;
    private final WeatherService weatherService;

    @GetMapping
    public ResponseEntity<List<LocationResponse>> getAll() {
        return ResponseEntity.ok(locationService.getAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<LocationResponse> getByName(
            @PathVariable String name
    ) {
        return ResponseEntity.ok(locationService.getByName(name));
    }

    @GetMapping("/{latitude}_{longitude}")
    public ResponseEntity<LocationResponse> getByCord(
            @PathVariable double latitude,
            @PathVariable double longitude
    ) {
        return ResponseEntity.ok(locationService.getByCord(new CoordinateRequest(latitude, longitude)));
    }

    @GetMapping("/{latitude}_{longitude}/weather")
    public ResponseEntity<WeatherResponse> getWeatherByLocationCord(
            @PathVariable double latitude,
            @PathVariable double longitude
    ) throws URISyntaxException, IOException, InterruptedException {
        return ResponseEntity.ok(weatherService.getWeatherByLocationCord(new CoordinateRequest(latitude, longitude)));
    }

    @GetMapping("/{name}/weather")
    public ResponseEntity<WeatherResponse> getWeatherByLocationName(
            @PathVariable String name
    ) throws URISyntaxException, IOException, InterruptedException {
        return ResponseEntity.ok(weatherService.getWeatherByLocationName(new NameRequest(name)));
    }


    @PostMapping(headers = "action=coordinates")
    public ResponseEntity<LocationResponse> createByCoordinates(
            @RequestBody CoordinateRequest request
    ) throws IOException, URISyntaxException, InterruptedException {
        return ResponseEntity.ok(locationService.createByCord(request));
    }

    @PostMapping(headers = "action=name")
    public ResponseEntity<LocationResponse> createByName(
            @RequestBody NameRequest request
    ) throws URISyntaxException, IOException, InterruptedException {
        return ResponseEntity.ok(locationService.createByName(request));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteByName(
            @PathVariable String name
    ) {
        return ResponseEntity.ok(locationService.deleteByName(name));
    }

    @DeleteMapping("/{latitude}_{longitude}")
    public ResponseEntity<String> deleteByCord(
            @PathVariable double latitude,
            @PathVariable double longitude
    ) {
        return ResponseEntity.ok(locationService.deleteByCord(new CoordinateRequest(latitude, longitude)));
    }


    @GetMapping("/test")
    public ResponseEntity<ApiWeatherResponse> check() throws URISyntaxException, IOException, InterruptedException {

        String name = "Moscow";
        String key = "3945bc170fe722a1da3cff2e76de555b";

        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + name + "&appid=" + key + "&units=metric" + "&lang=ru";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();


        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ApiWeatherResponse check = objectMapper.readValue(response.body(), ApiWeatherResponse.class);


        return ResponseEntity.ok(check);
    }
}

