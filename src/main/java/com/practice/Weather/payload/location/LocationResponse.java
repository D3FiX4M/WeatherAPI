package com.practice.Weather.payload.location;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse {

    private Long id;
    private String name;
    private String country;
    private double latitude;
    private double longitude;
}
