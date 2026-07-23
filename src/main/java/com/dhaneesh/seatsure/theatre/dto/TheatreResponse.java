package com.dhaneesh.seatsure.theatre.dto;
import  lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheatreResponse {

    private Long id;

    private String name;

    private String city;

    private String address;
}