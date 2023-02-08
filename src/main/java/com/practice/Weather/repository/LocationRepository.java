package com.practice.Weather.repository;

import com.practice.Weather.entity.Location;
import com.practice.Weather.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findAllByUser(User user);
    Optional<Location> findAllByUserAndName(User user, String name);
    boolean existsByUserAndName(User user, String name);
    Optional<Location> findAllByUserAndLatitudeAndLongitude(User user, double latitude, double longitude);

    boolean existsByUserAndLatitudeAndLongitude(User user, double latitude, double longitude);
}
