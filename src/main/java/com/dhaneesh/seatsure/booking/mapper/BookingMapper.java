package com.dhaneesh.seatsure.booking.mapper;

import com.dhaneesh.seatsure.booking.dto.BookingResponse;
import com.dhaneesh.seatsure.booking.dto.BookingSummaryResponse;
import com.dhaneesh.seatsure.booking.entity.Booking;
import com.dhaneesh.seatsure.booking.entity.BookingSeat;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingMapper {

    public BookingResponse toResponse(Booking booking) {

        BookingSeat firstSeat = booking.getBookingSeats().get(0);

        List<String> seats = booking.getBookingSeats()
                .stream()
                .map(bs ->
                        bs.getShowSeat()
                                .getSeat()
                                .getRow()
                                + bs.getShowSeat()
                                .getSeat()
                                .getSeatNumber())
                .toList();

        return BookingResponse.builder()
                .bookingId(booking.getId())
                .status(booking.getStatus())
                .movieName(firstSeat.getShowSeat().getShow().getMovie().getTitle())
                .theatreName(firstSeat.getShowSeat().getShow().getScreen().getTheatre().getName())
                .screenNumber(firstSeat.getShowSeat().getShow().getScreen().getScreenNumber())
                .showStartTime(firstSeat.getShowSeat().getShow().getStartTime())
                .seats(seats)
                .totalAmount(booking.getTotalAmount())
                .bookingTime(booking.getBookingTime())
                .build();
    }

    public BookingSummaryResponse toSummaryResponse(Booking booking) {

        BookingSeat firstSeat = booking.getBookingSeats().get(0);

        return BookingSummaryResponse.builder()
                .bookingId(booking.getId())
                .movieName(firstSeat.getShowSeat()
                        .getShow()
                        .getMovie()
                        .getTitle())
                .theatreName(firstSeat.getShowSeat()
                        .getShow()
                        .getScreen()
                        .getTheatre()
                        .getName())
                .showTime(firstSeat.getShowSeat()
                        .getShow()
                        .getStartTime())
                .status(booking.getStatus())
                .totalAmount(booking.getTotalAmount())
                .bookingTime(booking.getBookingTime())
                .build();
    }
}