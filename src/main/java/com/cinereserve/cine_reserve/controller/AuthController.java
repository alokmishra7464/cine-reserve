package com.cinereserve.cine_reserve.controller;

import com.cinereserve.cine_reserve.dto.LoginRequest;
import com.cinereserve.cine_reserve.dto.LoginResponse;
import com.cinereserve.cine_reserve.dto.RegisterRequest;
import com.cinereserve.cine_reserve.dto.UserResponse;
import com.cinereserve.cine_reserve.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
