package com.dhaneesh.seatsure.booking.service;

import com.dhaneesh.seatsure.booking.dto.BookingResponse;
import com.dhaneesh.seatsure.booking.dto.BookingSummaryResponse;
import com.dhaneesh.seatsure.booking.dto.CreateBookingRequest;
import com.dhaneesh.seatsure.booking.entity.Booking;
import com.dhaneesh.seatsure.booking.entity.BookingSeat;
import com.dhaneesh.seatsure.booking.entity.BookingStatus;
import com.dhaneesh.seatsure.booking.mapper.BookingMapper;
import com.dhaneesh.seatsure.booking.repository.BookingRepository;
import com.dhaneesh.seatsure.booking.repository.BookingSeatRepository;
import com.dhaneesh.seatsure.exceptions.ResourceNotFoundException;
import com.dhaneesh.seatsure.showseat.entity.SeatStatus;
import com.dhaneesh.seatsure.showseat.entity.ShowSeat;
import com.dhaneesh.seatsure.showseat.repository.ShowSeatRepository;
import com.dhaneesh.seatsure.user.entity.User;
import com.dhaneesh.seatsure.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final ShowSeatRepository showSeatRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;

    public BookingService(BookingRepository bookingRepository,
                          BookingSeatRepository bookingSeatRepository,
                          ShowSeatRepository showSeatRepository,
                          UserRepository userRepository,
                          BookingMapper bookingMapper) {

        this.bookingRepository = bookingRepository;
        this.bookingSeatRepository = bookingSeatRepository;
        this.showSeatRepository = showSeatRepository;
        this.userRepository = userRepository;
        this.bookingMapper = bookingMapper;
    }

    @Transactional
    public BookingResponse bookSeats(CreateBookingRequest request) {

        User user = getCurrentUser();

        List<ShowSeat> showSeats = getLockedShowSeats(request);

        validateBooking(request, showSeats);

        BigDecimal totalAmount = calculateTotal(showSeats);

        Booking booking = createBooking(user, totalAmount);


                createBookingSeats(booking, showSeats);

        markSeatsAsBooked(showSeats);

        return bookingMapper.toResponse(
                booking
        );
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }
    private List<ShowSeat> getLockedShowSeats(CreateBookingRequest request) {

        List<ShowSeat> showSeats =
                showSeatRepository.findAllByIdWithLock(
                        request.getShowSeatIds());

        return showSeats;
    }
    private void validateBooking(CreateBookingRequest request,
                                 List<ShowSeat> showSeats) {

        validateSeatCount(request, showSeats);

        validateSameShow(showSeats);

        validateAvailability(showSeats);
    }
    private void validateSeatCount(CreateBookingRequest request,
                                   List<ShowSeat> showSeats) {

        if (showSeats.size() != request.getShowSeatIds().size()) {

            throw new ResourceNotFoundException(
                    "One or more seats were not found.");
        }
    }
    private void validateSameShow(List<ShowSeat> showSeats) {

        Long showId = showSeats.get(0)
                .getShow()
                .getId();

        for (ShowSeat showSeat : showSeats) {

            if (!showSeat.getShow()
                    .getId()
                    .equals(showId)) {

                throw new IllegalArgumentException(
                        "All seats must belong to the same show.");
            }
        }
    }
    private void validateAvailability(List<ShowSeat> showSeats) {

        for (ShowSeat showSeat : showSeats) {

            if (showSeat.getStatus() != SeatStatus.AVAILABLE) {

                throw new IllegalArgumentException(
                        "One or more seats are already booked.");
            }
        }
    }
    private BigDecimal calculateTotal(List<ShowSeat> showSeats) {

        BigDecimal total = BigDecimal.ZERO;

        for (ShowSeat showSeat : showSeats) {

            total = total.add(showSeat.getPrice());
        }

        return total;
    }
    private Booking createBooking(User user,
                                  BigDecimal totalAmount) {

        Booking booking = Booking.builder()
                .user(user)
                .status(BookingStatus.CONFIRMED)
                .totalAmount(totalAmount)
                .bookingTime(LocalDateTime.now())
                .build();

        return bookingRepository.save(booking);
    }
    private void createBookingSeats(
            Booking booking,
            List<ShowSeat> showSeats) {

        for (ShowSeat showSeat : showSeats) {

            BookingSeat bookingSeat = BookingSeat.builder()
                    .showSeat(showSeat)
                    .build();

            booking.addBookingSeat(bookingSeat);
        }

        bookingRepository.save(booking);
    }
    private void markSeatsAsBooked(List<ShowSeat> showSeats) {

        for (ShowSeat showSeat : showSeats) {

            showSeat.setStatus(SeatStatus.BOOKED);
        }

        showSeatRepository.saveAll(showSeats);
    }
    public List<BookingSummaryResponse> getMyBookings() {

        User user = getCurrentUser();

        List<Booking> bookings =
                bookingRepository.findBookingsWithDetails(user.getId());

        return bookings.stream()
                .map(bookingMapper::toSummaryResponse)
                .toList();
    }
    @Transactional
    public void cancelBooking(Long bookingId) {

        User currentUser = getCurrentUser();

        Booking booking = bookingRepository
                .findBookingForCancellation(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        if (!booking.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException(
                    "You are not authorized to cancel this booking.");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalStateException(
                    "Booking is already cancelled.");
        }

        booking.setStatus(BookingStatus.CANCELLED);

        for (BookingSeat bookingSeat : booking.getBookingSeats()) {
            bookingSeat.getShowSeat().setStatus(SeatStatus.AVAILABLE);
        }

        bookingRepository.save(booking);
    }
}