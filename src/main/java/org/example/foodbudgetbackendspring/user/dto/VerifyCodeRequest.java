package org.example.foodbudgetbackendspring.user.dto;

import jakarta.validation.constraints.NotNull;
import org.example.foodbudgetbackendspring.user.model.VerificationType;

public record VerifyCodeRequest(
        @NotNull String email,
        @NotNull String code,
        @NotNull VerificationType type
) {
}
