package com.cinereserve.cine_reserve.service;

import com.cinereserve.cine_reserve.dto.BookingRequest;
import com.cinereserve.cine_reserve.dto.BookingResponse;
import com.cinereserve.cine_reserve.enums.BookingStatus;
import com.cinereserve.cine_reserve.enums.ShowSeatStatus;
import com.cinereserve.cine_reserve.exception.ResourceNotFoundException;
import com.cinereserve.cine_reserve.exception.SeatConflictException;
import com.cinereserve.cine_reserve.exception.SeatNotFoundException;
import com.cinereserve.cine_reserve.exception.ShowNotFoundException;
import com.cinereserve.cine_reserve.model.*;
import com.cinereserve.cine_reserve.repository.BookingRepository;
import com.cinereserve.cine_reserve.repository.BookingSeatRepository;
import com.cinereserve.cine_reserve.repository.ShowRepository;
import com.cinereserve.cine_reserve.repository.ShowSeatRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ShowRepository showRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final ShowSeatRepository showSeatRepository;

    public BookingService(
            BookingRepository bookingRepository,
            ShowRepository showRepository,
            BookingSeatRepository bookingSeatRepository,
            ShowSeatRepository showSeatRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.bookingSeatRepository = bookingSeatRepository;
    }

    @Transactional
    public BookingResponse createBooking(BookingRequest request) {

        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        User user = (User) authentication.getPrincipal();

        Show show = showRepository.findById(request.getShowId())
                .orElseThrow(() -> new ShowNotFoundException("Show not found"));

        List<ShowSeat> showSeats = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for(Long seatId : request.getSeatIds()) {

            ShowSeat showSeat = showSeatRepository
                    .findByShowIdAndSeatId(show.getId(), seatId)
                    .orElseThrow(() -> new SeatNotFoundException("Seat not found for this show"));


            if(showSeat.getStatus() != ShowSeatStatus.AVAILABLE) {
                throw new SeatConflictException("Seat " + seatId + " is not available");
            }

            showSeats.add(showSeat);

            totalAmount = totalAmount.add(show.getPrice());

        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setTotalAmount(totalAmount);
        booking.setCreatedAt(LocalDateTime.now());

        booking = bookingRepository.save(booking);

        List<Long> bookedSeatIds = new ArrayList<>();

        for(ShowSeat showSeat : showSeats) {
            BookingSeat bookingSeat = new BookingSeat();

            bookingSeat.setBooking(booking);
            bookingSeat.setSeat(showSeat.getSeat());

            bookingSeatRepository.save(bookingSeat);

            showSeat.setStatus(ShowSeatStatus.BOOKED);

            bookedSeatIds.add(showSeat.getSeat().getId());
        }

        return BookingResponse.builder()
                .bookingId(booking.getId())
                .showId(show.getId())
                .status(booking.getStatus())
                .totalAmount(booking.getTotalAmount())
                .createdAt(booking.getCreatedAt())
                .seatIds(bookedSeatIds)
                .build();
    }

    public BookingResponse getBookingById(Long bookingId) throws AccessDeniedException {

        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if(!booking.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not allowed to access this booking");
        }

        List<Long> seatIds = bookingSeatRepository
                .findByBookingId(bookingId)
                .stream()
                .map(bookingSeat -> bookingSeat.getSeat().getId())
                .toList();

        return BookingResponse.builder()
                .bookingId(booking.getId())
                .showId(booking.getShow().getId())
                .status(booking.getStatus())
                .totalAmount(booking.getTotalAmount())
                .createdAt(booking.getCreatedAt())
                .seatIds(seatIds)
                .build();
    }
}
