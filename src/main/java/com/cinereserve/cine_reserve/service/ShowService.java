package com.cinereserve.cine_reserve.service;

import com.cinereserve.cine_reserve.dto.ShowResponse;
import com.cinereserve.cine_reserve.exception.*;
import com.cinereserve.cine_reserve.model.Movie;
import com.cinereserve.cine_reserve.model.Screen;
import com.cinereserve.cine_reserve.model.Show;
import com.cinereserve.cine_reserve.repository.MovieRepository;
import com.cinereserve.cine_reserve.repository.ScreenRepository;
import com.cinereserve.cine_reserve.repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    private ShowResponse toResponse(Show show) {
        return ShowResponse.builder()
                .id(show.getId())
                .movieTitle(show.getMovie().getTitle())
                .screenId(show.getScreen().getId())
                .screenName(show.getScreen().getName())
                .theaterName(show.getScreen().getTheater().getName())
                .startTime(show.getStartTime())
                .endTime(show.getEndTime())
                .price(show.getPrice())
                .build();
    }

    public ShowResponse createShow(
            Long movieId,
            Long screenId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            BigDecimal price)
    {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found"));

        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() -> new ScreenNotFoundException("Screen Not Found !!!"));

        if(!startTime.isBefore(endTime)) {
            throw new InvalidShowTimeException("Start time must be before End time");
        }

        boolean overlapping = showRepository.existsByScreenIdAndStartTimeBeforeAndEndTimeAfter(
                screenId,
                endTime,
                startTime
        );

        if(overlapping) {
            throw new ShowConflictException("Screen already has a show during this time");
        }

        Show show = new Show();
        show.setMovie(movie);
        show.setScreen(screen);
        show.setStartTime(startTime);
        show.setEndTime(endTime);
        show.setPrice(price);

        Show savedShow =  showRepository.save(show);
        return toResponse(show);

    }

    public List<ShowResponse> getShows() {
        return showRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ShowResponse getShowById(Long id) {
        Show show =  showRepository.findById(id).orElseThrow(() -> new ShowNotFoundException("Show not found"));
        return toResponse(show);
    }
}
