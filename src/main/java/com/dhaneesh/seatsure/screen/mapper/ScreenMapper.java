package com.dhaneesh.seatsure.screen.mapper;

import com.dhaneesh.seatsure.screen.dto.CreateScreenRequest;
import com.dhaneesh.seatsure.screen.dto.ScreenResponse;
import com.dhaneesh.seatsure.screen.entity.Screen;
import com.dhaneesh.seatsure.theatre.entity.Theatre;
import org.springframework.stereotype.Component;

@Component
public class ScreenMapper {

    public Screen toEntity(CreateScreenRequest request, Theatre theatre) {

        return Screen.builder()
                .screenNumber(request.getScreenNumber())
                .theatre(theatre)
                .build();
    }

    public ScreenResponse toResponse(Screen screen) {

        return ScreenResponse.builder()
                .id(screen.getId())
                .screenNumber(screen.getScreenNumber())
                .theatreId(screen.getTheatre().getId())
                .theatreName(screen.getTheatre().getName())
                .build();
    }
}