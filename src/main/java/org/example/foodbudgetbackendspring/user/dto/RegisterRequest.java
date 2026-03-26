package org.example.foodbudgetbackendspring.user.dto;

import jakarta.annotation.Nonnull;

public record RegisterRequest(@Nonnull String email, @Nonnull String password) {
}
