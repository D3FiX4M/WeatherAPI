package com.practice.Weather.domain.mapper;

import com.practice.Weather.payload.location.ApiLocationResponse;
import com.practice.Weather.payload.location.LocationDto;
import com.practice.Weather.payload.location.LocationFilter;
import com.practice.Weather.payload.location.LocationResponse;
import com.practice.Weather.domain.entity.Location;
import com.practice.Weather.domain.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LocationMapper {

    public LocationResponse toDto(Location location){
        return LocationResponse.builder()
                .id(location.getId())
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
                            .id(location.getId())
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

    public LocationDto toDto(LocationFilter filter, User user){
        return LocationDto.builder()
                .id(filter.getId())
                .name(filter.getName())
                .user(user)
                .country(filter.getCountry())
                .latitude(filter.getLatitude())
                .longitude(filter.getLongitude())
                .build();
    }


}
