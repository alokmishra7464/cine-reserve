package com.cinereserve.cine_reserve.service;

import com.cinereserve.cine_reserve.model.Movie;
import com.cinereserve.cine_reserve.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    void successMovieSaved() {

        String title = "The Amazing Spider-Man";

        when(movieRepository.save((any(Movie.class))))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Movie result = movieService.createMovie(title);

        assertEquals(title, result.getTitle());

        ArgumentCaptor<Movie> movieCaptor = ArgumentCaptor.forClass(Movie.class);

        verify(movieRepository).save(movieCaptor.capture());

        Movie capturedMovie = movieCaptor.getValue();

        assertEquals(title, capturedMovie.getTitle());

    }
}
