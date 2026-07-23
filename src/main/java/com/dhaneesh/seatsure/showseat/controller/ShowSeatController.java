package com.dhaneesh.seatsure.showseat.controller;

import com.dhaneesh.seatsure.exceptions.ResourceNotFoundException;
import com.dhaneesh.seatsure.showseat.dto.ShowSeatResponse;
import com.dhaneesh.seatsure.showseat.service.ShowSeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
public class ShowSeatController {

    private final ShowSeatService showSeatService;

    public ShowSeatController(ShowSeatService showSeatService) {
        this.showSeatService = showSeatService;
    }

    @GetMapping("/{showId}/seats")
    public ResponseEntity<List<ShowSeatResponse>> getSeatsForShow(
            @PathVariable Long showId) {
        List<ShowSeatResponse> seats = showSeatService.getSeatsForShow(showId);

        if(seats.isEmpty())
        {
            throw new ResourceNotFoundException("No seats Found");
        }return ResponseEntity.ok(seats);
    }
}