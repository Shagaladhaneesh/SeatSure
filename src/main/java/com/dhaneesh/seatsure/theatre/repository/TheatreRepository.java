package com.dhaneesh.seatsure.theatre.repository;

import com.dhaneesh.seatsure.theatre.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre,Long> {
    Optional<Theatre> findByNameAndCity(String name, String city);

    List<Theatre> findByActiveTrue();
}
