package com.practice.Weather.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.Weather.domain.spec.SpecFilter;
import com.practice.Weather.domain.entity.Location;
import com.practice.Weather.domain.entity.User;
import com.practice.Weather.domain.spec.LocationSpec;
import com.practice.Weather.domain.spec.UserSpec;
import com.practice.Weather.exception.ExistException;
import com.practice.Weather.exception.NotFoundException;
import com.practice.Weather.domain.mapper.LocationMapper;
import com.practice.Weather.domain.mapper.WeatherMapper;
import com.practice.Weather.domain.repository.LocationRepository;
import com.practice.Weather.domain.repository.UserRepository;
import com.practice.Weather.payload.location.ApiLocationResponse;
import com.practice.Weather.payload.location.LocationDto;
import com.practice.Weather.payload.location.LocationFilter;
import com.practice.Weather.payload.location.LocationResponse;
import com.practice.Weather.payload.weather.ApiWeatherResponse;
import com.practice.Weather.payload.weather.WeatherResponse;
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
public class ApiServiceImpl implements LocationService, WeatherService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final LocationMapper locationMapper;
    private final WeatherMapper weatherMapper;
    private final SpecFilter<Location, LocationDto> specFilter;
    private final String API_KEY = "3945bc170fe722a1da3cff2e76de555b";

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if (principal instanceof User) {
            username = ((User) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return userRepository.findOne(UserSpec.usernameEq(username))
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

    public List<LocationResponse> get(LocationFilter filter) throws IllegalAccessException {

        LocationDto dto = locationMapper.toDto(filter, getCurrentUser());

        return locationMapper.toDto(locationRepository.findAll(specFilter.getSpecFromFilter(dto)));

    }

    @Override
    public LocationResponse getById(Long id) {
        return locationMapper.toDto(
                locationRepository.findById(id)
                        .orElseThrow(
                                () -> new NotFoundException("Location not found")
                        )
        );
    }

    @Override
    public WeatherResponse getWeather(Long id) throws URISyntaxException, IOException, InterruptedException {

        Location location = locationRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Location not found")
                );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.openweathermap.org/data/2.5/weather?lat=" +
                        location.getLatitude() +
                        "&lon=" + location.getLongitude() +
                        "&appid=" + API_KEY +
                        "&units=metric&lang=ru"))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return weatherMapper.ApiResponseToResponse(objectMapper.readValue(response.body(), ApiWeatherResponse.class));

    }

    @Override
    public LocationResponse createByName(String name) throws URISyntaxException, IOException, InterruptedException {
        if (locationRepository.exists(
                LocationSpec.userEq(getCurrentUser())
                        .and(LocationSpec.nameEq(name)))
        ) {
            throw new ExistException("Location with name already exist");
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.openweathermap.org/geo/1.0/direct?q=" +
                        name + "&limit=1&appid=" + API_KEY))
                .GET()
                .build();

        ApiLocationResponse locationResponse = getApiLocationResponse(request);

        return locationMapper.toDto(locationRepository.save(
                locationMapper.toEntity(locationResponse, getCurrentUser())
        ));
    }

    @Override
    public LocationResponse createByCord(Double latitude, Double longitude) throws IOException, InterruptedException, URISyntaxException {
        if (locationRepository.exists(
                LocationSpec.userEq(getCurrentUser())
                        .and(LocationSpec.latitudeEq(latitude))
                        .and(LocationSpec.longitudeEq(longitude))
        )
        ) {
            throw new ExistException("Location with coordinates already exist");
        }


        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.openweathermap.org/geo/1.0/reverse?lat=" +
                        latitude + "&lon=" + longitude +
                        "&limit=1&appid=" + API_KEY))
                .GET()
                .build();

        ApiLocationResponse locationResponse = getApiLocationResponse(request);

        if (locationRepository.exists(
                LocationSpec.userEq(getCurrentUser())
                        .and(LocationSpec.nameEq(locationResponse.getName()))
        )
        ) {
            throw new ExistException("Location already exist");
        }

        return locationMapper.toDto(locationRepository.save(
                locationMapper.toEntity(locationResponse, getCurrentUser())
        ));
    }


    @Override
    public String delete(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Location not found")
                );

        locationRepository.delete(location);

        return "Location successfully deleted";
    }


}
