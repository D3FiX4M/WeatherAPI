package com.practice.Weather.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.practice.Weather.dto.utilityDto.*;
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

//    @JsonSetter("coord")
//    private Coordinates coordinates;
    private List<Weather> weather;
    private Main main;
    private Long visibility;
    private Wind wind;
    private Clouds clouds;
    private Snow snow;
    private Rain rain;
//    private String name;

}
