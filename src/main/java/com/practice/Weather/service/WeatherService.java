package com.practice.Weather.service;

import com.practice.Weather.dto.CoordinateRequest;
import com.practice.Weather.dto.NameRequest;
import com.practice.Weather.dto.WeatherResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface WeatherService {
    WeatherResponse getWeatherByLocationName(NameRequest request) throws URISyntaxException, IOException, InterruptedException;
    WeatherResponse getWeatherByLocationCord(CoordinateRequest request) throws URISyntaxException, IOException, InterruptedException;
}
