package com.cinereserve.cine_reserve.controller;

import com.cinereserve.cine_reserve.dto.CreateSeatRequest;
import com.cinereserve.cine_reserve.model.Seat;
import com.cinereserve.cine_reserve.service.SeatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping
    public List<Seat> getSeats() {
        return seatService.getSeats();
    }

    @PostMapping
    public Seat createSeat(@RequestBody CreateSeatRequest request) {
        return seatService.createSeat(
                request.getRowLabel(),
                request.getSeatNumber(),
                request.getSeatType(),
                request.getScreenId()
        );
    }
}
