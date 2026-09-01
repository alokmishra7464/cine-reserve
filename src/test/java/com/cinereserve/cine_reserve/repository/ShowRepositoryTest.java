package com.cinereserve.cine_reserve.repository;

import com.cinereserve.cine_reserve.model.Movie;
import com.cinereserve.cine_reserve.model.Screen;
import com.cinereserve.cine_reserve.model.Show;
import com.cinereserve.cine_reserve.model.Theater;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ShowRepositoryTest  {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private TheaterRepository theaterRepository;


    @Test
    void shouldDetectOverlappingShow() {

        Movie movie = new Movie();
        movie.setTitle("Test Movie");

        movieRepository.save(movie);

        Theater theater = new Theater();
        theater.setAddress("test add");
        theater.setCity("test city");
        theater.setName("test theater");

        theaterRepository.save(theater);

        Screen screen = new Screen();
        screen.setName("Test Screen");
        screen.setTheater(theater);

        screenRepository.save(screen);


        Show existingShow = new Show();
        existingShow.setMovie(movie);
        existingShow.setScreen(screen);
        existingShow.setStartTime(
                LocalDateTime.of(2026, 9, 1, 18, 0)
        );
        existingShow.setEndTime(
                LocalDateTime.of(2026, 9, 1, 20, 0)
        );

        existingShow.setPrice(
                new BigDecimal("250.00")
        );

        showRepository.save(existingShow);

        boolean result =
                showRepository.existsByScreenIdAndStartTimeBeforeAndEndTimeAfter(
                        screen.getId(),
                        LocalDateTime.of(2026, 9, 1, 21, 0),
                        LocalDateTime.of(2026, 9, 1, 19, 0)
                );

        System.out.println(result + " for test 1");

        assertTrue(result);
    }

    @Test
    void shouldAllowNonOverlappingShow() {
        Movie movie = new Movie();
        movie.setTitle("Test Movie 2");

        movieRepository.save(movie);

        Theater theater = new Theater();
        theater.setAddress("test add 2");
        theater.setCity("test city 2");
        theater.setName("test theater 2");

        theaterRepository.save(theater);

        Screen screen = new Screen();
        screen.setName("Test Screen 2");
        screen.setTheater(theater);

        screenRepository.save(screen);


        Show existingShow = new Show();
        existingShow.setMovie(movie);
        existingShow.setScreen(screen);
        existingShow.setStartTime(
                LocalDateTime.of(2026, 9, 1, 19, 0)
        );
        existingShow.setEndTime(
                LocalDateTime.of(2026, 9, 1, 21, 0)
        );

        existingShow.setPrice(
                new BigDecimal("200.00")
        );

        showRepository.save(existingShow);

        boolean result =
                showRepository.existsByScreenIdAndStartTimeBeforeAndEndTimeAfter(
                        screen.getId(),
                        LocalDateTime.of(2026, 9, 1, 21, 0),
                        LocalDateTime.of(2026, 9, 1, 22, 0)
                );

        System.out.println(result + " for test 2");
        assertFalse(result);
    }
}