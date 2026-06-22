package com.portfolio.stockportfolio.controller;

import com.portfolio.stockportfolio.dto.AuthRequest;
import com.portfolio.stockportfolio.dto.AuthResponse;
import com.portfolio.stockportfolio.ratelimit.RateLimit;
import com.portfolio.stockportfolio.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @RateLimit(limit=3, windowSeconds = 10)
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRequest authRequest){
        String response = authService.register(authRequest.username(), authRequest.password());
        return ResponseEntity.ok(new AuthResponse(response));
    }

    @RateLimit(limit=5, windowSeconds = 10)
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        String response = authService.login(authRequest.username(), authRequest.password());
        return ResponseEntity.ok(new AuthResponse(response));
    }
}
