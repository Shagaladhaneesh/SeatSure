package com.dhaneesh.seatsure.showseat.service;

import com.dhaneesh.seatsure.showseat.dto.ShowSeatResponse;
import com.dhaneesh.seatsure.showseat.entity.ShowSeat;
import com.dhaneesh.seatsure.showseat.mapper.ShowSeatMapper;
import com.dhaneesh.seatsure.showseat.repository.ShowSeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowSeatService {

    private final ShowSeatRepository showSeatRepository;
    private final ShowSeatMapper showSeatMapper;

    public ShowSeatService(
            ShowSeatRepository showSeatRepository,
            ShowSeatMapper showSeatMapper) {

        this.showSeatRepository = showSeatRepository;
        this.showSeatMapper = showSeatMapper;
    }

    public List<ShowSeatResponse> getSeatsForShow(Long showId) {

        List<ShowSeat> showSeats =
                showSeatRepository
                        .findByShowIdOrderBySeatRowAscSeatSeatNumberAsc(showId);

        return showSeats.stream()
                .map(showSeatMapper::toResponse)
                .toList();
    }
}