package com.practice.Weather.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse {

    private String name;
    private String country;
    private double latitude;
    private double longitude;
}
