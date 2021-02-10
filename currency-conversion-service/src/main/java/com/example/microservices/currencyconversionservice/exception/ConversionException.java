package com.example.microservices.currencyconversionservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
public class ConversionException extends RuntimeException {

    public ConversionException(String message) {
        super(message);
    }
}
