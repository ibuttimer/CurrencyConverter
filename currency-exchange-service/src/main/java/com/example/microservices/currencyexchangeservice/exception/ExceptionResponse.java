package com.example.microservices.currencyexchangeservice.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ExceptionResponse {

    @Builder.Default private LocalDateTime timestamp = LocalDateTime.now();
    private String message;
    private String details;

    public static ExceptionResponse of(String message, String details) {
        return new ExceptionResponse(LocalDateTime.now(), message, details);
    }
}
