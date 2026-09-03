package com.cinereserve.cine_reserve.service;

import com.cinereserve.cine_reserve.enums.SeatType;
import com.cinereserve.cine_reserve.exception.ScreenNotFoundException;
import com.cinereserve.cine_reserve.model.Screen;
import com.cinereserve.cine_reserve.model.Seat;
import com.cinereserve.cine_reserve.repository.ScreenRepository;
import com.cinereserve.cine_reserve.repository.SeatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SeatServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private ScreenRepository screenRepository;

    @InjectMocks
    private SeatService seatService;

    @Test
    void shouldFindScreenAndSaveSeat() {

        Screen screen = new Screen();
        screen.setId(3L);
        screen.setName("Screen 1");

        when(screenRepository.findById(3L))
                .thenReturn(Optional.of(screen));

        when(seatRepository.save(any(Seat.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Seat result = seatService.createSeat(
                "A",
                1,
                SeatType.REGULAR,
                3L
        );

        ArgumentCaptor<Seat> seatCaptor =
                ArgumentCaptor.forClass(Seat.class);

        verify(seatRepository).save(seatCaptor.capture());

        Seat capturedSeat = seatCaptor.getValue();

        assertEquals("A", capturedSeat.getRowLabel());
        assertEquals(1, capturedSeat.getSeatNumber());
        assertEquals(SeatType.REGULAR, capturedSeat.getSeatType());
        assertEquals(screen, capturedSeat.getScreen());
    }


    @Test
    void shouldThrowErrorWhenScreenNotFound() {

        when(screenRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                ScreenNotFoundException.class,
                () -> seatService.createSeat(
                        "A",
                        1,
                        SeatType.REGULAR,
                        999L
                )
        );

        verify(seatRepository, never())
                .save(any(Seat.class));
    }
}
