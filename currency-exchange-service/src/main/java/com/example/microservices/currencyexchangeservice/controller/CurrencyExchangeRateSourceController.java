package com.example.microservices.currencyexchangeservice.controller;

import com.example.microservices.currencyexchangeservice.common.Profiles;
import com.example.microservices.currencyexchangeservice.model.ExchangeRateSource;
import com.example.microservices.currencyexchangeservice.service.ExchangeRateSourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.microservices.currencyexchangeservice.common.UrlConfig.*;

@RestController
@Profile({"dev", "unit_test", "qa", "integration"})
@RequestMapping(EXCHANGE_BASE_URL)
public class CurrencyExchangeRateSourceController {

    @Autowired
    private ExchangeRateSourceService exchangeRateSourceService;

    /**
     * Update exchange rates
     * @param rateSource - exchange rates to save
     * @return
     */
    @Operation(summary = "Update the currency exchange rates")
    @PostMapping(RATES_UPDATE_PART_URL)
    public ResponseEntity<ExchangeRateSource> retrieveExchangeRate(
            @Parameter(description = "Rates to save")
            @RequestBody ExchangeRateSource rateSource) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                    exchangeRateSourceService.save(rateSource)
                );
    }
}
