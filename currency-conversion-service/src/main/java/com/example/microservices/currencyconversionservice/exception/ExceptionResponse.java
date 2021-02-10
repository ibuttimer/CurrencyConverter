package com.example.microservices.currencyconversionservice.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionResponse {

    @Builder.Default private LocalDateTime timestamp = LocalDateTime.now();
    private String message;
    private String details;

    public static ExceptionResponse of(String message, String details) {
        return new ExceptionResponse(LocalDateTime.now(), message, details);
    }

    public static ExceptionResponse from(String json) {
        ExceptionResponse exceptionResponse = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            exceptionResponse = objectMapper.readValue(json, ExceptionResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return exceptionResponse;
    }
}
