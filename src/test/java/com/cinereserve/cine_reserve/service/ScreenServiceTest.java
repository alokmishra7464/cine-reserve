package com.cinereserve.cine_reserve.service;

import com.cinereserve.cine_reserve.model.Screen;
import com.cinereserve.cine_reserve.model.Theater;
import com.cinereserve.cine_reserve.repository.ScreenRepository;
import com.cinereserve.cine_reserve.repository.TheaterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ScreenServiceTest {

    @Mock
    private ScreenRepository screenRepository;

    @Mock
    private TheaterRepository theaterRepository;

    @InjectMocks
    private ScreenService screenService;

    @Test
    void shouldFindTheaterAndSaveScreen() {

        Theater theater = new Theater();
        theater.setId(1L);
        theater.setName("Test Theater");
        theater.setAddress("Test address");
        theater.setCity("Test city");

        when(theaterRepository.findById(1L))
                .thenReturn(Optional.of(theater));

        String name = "Test Screen";
        Long id = 1L;

        when(screenRepository.save(any(Screen.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        screenService.createScreen(name, id);

        verify(theaterRepository).findById(1L);

        ArgumentCaptor<Screen> screenCaptor = ArgumentCaptor.forClass(Screen.class);

        verify(screenRepository).save(screenCaptor.capture());

        Screen capturedScreen = screenCaptor.getValue();

        assertEquals(name, capturedScreen.getName());
        assertEquals(theater, capturedScreen.getTheater());
    }
}
