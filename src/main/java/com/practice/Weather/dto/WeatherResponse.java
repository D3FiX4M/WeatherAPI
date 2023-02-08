package com.practice.Weather.dto;


import com.practice.Weather.dto.utilityDto.Coordinates;
import com.practice.Weather.dto.utilityDto.Wind;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse {
//    private Coordinates coordinates;
//    private String name;
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
