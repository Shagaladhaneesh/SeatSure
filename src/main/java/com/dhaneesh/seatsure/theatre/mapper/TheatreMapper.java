package com.dhaneesh.seatsure.theatre.mapper;

import com.dhaneesh.seatsure.theatre.dto.CreateTheatreRequest;
import com.dhaneesh.seatsure.theatre.dto.TheatreResponse;
import com.dhaneesh.seatsure.theatre.entity.Theatre;
import org.springframework.stereotype.Component;

@Component
public class TheatreMapper {

    public Theatre toEntity(CreateTheatreRequest request) {

        return Theatre.builder()
                .name(request.getName())
                .city(request.getCity())
                .address(request.getAddress())
                .build();
    }

    public TheatreResponse toResponse(Theatre theatre) {

        return TheatreResponse.builder()
                .id(theatre.getId())
                .name(theatre.getName())
                .city(theatre.getCity())
                .address(theatre.getAddress())
                .build();
    }
}