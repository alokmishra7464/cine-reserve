package com.cinereserve.cine_reserve;

import com.cinereserve.cine_reserve.model.Movie;
import com.cinereserve.cine_reserve.model.Screen;
import com.cinereserve.cine_reserve.model.Show;
import com.cinereserve.cine_reserve.model.Theater;
import com.cinereserve.cine_reserve.repository.MovieRepository;
import com.cinereserve.cine_reserve.repository.ScreenRepository;
import com.cinereserve.cine_reserve.repository.ShowRepository;
import com.cinereserve.cine_reserve.service.ShowService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShowServiceTest {

    @Mock
    private ShowRepository showRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ScreenRepository screenRepository;

    @InjectMocks
    private ShowService showService;

    @Test
    void shouldCreateShowWhenDetailsAreValid() {


        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Test Movie");

        Screen screen = new Screen();
        screen.setName("Screen 1");
        screen.setId(2L);

        LocalDateTime starTime = LocalDateTime.of(2026, 9,1,18,0);
        LocalDateTime endTime = LocalDateTime.of(2026, 9, 1,20,0);

        BigDecimal price = new BigDecimal("250.00");

        when(movieRepository.findById(1L))
                .thenReturn(Optional.of(movie));

        when(screenRepository.findById(2L))
                .thenReturn(Optional.of(screen));

        when(showRepository.existsByScreenIdAndStartTimeBeforeAndEndTimeAfter(
                2L,
                endTime,
                starTime
        )).thenReturn(false);

        Show savedShow = new Show();
        savedShow.setId(3L);
        savedShow.setMovie(movie);
        savedShow.setScreen(screen);
        savedShow.setStartTime(starTime);
        savedShow.setEndTime(endTime);
        savedShow.setPrice(price);

        when(showRepository.save(any(Show.class)))
                .thenReturn(savedShow);

        Show result = showService.createShow(
                1L,
                2L,
                starTime,
                endTime,
                price
        );

        assertNotNull(result);
        assertEquals(3L, result.getId());

        ArgumentCaptor<Show> showCaptor = ArgumentCaptor.forClass(Show.class);

        verify(showRepository).save(showCaptor.capture());

        Show capturedShow = showCaptor.getValue();

        assertEquals(movie, capturedShow.getMovie());
        assertEquals(screen, capturedShow.getScreen());
        assertEquals(starTime, capturedShow.getStartTime());
        assertEquals(endTime, capturedShow.getEndTime());
        assertEquals(price, capturedShow.getPrice());

    }

    @Test
    void shouldThrowExceptionWhenMovieNotFound() {

        when(movieRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> showService.createShow(
                        999L,
                        2L,
                        LocalDateTime.of(2026,9,1,18,0),
                        LocalDateTime.of(2026,9,1,20,0),
                        new BigDecimal("250.00")
                )
        );

        verify(showRepository, never()).save(any(Show.class));
    }

    @Test
    void shouldThrowExceptionWhenScreenNotFound() {

        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Test movie");

        when(movieRepository.findById(1L))
                .thenReturn(Optional.of(movie));

        when(screenRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> showService.createShow(
                        1L,
                        999L,
                        LocalDateTime.of(2026,9,1,18,0),
                        LocalDateTime.of(2026,9,1,20,0),
                        new BigDecimal("250.00")
                )
        );

        verify(showRepository, never()).save(any(Show.class));
    }

    @Test
    void shouldThrowExceptionWhenStartTimeIsAfterEndTime() {

        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setId(1L);

        Screen screen = new Screen();
        screen.setId(2L);
        screen.setName("Test Screen");

        when(movieRepository.findById(1L))
                .thenReturn(Optional.of(movie));

        when(screenRepository.findById(2L))
                .thenReturn(Optional.of(screen));

        LocalDateTime startTime = LocalDateTime.of(2026, 9,1,20,0);
        LocalDateTime endTime = LocalDateTime.of(2026,9,1,18,0);

        assertThrows(
                IllegalArgumentException.class,
                () -> showService.createShow(
                        1L,
                        2L,
                        startTime,
                        endTime,
                        new BigDecimal("250.00")
                )
        );

        verify(showRepository, never()).save(any(Show.class));
    }

    @Test
    void shouldThrowExceptionWhenShowOverlapsExistingShow() {

        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Test Movie");

        Screen screen = new Screen();
        screen.setId(2L);
        screen.setName("Test Screen");

        LocalDateTime startTime = LocalDateTime.of(2026, 9,1,19,0);
        LocalDateTime endTime = LocalDateTime.of(2026, 9,1,21,0);

        when(movieRepository.findById(1L))
                .thenReturn(Optional.of(movie));

        when(screenRepository.findById(2L))
                .thenReturn(Optional.of(screen));

        when(showRepository.existsByScreenIdAndStartTimeBeforeAndEndTimeAfter(
                screen.getId(),
                endTime,
                startTime
        )).thenReturn(true);

        assertThrows(
                IllegalStateException.class,
                () -> showService.createShow(
                        1L,
                        2L,
                        startTime,
                        endTime,
                        new BigDecimal("250.00")
                )
        );

        verify(showRepository, never()).save(any(Show.class));
    }
}
