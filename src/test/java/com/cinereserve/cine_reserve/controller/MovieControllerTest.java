package com.cinereserve.cine_reserve.controller;

import com.cinereserve.cine_reserve.model.Movie;
import com.cinereserve.cine_reserve.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
}
