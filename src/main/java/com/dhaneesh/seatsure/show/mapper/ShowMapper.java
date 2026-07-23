package com.dhaneesh.seatsure.show.mapper;

import com.dhaneesh.seatsure.movie.entity.Movie;
import com.dhaneesh.seatsure.screen.entity.Screen;
import com.dhaneesh.seatsure.show.dto.CreateShowRequest;
import com.dhaneesh.seatsure.show.dto.ShowResponse;
import com.dhaneesh.seatsure.show.entity.Show;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ShowMapper {

    public Show toEntity(CreateShowRequest request,
                         Movie movie,
                         Screen screen,
                         LocalDateTime endTime) {

        return Show.builder()
                .movie(movie)
                .screen(screen)
                .startTime(request.getStartTime())
                .endTime(endTime)
                .build();
    }

    public ShowResponse toResponse(Show show) {

        return ShowResponse.builder()
                .id(show.getId())
                .movieId(show.getMovie().getId())
                .movieName(show.getMovie().getTitle())
                .screenId(show.getScreen().getId())
                .screenNumber(show.getScreen().getScreenNumber())
                .theatreName(show.getScreen().getTheatre().getName())
                .startTime(show.getStartTime())
                .endTime(show.getEndTime())
                .build();
    }
}