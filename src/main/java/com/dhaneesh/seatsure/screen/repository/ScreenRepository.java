package com.dhaneesh.seatsure.screen.repository;

import com.dhaneesh.seatsure.screen.entity.Screen;
import com.dhaneesh.seatsure.theatre.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScreenRepository extends JpaRepository<Screen, Long> {

    Optional<Screen> findByTheatreAndScreenNumber(
            Theatre theatre,
            Integer screenNumber
    );

    List<Screen> findByActiveTrue();

}