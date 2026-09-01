package com.cinereserve.cine_reserve.controller;

import com.cinereserve.cine_reserve.dto.CreateScreenRequest;
import com.cinereserve.cine_reserve.model.Screen;
import com.cinereserve.cine_reserve.service.ScreenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/screens")
public class ScreenController {

    private final ScreenService screenService;

    public ScreenController(ScreenService screenService) {
        this.screenService = screenService;
    }

    @PostMapping
    public Screen createScreen(@RequestBody CreateScreenRequest request) {
        return screenService.createScreen(
                request.getName(),
                request.getTheaterId()
        );
    }
}
