package com.dhaneesh.seatsure.movie.mapper;

import com.dhaneesh.seatsure.movie.dto.CreateMovieRequest;
import com.dhaneesh.seatsure.movie.dto.MovieResponse;
import com.dhaneesh.seatsure.movie.entity.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public Movie toEntity(CreateMovieRequest request) {

        return Movie.builder()
                .title(request.getTitle())
                .language(request.getLanguage())
                .genre(request.getGenre())
                .durationInMinutes(request.getDurationInMinutes())
                .certificate(request.getCertificate())
                .releaseDate(request.getReleaseDate())
                .build();
    }

    public MovieResponse toResponse(Movie movie) {

        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .language(movie.getLanguage())
                .genre(movie.getGenre())
                .durationInMinutes(movie.getDurationInMinutes())
                .certificate(movie.getCertificate())
                .releaseDate(movie.getReleaseDate())
                .build();
    }
}