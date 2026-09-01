package com.cinereserve.cine_reserve.service;

import com.cinereserve.cine_reserve.model.Theater;
import com.cinereserve.cine_reserve.repository.TheaterRepository;
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
public class TheaterServiceTest {

    @Mock
    private TheaterRepository theaterRepository;

    @InjectMocks
    private TheaterService theaterService;

    @Test
    void shouldCreateTheater() {

        String name = "Theater 1";
        String address = "ABC Delhi";
        String city = "Delhi";

        when(theaterRepository.save(any(Theater.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        theaterService.createTheater(name, address, city);

        ArgumentCaptor<Theater> theaterCaptor = ArgumentCaptor.forClass(Theater.class);

        verify(theaterRepository).save(theaterCaptor.capture());

        Theater capturedTheater = theaterCaptor.getValue();

        assertEquals(name, capturedTheater.getName());
        assertEquals(address, capturedTheater.getAddress());
        assertEquals(city, capturedTheater.getCity());
    }
}
