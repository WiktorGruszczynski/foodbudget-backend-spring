package org.example.foodbudgetbackendspring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntime(
            RuntimeException ex
    ){
        log.error("Business error", ex);
        ApiError error = new ApiError(
                LocalDateTime.now(),
                ex.getMessage()
        );

        // return 400 bad request
        return ResponseEntity.badRequest().body(error);
    }
}
