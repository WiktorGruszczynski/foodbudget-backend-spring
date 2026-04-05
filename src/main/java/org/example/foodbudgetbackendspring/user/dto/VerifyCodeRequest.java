package org.example.foodbudgetbackendspring.user.dto;

import org.example.foodbudgetbackendspring.user.model.VerificationType;

public record VerifyCodeRequest(
    String email,
    String code,
    VerificationType type
) {
}
