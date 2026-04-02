package org.example.foodbudgetbackendspring.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.foodbudgetbackendspring.user.dto.PasswordResetRequest;
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

    @PostMapping("/register-verify")
    public ResponseEntity<?> verifyRegistration(@RequestParam String code){
        userService.verifyRegistration(code);
        return ResponseEntity.ok("Account activated");
    }

    @PostMapping("/resend-code")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email){
        userService.resendVerificationCode(email);
        return ResponseEntity.ok("Email resent");
    }

    @PostMapping("/send-password-reset-code")
    public ResponseEntity<?> sendPasswordResetCode(@RequestParam String email){
        userService.sendPasswordResetEmail(email);
        return ResponseEntity.ok("Email sent");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPasswordWithVerificationCode(@RequestParam PasswordResetRequest request){
        userService.resetPassword(request);
        return ResponseEntity.ok("Password reset successful");
    }
}