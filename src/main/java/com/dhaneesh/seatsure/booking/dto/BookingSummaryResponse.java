package com.dhaneesh.seatsure.booking.dto;

import com.dhaneesh.seatsure.booking.entity.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingSummaryResponse {

    private Long bookingId;

    private String movieName;

    private String theatreName;

    private LocalDateTime showTime;

    private BookingStatus status;

    private BigDecimal totalAmount;

    private LocalDateTime bookingTime;

}