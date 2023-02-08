package com.practice.Weather.mapper;

import com.practice.Weather.dto.ApiLocationResponse;
import com.practice.Weather.dto.LocationResponse;
import com.practice.Weather.entity.Location;
import com.practice.Weather.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LocationMapper {

    public LocationResponse toDto(Location location){
        return LocationResponse.builder()
                .name(location.getName())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .country(location.getCountry())
                .build();
    }

    public List<LocationResponse> toDto(List<Location> locations){
        List<LocationResponse> dtoList = new ArrayList<>();
        for (Location location: locations
             ) {
            dtoList.add(
                    LocationResponse.builder()
                            .name(location.getName())
                            .latitude(location.getLatitude())
                            .longitude(location.getLongitude())
                            .country(location.getCountry())
                            .build()
            );
        }

        return dtoList;
    }

    public Location toEntity(ApiLocationResponse response, User user){
        return Location.builder()
                .name(response.getName())
                .user(user)
                .country(response.getCountry())
                .longitude(response.getLongitude())
                .latitude(response.getLatitude())
                .build();
    }


}
