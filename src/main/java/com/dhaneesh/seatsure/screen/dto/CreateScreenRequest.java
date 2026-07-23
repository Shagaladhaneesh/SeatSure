package com.dhaneesh.seatsure.screen.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateScreenRequest {

    @NotNull
    private Integer screenNumber;

    @NotNull
    private Long theatreId;

    @NotNull
    @Min(1)
    @Max(26)
    private Integer rows;

    @NotNull
    @Min(1)
    private Integer seatsPerRow;

}