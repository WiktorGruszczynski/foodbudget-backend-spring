package org.example.foodbudgetbackendspring.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.foodbudgetbackendspring.mail.MailService;
import org.example.foodbudgetbackendspring.user.dto.PasswordResetRequest;
import org.example.foodbudgetbackendspring.user.dto.RegisterRequest;
import org.example.foodbudgetbackendspring.user.dto.VerifyCodeRequest;
import org.example.foodbudgetbackendspring.user.model.User;
import org.example.foodbudgetbackendspring.user.model.VerificationCode;
import org.example.foodbudgetbackendspring.user.model.VerificationType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final static String VERIFICATION_ERROR_MESSAGE = "Invalid code or email";

    private String generateVerificationCodeString(){
        return String.valueOf(
                new SecureRandom().nextInt(900_000) + 100_000
        );
    }

    private VerificationCode generateVerificationCodeObject(User user, VerificationType type) {
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setCode(generateVerificationCodeString());
        verificationCode.setType(type);
        verificationCode.setUser(user);
        verificationCode.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        return verificationCode;
    }

    private VerificationCode validateVerificationCode(String code, User user, VerificationType type){
        VerificationCode verificationCode = verificationCodeRepository
                .findByCodeAndUserId(
                        code,
                        user.getId()
                )
                .orElseThrow(
                        () -> new RuntimeException(VERIFICATION_ERROR_MESSAGE)
                );

        if (!verificationCode.getType().equals(type)){
            throw new RuntimeException(VERIFICATION_ERROR_MESSAGE);
        }

        if (verificationCode.isExpired()){
            throw new RuntimeException("Code is expired");
        }

        return verificationCode;
    }

    @Transactional
    public void register(RegisterRequest request){
        User user = userRepository.findByEmail(request.email()).orElse(null);

        if (user != null) {
            if (user.isEnabled()){
                throw new RuntimeException("User with this email already exists");
            }
            resendVerificationCode(user.getEmail());
        }
        else {
            user = new User();
            user.setEmail(request.email());
            user.setPassword(passwordEncoder.encode(request.password()));

            VerificationCode verificationCode = generateVerificationCodeObject(user, VerificationType.EMAIL_VERIFICATION);
            userRepository.save(user);
            verificationCodeRepository.save(verificationCode);

            mailService.sendRegistrationEmail(
                    user.getEmail(),
                    verificationCode.getCode()
            );
        }
    }

    @Transactional
    public void resendVerificationCode(String email){
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException(VERIFICATION_ERROR_MESSAGE));

        if (user.isEnabled()) {
            throw new RuntimeException("User is already verified");
        }

        VerificationCode verificationCode = generateVerificationCodeObject(user, VerificationType.EMAIL_VERIFICATION);
        verificationCodeRepository.save(verificationCode);

        mailService.sendRegistrationEmail(
                user.getEmail(),
                verificationCode.getCode()
        );
    }

    @Transactional
    public void verifyRegistration(String code, String email){
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException(VERIFICATION_ERROR_MESSAGE));

        VerificationCode verificationCode = validateVerificationCode(
                code,
                user,
                VerificationType.EMAIL_VERIFICATION
        );

        user.setEnabled(true);
        userRepository.save(user);
        verificationCodeRepository.delete(verificationCode);
    }

    @Transactional
    public void sendPasswordResetEmail(String email){
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException(VERIFICATION_ERROR_MESSAGE));

        VerificationCode code = generateVerificationCodeObject(user, VerificationType.PASSWORD_RESET);
        verificationCodeRepository.save(code);

        mailService.sendPasswordResetEmail(
                email,
                code.getCode()
        );
    }

    @Transactional
    public void resetPassword(PasswordResetRequest request) {
        User user = userRepository
                .findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException(VERIFICATION_ERROR_MESSAGE));

        VerificationCode verificationCode = validateVerificationCode(
                request.code(),
                user,
                VerificationType.PASSWORD_RESET
        );

        user.setPassword(
                passwordEncoder.encode(request.password())
        );

        userRepository.save(user);
        verificationCodeRepository.delete(verificationCode);
    }

    public void verifyCode(VerifyCodeRequest request){
        User user = userRepository
                .findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException(VERIFICATION_ERROR_MESSAGE));

        validateVerificationCode(
                request.code(),
                user,
                request.type()
        );
    }
}
