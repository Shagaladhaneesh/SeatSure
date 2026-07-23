package com.dhaneesh.seatsure.screen.controller;

import com.dhaneesh.seatsure.screen.dto.CreateScreenRequest;
import com.dhaneesh.seatsure.screen.dto.ScreenResponse;
import com.dhaneesh.seatsure.screen.service.ScreenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Screens", description = "Screen Management APIs")
@RestController
@RequestMapping("/api/screens")
public class ScreenController {

    private final ScreenService screenService;

    public ScreenController(ScreenService screenService) {
        this.screenService = screenService;
    }

    @PostMapping
    public ResponseEntity<ScreenResponse> createScreen(
            @Valid @RequestBody CreateScreenRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(screenService.createScreen(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScreenResponse> getScreenById(
            @PathVariable Long id) {

        return ResponseEntity.ok(screenService.getScreenById(id));
    }

    @GetMapping
    public ResponseEntity<List<ScreenResponse>> getAllScreens() {

        return ResponseEntity.ok(screenService.getAllScreens());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScreen(
            @PathVariable Long id) {

        screenService.deleteScreen(id);

        return ResponseEntity.noContent().build();
    }
}