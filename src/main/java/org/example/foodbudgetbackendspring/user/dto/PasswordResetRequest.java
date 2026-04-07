package org.example.foodbudgetbackendspring.user.dto;

import jakarta.validation.constraints.NotNull;

public record PasswordResetRequest (
        @NotNull String email,
        @NotNull String password,
        @NotNull String code
){ }
