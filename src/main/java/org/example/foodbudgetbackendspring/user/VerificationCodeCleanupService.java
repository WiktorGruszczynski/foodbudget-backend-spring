package org.example.foodbudgetbackendspring.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class VerificationCodeCleanupService {
    private final VerificationCodeRepository verificationCodeRepository;

    @Scheduled(cron = "0 0 1 * * *")
    @Transactional
    public void deleteExpiredVerificationCodes() {
        log.info("Deleting expired verification codes");

        verificationCodeRepository.deleteByExpiryDateBefore(LocalDateTime.now());

        log.info("Deleted expired verification codes");
    }
}
