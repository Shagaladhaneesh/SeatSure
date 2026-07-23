package com.dhaneesh.seatsure.showseat.dto;

import com.dhaneesh.seatsure.showseat.entity.SeatStatus;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowSeatResponse {

    private long showSeatId;
    private String seatLabel;
    private SeatStatus status;
    private BigDecimal price;

}
