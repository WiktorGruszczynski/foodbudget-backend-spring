package org.example.foodbudgetbackendspring.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.foodbudgetbackendspring.user.dto.RegisterRequest;
import org.example.foodbudgetbackendspring.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.status(201).build();
    }

}