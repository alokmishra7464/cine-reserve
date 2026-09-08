package com.cinereserve.cine_reserve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LockInfo {
    Long showId;
    Long seatId;
    String value;
}
