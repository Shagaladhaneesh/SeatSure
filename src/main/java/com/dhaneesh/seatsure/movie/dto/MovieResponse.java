package com.dhaneesh.seatsure.movie.dto;

import com.dhaneesh.seatsure.movie.entity.Certificate;
import com.dhaneesh.seatsure.movie.entity.Genre;
import com.dhaneesh.seatsure.movie.entity.Language;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponse {

    private Long id;

    private String title;

    private Language language;

    private Genre genre;

    private Integer durationInMinutes;

    private Certificate certificate;

    private LocalDate releaseDate;

    // getters and setters
}