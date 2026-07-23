package com.dhaneesh.seatsure.showseat.mapper;

import com.dhaneesh.seatsure.showseat.dto.ShowSeatResponse;
import com.dhaneesh.seatsure.showseat.entity.ShowSeat;
import org.springframework.stereotype.Component;

@Component
public class ShowSeatMapper {

    public ShowSeatResponse toResponse(ShowSeat showSeat){

        return ShowSeatResponse.builder()
                .showSeatId(showSeat.getId())
                .seatLabel(
                        showSeat.getSeat().getRow()
                                + showSeat.getSeat().getSeatNumber()
                )
                .status(showSeat.getStatus())
                .price(showSeat.getPrice())
                .build();
    }

}