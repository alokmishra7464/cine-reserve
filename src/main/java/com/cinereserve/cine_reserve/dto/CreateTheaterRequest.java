package com.cinereserve.cine_reserve.dto;

import lombok.Data;

@Data
public class CreateTheaterRequest {
    private String name;
    private String address;
    private String city;
}
