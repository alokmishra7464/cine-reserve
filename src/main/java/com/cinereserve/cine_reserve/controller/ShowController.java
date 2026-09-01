package com.cinereserve.cine_reserve.controller;

import com.cinereserve.cine_reserve.dto.CreateShowRequest;
import com.cinereserve.cine_reserve.model.Show;
import com.cinereserve.cine_reserve.service.ShowService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shows")
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @PostMapping
    public Show createShow(@RequestBody CreateShowRequest request) {
        return showService.createShow(
                request.getMovieId(),
                request.getScreenId(),
                request.getStartTime(),
                request.getEndTime(),
                request.getPrice()
        );
    }
}
