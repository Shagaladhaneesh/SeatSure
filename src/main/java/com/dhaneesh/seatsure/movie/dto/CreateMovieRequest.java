package com.dhaneesh.seatsure.movie.dto;

import com.dhaneesh.seatsure.movie.entity.Certificate;
import com.dhaneesh.seatsure.movie.entity.Genre;
import com.dhaneesh.seatsure.movie.entity.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMovieRequest {

    @NotBlank
    private String title;

    @NotNull
    private Language language;

    @NotNull
    private Genre genre;

    @NotNull
    private Integer durationInMinutes;

    @NotNull
    private Certificate certificate;

    @NotNull
    private LocalDate releaseDate;

    // getters and setters
}