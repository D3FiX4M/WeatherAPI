package com.practice.Weather.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.Weather.dto.*;
import com.practice.Weather.entity.Location;
import com.practice.Weather.entity.User;
import com.practice.Weather.exception.ExistException;
import com.practice.Weather.exception.NotFoundException;
import com.practice.Weather.mapper.LocationMapper;
import com.practice.Weather.mapper.WeatherMapper;
import com.practice.Weather.repository.LocationRepository;
import com.practice.Weather.repository.UserRepository;
import com.practice.Weather.service.LocationService;
import com.practice.Weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService, WeatherService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final LocationMapper locationMapper;
    private final WeatherMapper weatherMapper;

    private final String API_KEY = "3945bc170fe722a1da3cff2e76de555b";

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if (principal instanceof User) {
            username = ((User) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new NotFoundException("User not found")
                );
    }

    public ApiLocationResponse getApiLocationResponse(HttpRequest request) throws IOException, InterruptedException {

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<ApiLocationResponse> locationResponseList = objectMapper.readValue(response.body(), new TypeReference<>() {
        });

        return locationResponseList.get(0);
    }


    public ApiWeatherResponse getApiWeatherResponse(HttpRequest request) throws IOException, InterruptedException {

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper.readValue(response.body(), ApiWeatherResponse.class);
    }

    @Override
    public List<LocationResponse> getAll() {
        return locationMapper.toDto(locationRepository.findAllByUser(
                getCurrentUser())
        );
    }

    @Override
    public LocationResponse getByName(String name) {

        return locationMapper.toDto(
                locationRepository.findAllByUserAndName(
                        getCurrentUser(),
                        name
                ).orElseThrow(
                        () -> new NotFoundException("Locations not found")
                )
        );
    }

    @Override
    public LocationResponse getByCord(CoordinateRequest request) {

        return locationMapper.toDto(
                locationRepository.findAllByUserAndLatitudeAndLongitude(
                        getCurrentUser(),
                        request.getLatitude(),
                        request.getLongitude()
                ).orElseThrow(
                        () -> new NotFoundException("Location not found")
                )
        );
    }

    @Override
    public WeatherResponse getWeatherByLocationName(NameRequest nameRequest) throws URISyntaxException, IOException, InterruptedException {

        if (!locationRepository.existsByUserAndName(getCurrentUser(), nameRequest.getName())) {
            throw new NotFoundException("Location with name not found");
        }


        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.openweathermap.org/data/2.5/weather?q=" +
                        nameRequest.getName() +
                        "&appid=" + API_KEY +
                        "&units=metric&lang=ru"))
                .GET()
                .build();

        ApiWeatherResponse response = getApiWeatherResponse(request);

        return weatherMapper.ApiResponseToResponse(response);

    }

    @Override
    public WeatherResponse getWeatherByLocationCord(CoordinateRequest coordinateRequest) throws URISyntaxException, IOException, InterruptedException {

        if (!locationRepository.existsByUserAndLatitudeAndLongitude(getCurrentUser(), coordinateRequest.getLatitude(), coordinateRequest.getLongitude())) {
            throw new NotFoundException("Location with coordinates not found");
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.openweathermap.org/data/2.5/weather?lat=" +
                        coordinateRequest.getLatitude() +
                        "&lon=" + coordinateRequest.getLongitude() +
                        "&appid=" + API_KEY +
                        "&units=metric&lang=ru"))
                .GET()
                .build();

        ApiWeatherResponse response = getApiWeatherResponse(request);

        return weatherMapper.ApiResponseToResponse(response);

    }

    @Override
    public LocationResponse createByName(NameRequest nameRequest) throws URISyntaxException, IOException, InterruptedException {
        if (locationRepository.existsByUserAndName(getCurrentUser(), nameRequest.getName())) {
            throw new ExistException("Location with name already exist");
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.openweathermap.org/geo/1.0/direct?q=" + nameRequest.getName() + "&limit=1&appid=" + API_KEY))
                .GET()
                .build();

        ApiLocationResponse locationResponse = getApiLocationResponse(request);

        return locationMapper.toDto(locationRepository.save(
                locationMapper.toEntity(locationResponse, getCurrentUser())
        ));
    }

    @Override
    public LocationResponse createByCord(CoordinateRequest coordinateRequest) throws IOException, InterruptedException, URISyntaxException {
        if (locationRepository.existsByUserAndLatitudeAndLongitude(getCurrentUser(), coordinateRequest.getLatitude(), coordinateRequest.getLongitude())) {
            throw new ExistException("Location with coordinates already exist");
        }


        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.openweathermap.org/geo/1.0/reverse?lat=" +
                        coordinateRequest.getLatitude() +
                        "&lon=" +
                        coordinateRequest.getLongitude() +
                        "&limit=1&appid=" +
                        API_KEY))
                .GET()
                .build();

        ApiLocationResponse locationResponse = getApiLocationResponse(request);

        if (locationRepository.existsByUserAndName(getCurrentUser(), locationResponse.getName())){
            throw new ExistException("Location with name already exist");
        }

        return locationMapper.toDto(locationRepository.save(
                locationMapper.toEntity(locationResponse, getCurrentUser())
        ));
    }


    @Override
    public String deleteByName(String name) {

        Location location = locationRepository.findAllByUserAndName(
                getCurrentUser(),
                name
        ).orElseThrow(
                () -> new NotFoundException("Location not found")
        );

        locationRepository.delete(location);

        return "Location successfully deleted";
    }

    @Override
    public String deleteByCord(CoordinateRequest request) {

        Location location = locationRepository.findAllByUserAndLatitudeAndLongitude(
                getCurrentUser(),
                request.getLatitude(),
                request.getLongitude()
        ).orElseThrow(
                () -> new NotFoundException("Location not found")
        );

        locationRepository.delete(location);

        return "Location successfully deleted";
    }
}
