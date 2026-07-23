package com.dhaneesh.seatsure.booking.controller;

import com.dhaneesh.seatsure.booking.dto.BookingResponse;
import com.dhaneesh.seatsure.booking.dto.BookingSummaryResponse;
import com.dhaneesh.seatsure.booking.dto.CreateBookingRequest;
import com.dhaneesh.seatsure.booking.service.BookingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Bookings", description = "Booking APIs")
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> bookSeats(
            @Valid @RequestBody CreateBookingRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingService.bookSeats(request));
    }

    @GetMapping("/my-bookings")
    public ResponseEntity<List<BookingSummaryResponse>> getMyBookings() {

        return ResponseEntity.ok(
                bookingService.getMyBookings()
        );
    }

    @PatchMapping("/{bookingId}/cancel")
    public ResponseEntity<Void> cancelBooking(
            @PathVariable Long bookingId) {

        bookingService.cancelBooking(bookingId);

        return ResponseEntity.noContent().build();
    }
}