package com.dhaneesh.seatsure.booking.dto;

import com.dhaneesh.seatsure.booking.entity.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {

    private Long bookingId;

    private BookingStatus status;

    private String movieName;

    private String theatreName;

    private Integer screenNumber;

    private LocalDateTime showStartTime;

    private List<String> seats;

    private BigDecimal totalAmount;

    private LocalDateTime bookingTime;

}