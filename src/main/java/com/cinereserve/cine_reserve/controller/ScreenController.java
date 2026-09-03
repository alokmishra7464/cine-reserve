package com.cinereserve.cine_reserve.controller;

import com.cinereserve.cine_reserve.dto.CreateScreenRequest;
import com.cinereserve.cine_reserve.model.Screen;
import com.cinereserve.cine_reserve.service.ScreenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screens")
public class ScreenController {

    private final ScreenService screenService;

    public ScreenController(ScreenService screenService) {
        this.screenService = screenService;
    }

    @GetMapping
    public List<Screen> getScreens() {
        return screenService.getScreens();
    }

    @GetMapping("/{id}")
    public Screen getScreenById(@PathVariable Long id) {
        return screenService.getScreenById(id);
    }

    @PostMapping
    public Screen createScreen(@RequestBody CreateScreenRequest request) {
        return screenService.createScreen(
                request.getName(),
                request.getTheaterId()
        );
    }
}
