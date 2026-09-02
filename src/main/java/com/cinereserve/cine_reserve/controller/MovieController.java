package com.cinereserve.cine_reserve.controller;

import com.cinereserve.cine_reserve.dto.CreateMovieRequest;
import com.cinereserve.cine_reserve.model.Movie;
import com.cinereserve.cine_reserve.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getMovies() {
        return movieService.getMovies();
    }

    @PostMapping
    public Movie createMovie(@RequestBody CreateMovieRequest request) {
        return movieService.createMovie(request.getTitle());
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

}
