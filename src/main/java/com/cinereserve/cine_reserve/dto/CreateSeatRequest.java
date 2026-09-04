package com.cinereserve.cine_reserve.dto;

import com.cinereserve.cine_reserve.enums.SeatType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateSeatRequest {

    private String rowLabel;
    private Integer seatNumber;
    private SeatType seatType;
    private Long screenId;
}
