package org.example.foodbudgetbackendspring.user.dto;

import jakarta.validation.constraints.NotNull;

public record RegisterRequest(@NotNull String email, @NotNull  String password) {
}
