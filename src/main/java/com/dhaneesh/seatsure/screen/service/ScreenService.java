package com.dhaneesh.seatsure.screen.service;

import com.dhaneesh.seatsure.exceptions.ResourceAlreadyExistsException;
import com.dhaneesh.seatsure.exceptions.ResourceNotFoundException;
import com.dhaneesh.seatsure.screen.dto.CreateScreenRequest;
import com.dhaneesh.seatsure.screen.dto.ScreenResponse;
import com.dhaneesh.seatsure.screen.entity.Screen;
import com.dhaneesh.seatsure.screen.mapper.ScreenMapper;
import com.dhaneesh.seatsure.screen.repository.ScreenRepository;
import com.dhaneesh.seatsure.seat.entity.Seat;
import com.dhaneesh.seatsure.seat.entity.SeatType;
import com.dhaneesh.seatsure.seat.repository.SeatRepository;
import com.dhaneesh.seatsure.theatre.entity.Theatre;
import com.dhaneesh.seatsure.theatre.repository.TheatreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScreenService {

    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;
    private final ScreenMapper screenMapper;
    private final SeatRepository seatRepository;

    public ScreenService(ScreenRepository screenRepository,
                          TheatreRepository theatreRepository,
                          SeatRepository seatRepository,
                          ScreenMapper screenMapper) {

        this.screenRepository = screenRepository;
        this.theatreRepository = theatreRepository;
        this.seatRepository = seatRepository;
        this.screenMapper = screenMapper;
    }

    @Transactional
    public ScreenResponse createScreen(CreateScreenRequest request) {

        Theatre theatre = getActiveTheatre(request.getTheatreId());

        screenRepository.findByTheatreAndScreenNumber(
                theatre,
                request.getScreenNumber()
        ).ifPresent(screen -> {
            throw new ResourceAlreadyExistsException(
                    "Screen already exists in this theatre"
            );
        });

        Screen screen = screenMapper.toEntity(request, theatre);

        Screen savedScreen = screenRepository.save(screen);

        generateSeats(
                savedScreen,
                request.getRows(),
                request.getSeatsPerRow()
        );

        return screenMapper.toResponse(savedScreen);
    }
    private void generateSeats(Screen screen,
                               int rows,
                               int seatsPerRow) {

        List<Seat> seats = new ArrayList<>();

        char row = 'A';

        for (int i = 0; i < rows; i++) {

            for (int j = 1; j <= seatsPerRow; j++) {

                Seat seat = Seat.builder()
                        .row(String.valueOf(row))
                        .seatNumber(j)
                        .seatType(SeatType.REGULAR)
                        .screen(screen)
                        .build();

                seats.add(seat);
            }

            row++;
        }

        seatRepository.saveAll(seats);
    }

    public ScreenResponse getScreenById(Long id) {

        return screenMapper.toResponse(getActiveScreen(id));
    }

    public List<ScreenResponse> getAllScreens() {

        return screenRepository.findByActiveTrue()
                .stream()
                .map(screenMapper::toResponse)
                .toList();
    }

    public void deleteScreen(Long id) {

        Screen screen = getActiveScreen(id);

        screen.setActive(false);

        screenRepository.save(screen);
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

    private Theatre getActiveTheatre(Long id) {

        Theatre theatre = theatreRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Theatre not found"));

        if (!theatre.getActive()) {
            throw new ResourceNotFoundException("Theatre not found");
        }

        return theatre;
    }
}