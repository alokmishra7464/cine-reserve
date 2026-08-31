package com.cinereserve.cine_reserve.service;

import com.cinereserve.cine_reserve.model.Movie;
import com.cinereserve.cine_reserve.model.Screen;
import com.cinereserve.cine_reserve.model.Show;
import com.cinereserve.cine_reserve.repository.MovieRepository;
import com.cinereserve.cine_reserve.repository.ScreenRepository;
import com.cinereserve.cine_reserve.repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ShowService {

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;

    public ShowService(
            ShowRepository showRepository,
            MovieRepository movieRepository,
            ScreenRepository screenRepository)
    {
        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
        this.screenRepository = screenRepository;
    }

    public Show createShow(
            Long movieId,
            Long screenId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            BigDecimal price)
    {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie Not Found !!!"));

        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() -> new RuntimeException("Screen Not Found !!!"));

        if(!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("Start time must be before End time");
        }

        boolean overlapping = showRepository.existsByScreenIdAndStartTimeBeforeAndEndTimeAfter(
                screenId,
                endTime,
                startTime
        );

        if(overlapping) {
            throw new IllegalStateException("Screen already has a show during this time");
        }

        Show show = new Show();
        show.setMovie(movie);
        show.setScreen(screen);
        show.setStartTime(startTime);
        show.setEndTime(endTime);
        show.setPrice(price);

        return showRepository.save(show);

    }
}
