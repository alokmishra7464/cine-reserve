package com.cinereserve.cine_reserve.controller;

import com.cinereserve.cine_reserve.dto.CreateTheaterRequest;
import com.cinereserve.cine_reserve.model.Theater;
import com.cinereserve.cine_reserve.service.TheaterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/theaters")
public class TheaterController {

    private final TheaterService theaterService;

    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @PostMapping
    public Theater createTheater(@RequestBody CreateTheaterRequest request) {
        return theaterService.createTheater(
                request.getName(),
                request.getAddress(),
                request.getCity()
        );
    }


}
