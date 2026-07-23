package com.dhaneesh.seatsure.booking.repository;

import com.dhaneesh.seatsure.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
            SELECT DISTINCT b
            FROM Booking b
            LEFT JOIN FETCH b.bookingSeats bs
            LEFT JOIN FETCH bs.showSeat ss
            LEFT JOIN FETCH ss.show s
            LEFT JOIN FETCH s.movie
            LEFT JOIN FETCH s.screen sc
            LEFT JOIN FETCH sc.theatre
            WHERE b.user.id = :userId
            ORDER BY b.bookingTime DESC
            """)
    List<Booking> findBookingsWithDetails(@Param("userId") Long userId);

    @Query("""
SELECT DISTINCT b
FROM Booking b
LEFT JOIN FETCH b.bookingSeats bs
LEFT JOIN FETCH bs.showSeat ss
WHERE b.id = :bookingId
""")
    Optional<Booking> findBookingForCancellation(
            @Param("bookingId") Long bookingId);
}