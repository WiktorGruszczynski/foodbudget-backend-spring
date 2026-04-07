package org.example.foodbudgetbackendspring.user.repository;

import jakarta.transaction.Transactional;
import org.example.foodbudgetbackendspring.user.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, UUID> {
    Optional<VerificationCode> findByCodeAndUserId(String code, UUID userId);

    @Modifying
    @Transactional
    void deleteByExpiryDateBefore(LocalDateTime date);
}
