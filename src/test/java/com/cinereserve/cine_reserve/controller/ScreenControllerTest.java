package com.cinereserve.cine_reserve.controller;

import com.cinereserve.cine_reserve.model.Screen;
import com.cinereserve.cine_reserve.service.ScreenService;
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

@WebMvcTest(ScreenController.class)
public class ScreenControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ScreenService screenService;

    @Test
    void shouldCreateScreen() throws Exception {

        Screen screen = new Screen();
        screen.setId(1L);
        screen.setName("Test Screen");

        when(screenService.createScreen("Test Screen", 2L))
                .thenReturn(screen);

        mockMvc.perform(
                post("/api/screens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name":"Test Screen",
                                    "theaterId":2
                                }
                                """)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Screen"));

        verify(screenService).createScreen("Test Screen", 2L);

    }
}
