package com.cinereserve.cine_reserve.controller;

import com.cinereserve.cine_reserve.dto.ShowResponse;
import com.cinereserve.cine_reserve.model.Movie;
import com.cinereserve.cine_reserve.model.Screen;
import com.cinereserve.cine_reserve.model.Show;
import com.cinereserve.cine_reserve.service.ShowService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShowController.class)
public class ShowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ShowService showService;

    @Test
    void shouldCreateShow() throws Exception {

        // Arrange

        LocalDateTime startTime =
                LocalDateTime.of(2026, 9, 1, 19, 0);

        LocalDateTime endTime =
                LocalDateTime.of(2026, 9, 1, 21, 0);

        BigDecimal price =
                new BigDecimal("250.00");

        Movie movie = new Movie();
        movie.setId(2L);
        movie.setTitle("The Amazing Spider-Man 2");

        Screen screen = new Screen();
        screen.setId(3L);
        screen.setName("Screen 1");

        ShowResponse response = ShowResponse.builder()
                .id(1L)
                .movieTitle("The Amazing Spider-Man 2")
                .screenId(3L)
                .screenName("Screen 1")
                .theaterName("DB Mall")
                .startTime(LocalDateTime.of(2026, 9, 1, 19, 0))
                .endTime(LocalDateTime.of(2026, 9, 1, 21, 0))
                .price(new BigDecimal("250.00"))
                .build();


        when(showService.createShow(
                2L,
                3L,
                startTime,
                endTime,
                price
        )).thenReturn(response);


        // Act

        mockMvc.perform(
                        post("/api/shows")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "movieId": 2,
                                    "screenId": 3,
                                    "startTime": "2026-09-01T19:00:00",
                                    "endTime": "2026-09-01T21:00:00",
                                    "price": 250.00
                                }
                                """)
                )


                // Assert

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.startTime")
                        .value("2026-09-01T19:00:00"))
                .andExpect(jsonPath("$.endTime")
                        .value("2026-09-01T21:00:00"))
                .andExpect(jsonPath("$.price").value(250.00))
                .andExpect(jsonPath("$.movieTitle")
                        .value("The Amazing Spider-Man 2"))
                .andExpect(jsonPath("$.screenId").value(3))
                .andExpect(jsonPath("$.screenName")
                        .value("Screen 1"));


        // Verify

        verify(showService).createShow(
                2L,
                3L,
                startTime,
                endTime,
                price
        );
    }
}