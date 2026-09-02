package com.cinereserve.cine_reserve.controller;

import com.cinereserve.cine_reserve.model.Theater;
import com.cinereserve.cine_reserve.service.TheaterService;
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
}
