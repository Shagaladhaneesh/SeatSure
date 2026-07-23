package com.dhaneesh.seatsure.show.service;

import com.dhaneesh.seatsure.exceptions.ResourceAlreadyExistsException;
import com.dhaneesh.seatsure.exceptions.ResourceNotFoundException;
import com.dhaneesh.seatsure.movie.entity.Movie;
import com.dhaneesh.seatsure.movie.repository.MovieRepository;
import com.dhaneesh.seatsure.screen.entity.Screen;
import com.dhaneesh.seatsure.screen.repository.ScreenRepository;
import com.dhaneesh.seatsure.seat.entity.Seat;
import com.dhaneesh.seatsure.seat.entity.SeatType;
import com.dhaneesh.seatsure.seat.repository.SeatRepository;
import com.dhaneesh.seatsure.show.dto.CreateShowRequest;
import com.dhaneesh.seatsure.show.dto.ShowResponse;
import com.dhaneesh.seatsure.show.entity.Show;
import com.dhaneesh.seatsure.show.mapper.ShowMapper;
import com.dhaneesh.seatsure.show.repository.ShowRepository;
import com.dhaneesh.seatsure.showseat.entity.SeatStatus;
import com.dhaneesh.seatsure.showseat.entity.ShowSeat;
import com.dhaneesh.seatsure.showseat.repository.ShowSeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShowService {

    @Value("${show.cleaning-buffer-minutes}")
    private int cleaningBufferMinutes;

    private final SeatRepository seatRepository;
    private final ShowSeatRepository showSeatRepository;

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final ShowMapper showMapper;

    public ShowService(ShowRepository showRepository,
                       MovieRepository movieRepository,
                       ScreenRepository screenRepository,
                       SeatRepository seatRepository,
                       ShowSeatRepository showSeatRepository,
                       ShowMapper showMapper) {

        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
        this.screenRepository = screenRepository;
        this.seatRepository = seatRepository;
        this.showSeatRepository = showSeatRepository;
        this.showMapper = showMapper;
    }

    public List<ShowResponse> getShowsByMovie(Long movieId) {

        //throwing exception when movie is not present. instead of searching
        // shows of that movie

        movieRepository.findById(movieId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Movie not found"));
        List<Show> shows = showRepository.findByMovieId(movieId);

            return shows.stream()
                    .map(showMapper::toResponse)
                    .toList();
    }

    @Transactional
    public ShowResponse createShow(CreateShowRequest request) {

        Movie movie = getActiveMovie(request.getMovieId());

        Screen screen = getActiveScreen(request.getScreenId());

        LocalDateTime endTime =
                calculateEndTime(movie, request.getStartTime());

        validateShowTiming(
                screen,
                request.getStartTime(),
                endTime
        );

        Show show = showMapper.toEntity(
                request,
                movie,
                screen,
                endTime
        );

        Show savedShow = showRepository.save(show);
        generateShowSeats(savedShow);
        return showMapper.toResponse(savedShow);
    }
    private void generateShowSeats(Show show) {

        List<Seat> seats =
                seatRepository.findByScreenAndActiveTrue(show.getScreen());

        List<ShowSeat> showSeats = new ArrayList<>();

        for (Seat seat : seats) {

            ShowSeat showSeat = ShowSeat.builder()
                    .show(show)
                    .seat(seat)
                    .status(SeatStatus.AVAILABLE)
                    .price(getSeatPrice(seat.getSeatType()))
                    .build();

            showSeats.add(showSeat);
        }

        showSeatRepository.saveAll(showSeats);
    }

    private BigDecimal getSeatPrice(SeatType seatType) {

        return switch (seatType) {

            case REGULAR -> BigDecimal.valueOf(200);

            case PREMIUM -> BigDecimal.valueOf(300);

            case RECLINER -> BigDecimal.valueOf(500);
        };
    }
    public ShowResponse getShowById(Long id) {

        return showMapper.toResponse(getActiveShow(id));
    }

    public List<ShowResponse> getAllShows() {

        return showRepository.findByActiveTrue()
                .stream()
                .map(showMapper::toResponse)
                .toList();
    }

    public void deleteShow(Long id) {

        Show show = getActiveShow(id);

        show.setActive(false);

        showRepository.save(show);
    }

    private LocalDateTime calculateEndTime(Movie movie,
                                           LocalDateTime startTime) {

        return startTime.plusMinutes(movie.getDurationInMinutes());
    }

    private void validateShowTiming(Screen screen,
                                    LocalDateTime newStart,
                                    LocalDateTime newEnd) {

        List<Show> existingShows =
                showRepository.findByScreenAndActiveTrue(screen);

        for (Show show : existingShows) {

            LocalDateTime existingStart = show.getStartTime();

            LocalDateTime existingEnd =
                    show.getEndTime()
                            .plusMinutes(cleaningBufferMinutes);

            if (newStart.isBefore(existingEnd)
                    && newEnd.isAfter(existingStart)) {

                throw new ResourceAlreadyExistsException(
                        "Another show is already scheduled during this time."
                );
            }
        }
    }

    private Movie getActiveMovie(Long id) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Movie not found"));

        if (!movie.getActive()) {
            throw new ResourceNotFoundException("Movie not found");
        }

        return movie;
    }

    private Screen getActiveScreen(Long id) {

        Screen screen = screenRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Screen not found"));

        if (!screen.getActive()) {
            throw new ResourceNotFoundException("Screen not found");
        }

        return screen;
    }

    private Show getActiveShow(Long id) {

        Show show = showRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Show not found"));

        if (!show.getActive()) {
            throw new ResourceNotFoundException("Show not found");
        }

        return show;
    }


}