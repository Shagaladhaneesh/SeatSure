package com.dhaneesh.seatsure.show.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowResponse {

    private Long id;

    private Long movieId;
    private String movieName;

    private Long screenId;
    private Integer screenNumber;

    private String theatreName;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}