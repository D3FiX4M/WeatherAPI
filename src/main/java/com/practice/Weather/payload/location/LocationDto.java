package com.practice.Weather.payload.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.practice.Weather.domain.entity.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDto {
    private Long id;
    private String name;
    private User user;
    private String country;
    private Double latitude;
    private Double longitude;
}
