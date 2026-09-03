package com.cinereserve.cine_reserve.dto;

import com.cinereserve.cine_reserve.enums.SeatType;
import lombok.Data;

@Data
public class CreateSeatRequest {

    private String rowLabel;
    private Integer seatNumber;
    private SeatType seatType;
    private Long screenId;
}
