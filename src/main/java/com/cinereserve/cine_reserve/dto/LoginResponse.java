package com.cinereserve.cine_reserve.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String token;
    private Long userId;
    private String name;
    private String email;
}
