package com.cinereserve.cine_reserve.service;

import com.cinereserve.cine_reserve.dto.MovieResponse;
import com.cinereserve.cine_reserve.exception.MovieNotFoundException;
import com.cinereserve.cine_reserve.model.Movie;
import com.cinereserve.cine_reserve.repository.MovieRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieResponse> getMovies() {
        return movieRepository.findAll()
                .stream()
                .map(movie -> {
                    return new MovieResponse(movie.getId()
                            ,movie.getTitle()
                    );
                }).toList();
    }

    public MovieResponse createMovie(String title) {
        Movie movie = new Movie();
        movie.setTitle(title);
        Movie savedMovie =  movieRepository.save(movie);
        return new MovieResponse(savedMovie.getId(),
                savedMovie.getTitle()
        );
    }

    @Cacheable(value = "movies", key = "#id")
    public MovieResponse getMovieById(Long id) {
        Movie movie =  movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found"));
        return new MovieResponse(movie.getId() ,movie.getTitle());
    }

    @CacheEvict(value = "movies", key = "#id")
    public MovieResponse updateMovie(Long id, String title) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() ->
                        new MovieNotFoundException("Movie not found"));

        movie.setTitle(title);

        Movie savedMovie = movieRepository.save(movie);
        return new MovieResponse(savedMovie.getId(), savedMovie.getTitle());
    }

    @CacheEvict(value = "movies", key = "#id")
    public void deleteMovie(Long id) {

        if (!movieRepository.existsById(id)) {
            throw new MovieNotFoundException("Movie not found");
        }

        movieRepository.deleteById(id);
    }
}
