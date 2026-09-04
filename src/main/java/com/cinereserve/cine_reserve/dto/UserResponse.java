package com.cinereserve.cine_reserve.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class UserResponse {

    private Long id;
    private String name;
    private String email;
}
