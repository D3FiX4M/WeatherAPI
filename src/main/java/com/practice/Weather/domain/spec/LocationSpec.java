package com.practice.Weather.domain.spec;

import com.practice.Weather.domain.entity.Location;
import com.practice.Weather.domain.entity.Location_;
import com.practice.Weather.domain.entity.User;
import com.practice.Weather.domain.entity.User_;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.util.function.Function;

@UtilityClass
public class LocationSpec {

    public Specification<Location> userEq(User user) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Location_.USER), user));
    }

    public Specification<Location> nameEq(String name){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Location_.NAME), name));
    }

    public Specification<Location> latitudeEq(double value){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Location_.LATITUDE), value));
    }

    public Specification<Location> longitudeEq(double value){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Location_.LONGITUDE), value));
    }

}
