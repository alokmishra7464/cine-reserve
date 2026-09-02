package com.cinereserve.cine_reserve.controller;

import com.cinereserve.cine_reserve.exception.MovieNotFoundException;
import com.cinereserve.cine_reserve.model.Movie;
import com.cinereserve.cine_reserve.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovieService movieService;

    @Test
    void shouldCreateMovie() throws Exception {

        Movie movie = new Movie();
        movie.setTitle("The Amazing Spider-man");
        movie.setId(1L);

        when(movieService.createMovie("The Amazing Spider-man"))
                .thenReturn(movie);

        mockMvc.perform(
                post("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title":"The Amazing Spider-man"
                                }
                                """)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("The Amazing Spider-man"));


        verify(movieService).createMovie("The Amazing Spider-man");

    }

    @Test
    void shouldReturnAllMovies() throws Exception {
        Movie movie1 = new Movie();
        movie1.setId(1L);
        movie1.setTitle("The Amazing Spider-Man");

        Movie movie2 = new Movie();
        movie2.setId(2L);
        movie2.setTitle("Avengers");

        when(movieService.getMovies())
                .thenReturn(List.of(movie1, movie2));

        mockMvc.perform(
                get("/api/movies")
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("The Amazing Spider-Man"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Avengers"));

        verify(movieService).getMovies();
    }

    @Test
    void shouldReturnMovieById() throws Exception {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Avengers");

        when(movieService.getMovieById(1L))
                .thenReturn(movie);

        mockMvc.perform(
                get("/api/movies/1")
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Avengers"));


        verify(movieService).getMovieById(1L);
    }

    @Test
    void shouldReturn404WhenMethodNotFound() throws Exception {

        when(movieService.getMovieById(999L))
                .thenThrow(new MovieNotFoundException("Movie not found"));

        mockMvc.perform(
                get("/api/movies/999")
        ).andExpect(status().isNotFound())
                .andExpect(content().string("Movie not found"));

        verify(movieService).getMovieById(999L);
    }
}
