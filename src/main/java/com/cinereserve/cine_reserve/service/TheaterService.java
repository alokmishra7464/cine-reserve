package com.cinereserve.cine_reserve.service;

import com.cinereserve.cine_reserve.model.Screen;
import com.cinereserve.cine_reserve.model.Theater;
import com.cinereserve.cine_reserve.repository.TheaterRepository;
import org.springframework.stereotype.Service;

@Service
public class TheaterService {

    private final TheaterRepository theaterRepository;

    public TheaterService(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

}
