package com.cinereserve.cine_reserve.service;

import com.cinereserve.cine_reserve.enums.SeatType;
import com.cinereserve.cine_reserve.exception.ScreenNotFoundException;
import com.cinereserve.cine_reserve.model.Screen;
import com.cinereserve.cine_reserve.model.Seat;
import com.cinereserve.cine_reserve.repository.ScreenRepository;
import com.cinereserve.cine_reserve.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final ScreenRepository screenRepository;

    public SeatService(SeatRepository seatRepository, ScreenRepository screenRepository) {
        this.seatRepository = seatRepository;
        this.screenRepository = screenRepository;
    }


    public Seat createSeat(String rowLabel, Integer seatNumber, SeatType seatType, Long screenId) {

        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() -> new ScreenNotFoundException("Screen not found"));

        Seat seat = new Seat();
        seat.setScreen(screen);
        seat.setSeatNumber(seatNumber);
        seat.setRowLabel(rowLabel);
        seat.setSeatType(seatType);

        return seatRepository.save(seat);
    }

    public List<Seat> getSeats() {
        return seatRepository.findAll();
    }
}
