package com.cinereserve.cine_reserve.service;

import com.cinereserve.cine_reserve.exception.TheaterNotFoundException;
import com.cinereserve.cine_reserve.model.Screen;
import com.cinereserve.cine_reserve.model.Theater;
import com.cinereserve.cine_reserve.repository.TheaterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterService {

    private final TheaterRepository theaterRepository;

    public TheaterService(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    public Theater createTheater(String name, String address, String city) {
        Theater theater = new Theater();
        theater.setName(name);
        theater.setAddress(address);
        theater.setCity(city);

        return theaterRepository.save(theater);
    }

    public List<Theater> getTheaters() {
        return theaterRepository.findAll();
    }

    public Theater getTheaterById(Long id) {
        return theaterRepository.findById(id).orElseThrow(() -> new TheaterNotFoundException("Theater not found"));
    }
}
