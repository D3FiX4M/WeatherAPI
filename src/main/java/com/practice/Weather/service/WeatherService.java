package com.practice.Weather.service;

import com.practice.Weather.payload.weather.WeatherResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface WeatherService {
    WeatherResponse getWeather(Long id) throws URISyntaxException, IOException, InterruptedException;
}
