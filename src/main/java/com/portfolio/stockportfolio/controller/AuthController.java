package com.portfolio.stockportfolio.controller;

import com.portfolio.stockportfolio.entity.User;
import com.portfolio.stockportfolio.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        String response = authService.register(user.getUsername(), user.getPassword());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        String response = authService.login(user.getUsername(), user.getPassword());
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException exception){
        return ResponseEntity.status(401).body(exception.getMessage());
    }

}
