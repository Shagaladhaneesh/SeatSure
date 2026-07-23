package com.dhaneesh.seatsure.movie.service;

import com.dhaneesh.seatsure.exceptions.ResourceAlreadyExistsException;
import com.dhaneesh.seatsure.exceptions.ResourceNotFoundException;
import com.dhaneesh.seatsure.movie.dto.CreateMovieRequest;
import com.dhaneesh.seatsure.movie.dto.MovieResponse;
import com.dhaneesh.seatsure.movie.dto.UpdateMovieRequest;
import com.dhaneesh.seatsure.movie.entity.Movie;
import com.dhaneesh.seatsure.movie.mapper.MovieMapper;
import com.dhaneesh.seatsure.movie.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public MovieService(MovieRepository movieRepository,
                        MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    public MovieResponse createMovie(CreateMovieRequest request) {

        movieRepository.findByTitle(request.getTitle())
                .ifPresent(movie -> {
                    throw new ResourceAlreadyExistsException("Movie already exists");
                });

        Movie movie = movieMapper.toEntity(request);

        Movie savedMovie = movieRepository.save(movie);

        return movieMapper.toResponse(savedMovie);
    }

    public MovieResponse getMovieById(Long id) {

        Movie movie = getMovie(id);

        return movieMapper.toResponse(movie);
    }

    public List<MovieResponse> getAllMovies() {

        return movieRepository.findByActiveTrue()
                .stream()
                .map(movieMapper::toResponse)
                .toList();
    }

    public MovieResponse updateMovie(Long id,
                                     UpdateMovieRequest request) {

        Movie movie = getMovie(id);

        movie.setTitle(request.getTitle());
        movie.setLanguage(request.getLanguage());
        movie.setGenre(request.getGenre());
        movie.setDurationInMinutes(request.getDurationInMinutes());
        movie.setCertificate(request.getCertificate());
        movie.setReleaseDate(request.getReleaseDate());

        Movie updatedMovie = movieRepository.save(movie);

        return movieMapper.toResponse(updatedMovie);
    }

    public void deleteMovie(Long id) {

        Movie movie = getMovie(id);

        movie.setActive(false);

        movieRepository.save(movie);
    }

    private Movie getMovie(Long id) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Movie not found"));

        if (!movie.getActive()) {
            throw new ResourceNotFoundException("Movie not found");
        }

        return movie;
    }

}