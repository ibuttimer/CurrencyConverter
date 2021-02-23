package com.example.microservices.currencyexchangeservice.controller;

import com.example.microservices.currencyexchangeservice.model.ExchangeRate;
import com.example.microservices.currencyexchangeservice.service.ExchangeRateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.example.microservices.currencyexchangeservice.common.UrlConfig.*;

@Slf4j
@RestController
@RequestMapping(EXCHANGE_BASE_URL)
public class CurrencyExchangeController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private Environment environment;

    @Value("${info.app.version}")
    private String appVersion;

    /**
     * Get an exchange rate
     * @param fromCurrency - conversion from currency
     * @param toCurrency - conversion to currency
     * @return
     */
    @Operation(summary = "Get a currency exchange rate")
    @GetMapping(EXCHANGE_PART_URL)
    public ExchangeRate retrieveExchangeRate(
            @Parameter(description = "ISO 4217 currency code of currency to convert from, e.g. USD")
            @PathVariable(name = FROM_PV) String fromCurrency,
            @Parameter(description = "ISO 4217 currency code of currency to convert to, e.g. EUR")
            @PathVariable(name = TO_PV) String toCurrency) {

        log.info(getEnvironment());

        return exchangeRateService.getExchangeRate(fromCurrency, toCurrency, getEnvironment());
    }

    private String getEnvironment() {
        String host;
        try {
            host = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            host = "unknown";
        }
        return host + ":" +
                environment.getProperty("local.server.port") + ":" +
                appVersion;
    }
}
