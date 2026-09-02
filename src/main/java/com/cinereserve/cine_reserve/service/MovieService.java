package com.cinereserve.cine_reserve.service;

import com.cinereserve.cine_reserve.exception.MovieNotFoundException;
import com.cinereserve.cine_reserve.model.Movie;
import com.cinereserve.cine_reserve.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public Movie createMovie(String title) {
        Movie movie = new Movie();
        movie.setTitle(title);
        return movieRepository.save(movie);
    }


    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie not found"));
    }
}
