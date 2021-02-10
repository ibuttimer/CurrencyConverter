package com.example.microservices.currencyexchangeservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.microservices.currencyexchangeservice.common.UrlConfig.*;

@RestController
public class ActuatorController {

    /**
     * Get the actuator info. The request is redirected to the actual actuator url.
     * @return
     */
    @Operation(summary = "Get the actuator info")
    @GetMapping(CURRENCY_EXCHANGE_ACTUATOR_INFO_URL)
    public void retrieveExchangeRate(HttpServletResponse response) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();

            response.setHeader(HttpHeaders.LOCATION,
                    request.getRequestURL().toString()
                            .replace(EXCHANGE_BASE_URL, ""));
            response.setStatus(HttpStatus.PERMANENT_REDIRECT.value());
        }
    }
}
