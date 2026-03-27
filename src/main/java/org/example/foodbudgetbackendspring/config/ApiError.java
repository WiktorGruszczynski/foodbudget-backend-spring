package org.example.foodbudgetbackendspring.config;

import java.time.LocalDateTime;

public record ApiError(
        LocalDateTime timestamp,
        String message
) {}