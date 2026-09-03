package com.cinereserve.cine_reserve.controller;

import com.cinereserve.cine_reserve.exception.TheaterNotFoundException;
import com.cinereserve.cine_reserve.model.Theater;
import com.cinereserve.cine_reserve.service.TheaterService;
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

@WebMvcTest(TheaterController.class)
public class TheaterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TheaterService theaterService;

    @Test
    void shouldCreateTheater() throws Exception {

        Theater theater = new Theater();
        theater.setId(1L);
        theater.setName("Test Theater");
        theater.setAddress("Test theater address");
        theater.setCity("Test city");

        when(theaterService.createTheater(
                "Test Theater",
                "Test theater address",
                "Test city"
        )).thenReturn(theater);

        mockMvc.perform(
                post("/api/theaters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Test Theater",
                                    "address": "Test theater address",
                                    "city": "Test city"
                                }
                                """)

        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Theater"))
                .andExpect(jsonPath("$.address").value("Test theater address"))
                .andExpect(jsonPath("$.city").value("Test city"));

        verify(theaterService).createTheater("Test Theater", "Test theater address", "Test city");
    }

    @Test
    void shouldReturnAllTheaters() throws Exception {
        Theater theater1 = new Theater();
        theater1.setId(1L);
        theater1.setName("DB Mall");
        theater1.setAddress("MP Nagar");
        theater1.setCity("Bhopal");

        Theater theater2 = new Theater();
        theater2.setId(2L);
        theater2.setName("Cinepolis");
        theater2.setAddress("Mall Road");
        theater2.setCity("Bhopal");

        when(theaterService.getTheaters())
                .thenReturn(List.of(theater1, theater2));

        mockMvc.perform(
                get("/api/theaters")
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("DB Mall"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Cinepolis"));

        verify(theaterService).getTheaters();
    }

    @Test
    void shouldReturnTheaterById() throws Exception {

        Theater theater = new Theater();
        theater.setId(4L);
        theater.setName("DB Mall");
        theater.setAddress("MP Nagar");
        theater.setCity("Bhopal");

        when(theaterService.getTheaterById(4L))
                .thenReturn(theater);

        mockMvc.perform(
                        get("/api/theaters/4")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.name").value("DB Mall"))
                .andExpect(jsonPath("$.address").value("MP Nagar"))
                .andExpect(jsonPath("$.city").value("Bhopal"));

        verify(theaterService).getTheaterById(4L);
    }

    @Test
    void shouldReturn404WhenTheaterNotFound() throws Exception {

        when(theaterService.getTheaterById(999L))
                .thenThrow(
                        new TheaterNotFoundException("Theater not found")
                );

        mockMvc.perform(
                        get("/api/theaters/999")
                )
                .andExpect(status().isNotFound())
                .andExpect(content().string("Theater not found"));

        verify(theaterService).getTheaterById(999L);
    }
}
