package com.example.microservices.currencyconversionservice.controller;

import com.example.microservices.currencyconversionservice.model.CurrencyConversion;
import com.example.microservices.currencyconversionservice.service.CurrencyConversionService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static com.example.microservices.currencyconversionservice.common.UrlConfig.*;
import static com.example.microservices.currencyconversionservice.model.ExchangeRate.nowTimestamp;


@RestController
@RequestMapping(CONVERSION_BASE_URL)
public class CurrencyConversionController {

    @Autowired
    private CurrencyConversionService currencyConversionService;

    /**
     * Get a currency conversion
     * @param fromCurrency - conversion from currency
     * @param toCurrency - conversion to currency
     * @param quantity - quantity of from currency
     * @return
     */
    @Operation(summary = "Get a currency conversion")
    @GetMapping(CONVERSION_PART_URL)
    @Retry(name = "retrieveCurrencyConversion", fallbackMethod = "retrieveCurrencyConversionFallback")
    @CircuitBreaker(name = "retrieveCurrencyConversion", fallbackMethod = "retrieveCurrencyConversionFallback")
    @RateLimiter(name = "retrieveCurrencyConversion", fallbackMethod = "retrieveCurrencyConversionFallback")
    @Bulkhead(name = "retrieveCurrencyConversion", fallbackMethod = "retrieveCurrencyConversionFallback")
    public CurrencyConversion retrieveCurrencyConversion(
            @Parameter(description = "ISO 4217 currency code of currency to convert from, e.g. USD")
            @PathVariable(name = FROM_PV) String fromCurrency,
            @Parameter(description = "ISO 4217 currency code of currency to convert to, e.g. EUR")
            @PathVariable(name = TO_PV) String toCurrency,
            @Parameter(description = "quantity of from currency to convert, e.g. 10.3")
            @PathVariable(name = QUANTITY_PV) BigDecimal quantity) {

        return currencyConversionService.getCurrencyConversion(fromCurrency, toCurrency, quantity);
    }

    /**
     * Fallback conversion when rate not available
     * @param fromCurrency - conversion from currency
     * @param toCurrency - conversion to currency
     * @param quantity - quantity of from currency
     * @param exception
     * @return
     */
    public CurrencyConversion retrieveCurrencyConversionFallback(String fromCurrency, String toCurrency, BigDecimal quantity,
                                                                 Exception exception) {
        return CurrencyConversion.conversionBuilder()
                .fromCurrency(fromCurrency)
                .toCurrency(fromCurrency)
                .conversionMultiple(BigDecimal.ONE)
                .quantity(quantity)
                .totalCalculatedAmount(quantity)
                .rateTimestamp(LocalDateTime.MIN.toEpochSecond(ZoneOffset.UTC))
                .transactionTimestamp(nowTimestamp())
                .environment("fallback")
                .build();
    }
}
