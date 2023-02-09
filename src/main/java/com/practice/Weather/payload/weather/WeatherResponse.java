package com.practice.Weather.payload.weather;


import com.practice.Weather.payload.weather.utilityDto.Wind;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse {

    private String weather;
    private String temperature;
    private String feelsLike;
    private String temp_min;
    private String temp_max;
    private String pressure;
    private String humidity;
    private String visibility;
    private Wind wind;
    private String cloud;
    private String snow;
    private String rain;

}
