package org.example.foodbudgetbackendspring.user.dto;

import jakarta.annotation.Nonnull;

public record LoginRequest(@Nonnull String email, @Nonnull String password) {
}
