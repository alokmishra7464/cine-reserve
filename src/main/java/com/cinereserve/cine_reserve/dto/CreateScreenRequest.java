package com.cinereserve.cine_reserve.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateScreenRequest {

    private String name;

    private Long theaterId;

}
