package com.cinereserve.cine_reserve.controller;

import com.cinereserve.cine_reserve.dto.CreateMovieRequest;
import com.cinereserve.cine_reserve.dto.MovieResponse;
import com.cinereserve.cine_reserve.model.Movie;
import com.cinereserve.cine_reserve.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public List<MovieResponse> getMovies() {
        return movieService.getMovies();
    }

    @PostMapping
    public MovieResponse createMovie(@Valid @RequestBody CreateMovieRequest request) {
        return movieService.createMovie(request.getTitle());
    }

    @GetMapping("/{id}")
    public MovieResponse getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @PutMapping("/{id}")
    public MovieResponse updateMovie(@PathVariable Long id, @RequestBody String title) {
        return movieService.updateMovie(id, title);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok().body("Movie with id: " + id + " is deleted");
    }

}
