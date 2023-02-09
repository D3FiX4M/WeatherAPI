package com.practice.Weather.payload.weather;

import com.practice.Weather.payload.weather.utilityDto.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiWeatherResponse {

    private List<Weather> weather;
    private Main main;
    private Long visibility;
    private Wind wind;
    private Clouds clouds;
    private Snow snow;
    private Rain rain;

}
