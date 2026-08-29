package com.cinereserve.cine_reserve.controller;

import com.cinereserve.cine_reserve.model.Movie;
import com.cinereserve.cine_reserve.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public List<Movie> getMovies() {
        return movieService.getMovies();
    }

    @PostMapping("/movies")
    public Movie createMovie(@RequestParam String title) {
        return movieService.createMovie(title);
    }

}
