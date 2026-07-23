package com.dhaneesh.seatsure.booking.repository;

import com.dhaneesh.seatsure.booking.entity.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingSeatRepository
        extends JpaRepository<BookingSeat, Long> {

}