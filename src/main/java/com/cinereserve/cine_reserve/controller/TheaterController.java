package com.cinereserve.cine_reserve.controller;

import com.cinereserve.cine_reserve.dto.CreateTheaterRequest;
import com.cinereserve.cine_reserve.model.Theater;
import com.cinereserve.cine_reserve.service.TheaterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theaters")
public class TheaterController {

    private final TheaterService theaterService;

    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @GetMapping
    public List<Theater> getTheaters() {
        return theaterService.getTheaters();
    }

    @GetMapping("/{id}")
    public Theater getTheaterById(@PathVariable Long id) {
        return theaterService.getTheaterById(id);
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
