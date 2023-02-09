package com.practice.Weather.domain.mapper;

import com.practice.Weather.payload.weather.ApiWeatherResponse;
import com.practice.Weather.payload.weather.WeatherResponse;
import com.practice.Weather.payload.weather.utilityDto.Wind;
import org.springframework.stereotype.Component;

@Component
public class WeatherMapper {

    public WeatherResponse ApiResponseToResponse(ApiWeatherResponse apiResponse){

        WeatherResponse weatherResponse = new WeatherResponse();

        weatherResponse.setWeather(apiResponse.getWeather().get(0).getDescription());
        weatherResponse.setTemperature(apiResponse.getMain().getTemp()+" °C");
        weatherResponse.setFeelsLike(apiResponse.getMain().getFeels_like()+" °C");
        weatherResponse.setTemp_min(apiResponse.getMain().getTemp_min()+" °C");
        weatherResponse.setTemp_max(apiResponse.getMain().getTemp_max()+" °C");
        weatherResponse.setHumidity(apiResponse.getMain().getHumidity()+" %");
        weatherResponse.setPressure(apiResponse.getMain().getPressure()+" гПа");
        weatherResponse.setVisibility(apiResponse.getVisibility()+" М");

        weatherResponse.setWind(
                new Wind(
                        apiResponse.getWind().getSpeed()+" м/с",
                        apiResponse.getWind().getDeg()+" °"
                ));


        weatherResponse.setCloud(apiResponse.getClouds().getAll()+" %");

        if (apiResponse.getSnow()!=null){

            weatherResponse.setSnow(apiResponse.getSnow().getH1()+" мм");
        } else {
            weatherResponse.setSnow("Отсутствует");
        }
        if (apiResponse.getRain()!=null){
            weatherResponse.setRain(apiResponse.getRain().getH1()+" мм");
        } else {
            weatherResponse.setRain("Отсутствует");
        }
        return weatherResponse;


    }

}
