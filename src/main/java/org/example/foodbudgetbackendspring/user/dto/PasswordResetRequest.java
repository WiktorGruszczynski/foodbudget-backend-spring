package org.example.foodbudgetbackendspring.user.dto;

public record PasswordResetRequest (
        String email,
        String password,
        String code
){ }
