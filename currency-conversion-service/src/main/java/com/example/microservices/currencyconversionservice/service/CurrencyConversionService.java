package com.example.microservices.currencyconversionservice.service;

import com.example.microservices.currencyconversionservice.config.Config;
import com.example.microservices.currencyconversionservice.exception.ConversionException;
import com.example.microservices.currencyconversionservice.exception.CurrencyNotFoundException;
import com.example.microservices.currencyconversionservice.exception.ExceptionResponse;
import com.example.microservices.currencyconversionservice.model.CurrencyConversion;
import com.example.microservices.currencyconversionservice.model.ExchangeRate;
import com.example.microservices.currencyconversionservice.proxy.CurrencyConversionProxy;
import com.example.microservices.currencyconversionservice.repository.CurrencyConversionRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.microservices.currencyconversionservice.common.UrlConfig.*;
import static com.example.microservices.currencyconversionservice.model.ExchangeRate.nowTimestamp;

@Service
public class CurrencyConversionService {

    @Autowired
    private Config config;

    @Autowired
    private CurrencyConversionRepository currencyConversionRepository;

    @Autowired
    private CurrencyConversionProxy conversionProxy;

    private final String url;

    private final RestTemplate restTemplate;

    public CurrencyConversionService(RestTemplateBuilder restTemplateBuilder,
                                     @Value("${currency.exchange.host}:") String exchangeHost
    ) {
        this.restTemplate = restTemplateBuilder.build();
        this.url = UriComponentsBuilder
                .fromUriString(exchangeHost)
                .path(EXCHANGE_URL)
                .build()
                .toUriString();
    }

    public CurrencyConversion getCurrencyConversion(String fromCurrency, String toCurrency, BigDecimal quantity) {

        AtomicReference<CurrencyConversion> result = new AtomicReference<>();
        getRateFeign(fromCurrency, toCurrency)
                .ifPresent(rate -> {
                    CurrencyConversion conversion = CurrencyConversion.conversionBuilder()
                            .fromCurrency(fromCurrency)
                            .toCurrency(toCurrency)
                            .conversionMultiple(rate.getConversionMultiple())
                            .quantity(quantity)
                            .totalCalculatedAmount(
                                    quantity.multiply(rate.getConversionMultiple()))
                            .rateTimestamp(rate.getRateTimestamp())
                            .transactionTimestamp(nowTimestamp())
                            .environment(rate.getEnvironment())
                            .build();
                    result.set(
                            currencyConversionRepository.save(conversion));

                });

        return result.get();
    }

    /**
     * Get exchange rate using Feign client
     * @param fromCurrency - conversion from currency
     * @param toCurrency - conversion to currency
     * @return
     */
    private Optional<ExchangeRate> getRateFeign(String fromCurrency, String toCurrency) {
        Optional<ExchangeRate> result;

        try {
            result = Optional.of(
                    conversionProxy.getCurrencyExchange(fromCurrency, toCurrency)
            );
        } catch (FeignException fe) {
            if (fe.status() == HttpStatus.NOT_FOUND.value()) {
                throw new CurrencyNotFoundException(
                        fe.getMessage());
            } else {
                throw new ConversionException(
                        fe.getMessage());
            }
        }
        return result;
    }

    /**
     * Get exchange rate using direct request
     * @param fromCurrency - conversion from currency
     * @param toCurrency - conversion to currency
     * @return
     */
    private Optional<ExchangeRate> getRate(String fromCurrency, String toCurrency) {
        Optional<ExchangeRate> result = Optional.empty();

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put(FROM_PV, fromCurrency);
        uriVariables.put(TO_PV, toCurrency);

        try {
            ResponseEntity<ExchangeRate> response = this.restTemplate.getForEntity(url, ExchangeRate.class, uriVariables);
            if (response.getStatusCode() == HttpStatus.OK) {
                result = Optional.ofNullable(response.getBody());
            }
        } catch (HttpStatusCodeException hsce) {
            ExceptionResponse exceptionResponse = ExceptionResponse.from(
                    hsce.getResponseBodyAsString());
            if (hsce.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CurrencyNotFoundException(
                        exceptionResponse.getMessage());
            } else {
                throw new ConversionException(
                        exceptionResponse.getMessage());
            }
        }
        return result;
    }

}
