package org.example.foodbudgetbackendspring.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.foodbudgetbackendspring.user.dto.PasswordResetRequest;
import org.example.foodbudgetbackendspring.user.dto.RegisterRequest;
import org.example.foodbudgetbackendspring.user.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/register-verify")
    public ResponseEntity<?> verifyRegistration(@RequestParam String code, @RequestParam String email){
        userService.verifyRegistration(code, email);
        return ResponseEntity.ok("Account activated");
    }

    @PostMapping("/resend-code")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email){
        userService.resendVerificationCode(email);
        return ResponseEntity.ok("Issued email transfer");
    }

    @PostMapping("/send-password-reset-code")
    public ResponseEntity<?> sendPasswordResetCode(@RequestParam String email){
        userService.sendPasswordResetEmail(email);
        return ResponseEntity.ok("Issued email transfer");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPasswordWithVerificationCode(@RequestParam PasswordResetRequest request){
        userService.resetPassword(request);
        return ResponseEntity.ok("Password reset successful");
    }
}