package com.cinereserve.cine_reserve.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateMovieRequest {

    @NotBlank(message = "Title is required")
    private String title;
}
