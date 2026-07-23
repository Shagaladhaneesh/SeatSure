package com.dhaneesh.seatsure.screen.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenResponse {

    private Long id;

    private Integer screenNumber;

    private Long theatreId;

    private String theatreName;

}