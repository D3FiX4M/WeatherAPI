package com.practice.Weather.payload.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationFilter {
    private Long id;
    private String name;
    private String country;
    private Double latitude;
    private Double longitude;

}
