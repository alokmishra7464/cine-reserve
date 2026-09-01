package com.cinereserve.cine_reserve.dto;

import lombok.Data;

@Data
public class CreateScreenRequest {

    private String name;

    private Long theaterId;

}
