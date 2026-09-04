package com.cinereserve.cine_reserve.dto;

import com.cinereserve.cine_reserve.enums.SeatType;
import com.cinereserve.cine_reserve.enums.ShowSeatStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShowSeatResponse {
    private Long id;
    private String rowLabel;
    private Integer seatNumber;
    private SeatType seatType;
    private ShowSeatStatus showSeatStatus;
}
