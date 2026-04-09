package org.example.foodbudgetbackendspring.user;

import lombok.RequiredArgsConstructor;
import org.example.foodbudgetbackendspring.user.dto.AuthResponse;
import org.example.foodbudgetbackendspring.user.dto.PasswordResetRequest;
import org.example.foodbudgetbackendspring.user.dto.RegisterRequest;
import org.example.foodbudgetbackendspring.user.dto.VerifyCodeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.status(201).body(
                new AuthResponse("success")
        );
    }

    @PostMapping("/register-verify")
    public ResponseEntity<AuthResponse> verifyRegistration(@RequestParam String code, @RequestParam String email){
        userService.verifyRegistration(code, email);
        return ResponseEntity.ok(
                new AuthResponse("Account activated")
        );
    }

    @PostMapping("/resend-code")
    public ResponseEntity<AuthResponse> resendVerificationCode(@RequestParam String email){
        userService.resendVerificationCode(email);
        return ResponseEntity.ok(
                new AuthResponse("Issued email transfer")
        );
    }

    @PostMapping("/send-password-reset-code")
    public ResponseEntity<AuthResponse> sendPasswordResetCode(@RequestParam String email){
        userService.sendPasswordResetEmail(email);
        return ResponseEntity.ok(
                new AuthResponse("Issued email transfer")
        );
    }

    @PostMapping("/verify-code")
    public ResponseEntity<AuthResponse> verifyCode(@RequestBody VerifyCodeRequest request){
        userService.verifyCode(request);
        return  ResponseEntity.ok(new AuthResponse("Success"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<AuthResponse> resetPasswordWithVerificationCode(@RequestBody PasswordResetRequest request){
        userService.resetPassword(request);
        return ResponseEntity.ok(
                new AuthResponse("Reset password successful")
        );
    }

    @GetMapping("/validate-session")
    public ResponseEntity<?> isSessionValid(){
        return ResponseEntity.ok(
                new AuthResponse("success")
        );
    }
}