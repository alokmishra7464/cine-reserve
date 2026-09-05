package com.cinereserve.cine_reserve.dto;

import com.cinereserve.cine_reserve.enums.BookingStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class BookingResponse {

    private Long bookingId;
    private Long showId;
    private BookingStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private List<Long> seatIds;
}
