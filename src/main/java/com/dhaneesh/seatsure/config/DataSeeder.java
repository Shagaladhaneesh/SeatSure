package com.dhaneesh.seatsure.config;

import com.dhaneesh.seatsure.movie.entity.Certificate;
import com.dhaneesh.seatsure.movie.entity.Genre;
import com.dhaneesh.seatsure.movie.entity.Language;
import com.dhaneesh.seatsure.movie.entity.Movie;
import com.dhaneesh.seatsure.movie.repository.MovieRepository;
import com.dhaneesh.seatsure.screen.entity.Screen;
import com.dhaneesh.seatsure.screen.repository.ScreenRepository;
import com.dhaneesh.seatsure.seat.entity.Seat;
import com.dhaneesh.seatsure.seat.entity.SeatType;
import com.dhaneesh.seatsure.seat.repository.SeatRepository;
import com.dhaneesh.seatsure.show.dto.CreateShowRequest;
import com.dhaneesh.seatsure.show.repository.ShowRepository;
import com.dhaneesh.seatsure.show.service.ShowService;
import com.dhaneesh.seatsure.theatre.entity.Theatre;
import com.dhaneesh.seatsure.theatre.repository.TheatreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final MovieRepository movieRepository;
    private final TheatreRepository theatreRepository;
    private final ScreenRepository screenRepository;
    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;

    private final ShowService showService;

    public DataSeeder(MovieRepository movieRepository,
                      TheatreRepository theatreRepository,
                      ScreenRepository screenRepository,
                      SeatRepository seatRepository,
                      ShowRepository showRepository,
                      ShowService showService) {

        this.movieRepository = movieRepository;
        this.theatreRepository = theatreRepository;
        this.screenRepository = screenRepository;
        this.seatRepository = seatRepository;
        this.showRepository = showRepository;
        this.showService = showService;
    }
    @Override
    public void run(String... args) {

        seedMovies();

        Theatre theatre = seedTheatre();

        Screen screen = seedScreen(theatre);

        seedSeats(screen);

        seedShows();
    }
    private void seedMovies() {

        if(movieRepository.count() > 0){
            return;
        }

        Movie movie1 = Movie.builder()
                .title("Interstellar")
                .language(Language.ENGLISH)
                .genre(Genre.ACTION)
                .durationInMinutes(148)
                .certificate(Certificate.UA)
                .releaseDate(LocalDate.of(2014,11,7))
                .active(true)
                .build();

        Movie movie2 = Movie.builder()
                .title("Inception")
                .language(Language.ENGLISH)
                .genre(Genre.ACTION)
                .durationInMinutes(148)
                .certificate(Certificate.UA)
                .releaseDate(LocalDate.of(2010,7,16))
                .active(true)
                .build();

        movieRepository.saveAll(List.of(movie1,movie2));
    }

    private Theatre seedTheatre() {

        if (theatreRepository.count() > 0) {
            return theatreRepository.findAll().get(0);
        }

        Theatre theatre = Theatre.builder()
                .name("PVR Koramangala")
                .city("Bangalore")
                .address("Forum Mall, Koramangala, Bangalore")
                .active(true)
                .build();

        return theatreRepository.save(theatre);
    }
    private Screen seedScreen(Theatre theatre){

        if(screenRepository.count()>0){

            return screenRepository.findAll().get(0);

        }

        Screen screen = Screen.builder()
                .screenNumber(1)
                .theatre(theatre)
                .active(true)
                .build();

        return screenRepository.save(screen);

    }
    private void seedSeats(Screen screen) {

        if (seatRepository.count() > 0) {
            return;
        }

        List<Seat> seats = new ArrayList<>();

        for (char row = 'A'; row <= 'J'; row++) {

            for (int number = 1; number <= 10; number++) {

                SeatType seatType;

                if (row <= 'C') {
                    seatType = SeatType.PREMIUM;
                } else if (row <= 'H') {
                    seatType = SeatType.REGULAR;
                } else {
                    seatType = SeatType.RECLINER;
                }

                Seat seat = Seat.builder()
                        .screen(screen)
                        .row(String.valueOf(row))
                        .seatNumber(number)
                        .seatType(seatType)
                        .active(true)
                        .build();

                seats.add(seat);
            }
        }

        seatRepository.saveAll(seats);
    }
    private void seedShows(){

        if(showRepository.count()>0){

            return;

        }

        Movie movie1 = movieRepository.findAll().get(0);

        Movie movie2 = movieRepository.findAll().get(1);

        Screen screen = screenRepository.findAll().get(0);

        showService.createShow(
                CreateShowRequest.builder()
                        .movieId(movie1.getId())
                        .screenId(screen.getId())
                        .startTime(LocalDateTime.now()
                                .plusDays(1)
                                .withHour(10)
                                .withMinute(0))
                        .build()
        );

        showService.createShow(
                CreateShowRequest.builder()
                        .movieId(movie2.getId())
                        .screenId(screen.getId())
                        .startTime(LocalDateTime.now()
                                .plusDays(1)
                                .withHour(14)
                                .withMinute(0))
                        .build()
        );
    }
}