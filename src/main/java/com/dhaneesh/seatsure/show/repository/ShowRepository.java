package com.dhaneesh.seatsure.show.repository;

import com.dhaneesh.seatsure.screen.entity.Screen;
import com.dhaneesh.seatsure.show.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByScreenAndActiveTrue(Screen screen);

    List<Show> findByActiveTrue();
    List<Show> findByMovieId(Long movieId);

}