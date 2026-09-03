package com.cinereserve.cine_reserve.dto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ShowResponse {

    private Long id;
    private String movieTitle;
    private Long screenId;
    private String screenName;
    private String theaterName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BigDecimal price;

}
