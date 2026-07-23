package com.dhaneesh.seatsure.theatre.controller;

import com.dhaneesh.seatsure.theatre.dto.CreateTheatreRequest;
import com.dhaneesh.seatsure.theatre.dto.TheatreResponse;
import com.dhaneesh.seatsure.theatre.service.TheatreService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Theatres", description = "Theatre Management APIs")
@RestController
@RequestMapping("/api/theatres")
public class TheatreController {

    private final TheatreService theatreService;

    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    @PostMapping
    public ResponseEntity<TheatreResponse> createTheatre(
            @Valid @RequestBody CreateTheatreRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(theatreService.createTheatre(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TheatreResponse> getTheatre(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                theatreService.getTheatreById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<TheatreResponse>> getAllTheatres() {

        return ResponseEntity.ok(
                theatreService.getAllTheatres()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheatre(
            @PathVariable Long id) {

        theatreService.deleteTheatre(id);

        return ResponseEntity.noContent().build();
    }
}
