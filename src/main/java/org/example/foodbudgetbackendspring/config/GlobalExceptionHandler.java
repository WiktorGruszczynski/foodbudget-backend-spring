package org.example.foodbudgetbackendspring.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntime(
            RuntimeException ex
    ){
        System.err.println("Business error: " + ex.getMessage());
        ApiError error = new ApiError(
                LocalDateTime.now(),
                ex.getMessage()
        );

        // return 400 bad request
        return ResponseEntity.badRequest().body(error);
    }
}
