package com.cinereserve.cine_reserve.service;

import com.cinereserve.cine_reserve.exception.ScreenNotFoundException;
import com.cinereserve.cine_reserve.exception.TheaterNotFoundException;
import com.cinereserve.cine_reserve.model.Screen;
import com.cinereserve.cine_reserve.model.Theater;
import com.cinereserve.cine_reserve.repository.ScreenRepository;
import com.cinereserve.cine_reserve.repository.TheaterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreenService {

    private final ScreenRepository screenRepository;
    private final TheaterRepository theaterRepository;

    public ScreenService(
            ScreenRepository screenRepository,
            TheaterRepository theaterRepository
    )
    {
        this.screenRepository = screenRepository;
        this.theaterRepository = theaterRepository;
    }

    public Screen createScreen(String name, Long theaterId) {

        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> new TheaterNotFoundException("Theater not found"));

        Screen screen = new Screen();
        screen.setName(name);
        screen.setTheater(theater);

        return screenRepository.save(screen);
    }

    public List<Screen> getScreens() {
        return screenRepository.findAll();
    }

    public Screen getScreenById(Long id) {
        return screenRepository.findById(id).orElseThrow(() -> new ScreenNotFoundException("Screen not found"));
    }
}
