package com.dhaneesh.seatsure.seat.repository;

import com.dhaneesh.seatsure.screen.entity.Screen;
import com.dhaneesh.seatsure.seat.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByScreenAndActiveTrue(Screen screen);
}