package com.example.microservices.currencyconversionservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.example.microservices.currencyconversionservice.common.UrlConfig.*;

@RestController
public class ActuatorController {

    /**
     * Get the actuator info. The request is redirected to the actual actuator url.
     */
    @Operation(summary = "Get the actuator info")
    @GetMapping(CURRENCY_EXCHANGE_ACTUATOR_INFO_URL)
    public void retrieveExchangeRate(HttpServletResponse response) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();

            response.setHeader(HttpHeaders.LOCATION,
                    request.getRequestURL().toString()
                            .replace(CONVERSION_BASE_URL, ""));
            response.setStatus(HttpStatus.PERMANENT_REDIRECT.value());
        }
    }
}
