package com.cinereserve.cine_reserve.service;

import com.cinereserve.cine_reserve.dto.ShowResponse;
import com.cinereserve.cine_reserve.dto.ShowSeatResponse;
import com.cinereserve.cine_reserve.enums.ShowSeatStatus;
import com.cinereserve.cine_reserve.exception.*;
import com.cinereserve.cine_reserve.model.*;
import com.cinereserve.cine_reserve.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final SeatRepository seatRepository;
    private final ShowSeatRepository showSeatRepository;

    public ShowService(
            ShowRepository showRepository,
            MovieRepository movieRepository,
            ScreenRepository screenRepository,
            ShowSeatRepository showSeatRepository,
            SeatRepository seatRepository)
    {
        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
        this.screenRepository = screenRepository;
        this.showSeatRepository = showSeatRepository;
        this.seatRepository = seatRepository;
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

    private ShowSeatResponse toShowSeatResponse(ShowSeat showSeat) {
        return ShowSeatResponse.builder()
                .id(showSeat.getId())
                .rowLabel(showSeat.getSeat().getRowLabel())
                .seatNumber(showSeat.getSeat().getSeatNumber())
                .seatType(showSeat.getSeat().getSeatType())
                .showSeatStatus(showSeat.getStatus()).build();
    }

    @Transactional
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

        //find seats for that screen and make them available(construct showSeat of it)
        List<Seat> seats = seatRepository.findByScreenId(screenId);
        for(Seat seat : seats) {
            ShowSeat showSeat = new ShowSeat();
            showSeat.setShow(show);
            showSeat.setSeat(seat);
            showSeat.setStatus(ShowSeatStatus.AVAILABLE);

            showSeatRepository.save(showSeat);
        }

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

    public List<ShowSeatResponse> getShowSeats(Long showId) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ShowNotFoundException("Show not found"));

        List<ShowSeat> showSeats = showSeatRepository.findByShowId(show.getId());

        return showSeats.stream()
                .map(this::toShowSeatResponse)
                .toList();
    }
}
