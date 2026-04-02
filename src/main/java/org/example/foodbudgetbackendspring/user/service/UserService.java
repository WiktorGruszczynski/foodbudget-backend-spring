package org.example.foodbudgetbackendspring.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.foodbudgetbackendspring.mail.MailService;
import org.example.foodbudgetbackendspring.user.dto.PasswordResetRequest;
import org.example.foodbudgetbackendspring.user.dto.RegisterRequest;
import org.example.foodbudgetbackendspring.user.model.User;
import org.example.foodbudgetbackendspring.user.model.VerificationCode;
import org.example.foodbudgetbackendspring.user.repository.UserRepository;
import org.example.foodbudgetbackendspring.user.repository.VerificationCodeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    private String generateVerificationCodeString(){
        return String.valueOf(
                new SecureRandom().nextInt(900_000) + 100_000
        );
    }

    private VerificationCode generateVerificationCodeObject(User user){
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setCode(generateVerificationCodeString());
        verificationCode.setUser(user);
        verificationCode.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        return verificationCode;
    }

    @Transactional
    public void register(RegisterRequest request){
        User user = userRepository.findByEmail(request.email()).orElse(null);

        if (user != null) {
            if (user.isEnabled()){
                throw new RuntimeException("User is registered and enabled");
            }
            else {
                throw new RuntimeException("User registered, waiting for verification code");
            }
        }

        user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);

        VerificationCode verificationCode = generateVerificationCodeObject(user);
        verificationCodeRepository.save(verificationCode);

        mailService.sendRegistrationEmail(
                user.getEmail(),
                verificationCode.getCode()
        );
    }

    @Transactional
    public void verifyRegistration(String code){
        VerificationCode verificationCode = verificationCodeRepository
                .findByCode(code)
                .orElseThrow(() -> new RuntimeException("Invalid verification code"));

        if (verificationCode.isExpired()){
            throw new RuntimeException("Expired verification code");
        }

        User user = verificationCode.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        verificationCodeRepository.delete(verificationCode);
    }

    @Transactional
    public void resendVerificationCode(String email){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isEnabled()) {
            throw new RuntimeException("User is already verified");
        }

        VerificationCode verificationCode = verificationCodeRepository
                .findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Invalid verification code"));

        verificationCode.setCode(generateVerificationCodeString());
        verificationCode.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        verificationCodeRepository.save(verificationCode);

        mailService.sendRegistrationEmail(
                user.getEmail(),
                verificationCode.getCode()
        );
    }


    public void sendPasswordResetEmail(String email){
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        VerificationCode code = generateVerificationCodeObject(user);
        verificationCodeRepository.save(code);

        mailService.sendPasswordResetEmail(
                email,
                code.getCode()
        );
    }

    @Transactional
    public void resetPassword(PasswordResetRequest request) {
        VerificationCode verificationCode = verificationCodeRepository
                .findByCode(request.code())
                .orElseThrow(() -> new RuntimeException("Invalid verification code"));

        if (verificationCode.isExpired()){
            throw new RuntimeException("Expired verification code");
        }

        if (!verificationCode.getCode().equals(request.code())){
            throw new RuntimeException("Invalid verification code");
        }

        Optional<User> userOptional = userRepository.findByEmail(request.email());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            user.setPassword(
                    passwordEncoder.encode(request.newPassword())
            );

            userRepository.save(user);
            verificationCodeRepository.delete(verificationCode);
        }
    }
}
