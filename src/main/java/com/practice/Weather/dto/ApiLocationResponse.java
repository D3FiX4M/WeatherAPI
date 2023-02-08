package com.practice.Weather.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiLocationResponse {
    private String name;
    @JsonSetter("lat")
    private double latitude;
    @JsonSetter("lon")
    private double longitude;
    private String country;
}
