package com.cinereserve.cine_reserve.controller;

import com.cinereserve.cine_reserve.dto.CreateShowRequest;
import com.cinereserve.cine_reserve.model.Show;
import com.cinereserve.cine_reserve.service.ShowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping
    public List<Show> getShows() {
        return showService.getShows();
    }

    @GetMapping("/{id}")
    public Show getShowById(@PathVariable Long id) {
        return showService.getShowById(id);
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
