package com.example.microservices.currencyexchangeservice.controller;

import com.example.microservices.currencyexchangeservice.model.ExchangeRate;
import com.example.microservices.currencyexchangeservice.service.ExchangeRateService;
import com.example.microservices.currencyexchangeservice.service.ExchangeRateSourceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import static com.example.microservices.currencyexchangeservice.common.Currency.CURRENCIES;
import static com.example.microservices.currencyexchangeservice.common.Currency.EUR;
import static com.example.microservices.currencyexchangeservice.common.UrlConfig.*;
import static com.example.microservices.currencyexchangeservice.model.ExchangeRate.nowTimestamp;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CurrencyExchangeController.class)
class CurrencyExchangeControllerMockTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ExchangeRateService exchangeRateService;

    @MockBean
    ExchangeRateSourceService exchangeRateSourceService;

    static final String BASE_CURRENCY = EUR;

    @DisplayName("Get exchange rate")
    @Test
    public void getExchangeRate() throws Exception {
        String host;
        try {
            host = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            host = "unknown";
        }
        final String hostEnvironment = host + ":null:@project.version@";
        final String environment = "test";
        final Long transactionTimestamp = nowTimestamp();
        final Long rateTimestamp = transactionTimestamp - 60;

        for (Map.Entry<String, String> entry : CURRENCIES.entrySet()) {

            if (!entry.getKey().equals(BASE_CURRENCY)) {
                // use hashcode of currency description as rate
                BigDecimal multiple = BigDecimal.valueOf(entry.getValue().hashCode());
                ExchangeRate rate = ExchangeRate.builder()
                        .fromCurrency(BASE_CURRENCY)
                        .toCurrency(entry.getKey())
                        .conversionMultiple(multiple)
                        .rateTimestamp(rateTimestamp)
                        .transactionTimestamp(transactionTimestamp)
                        .environment(environment)
                        .build();

                Mockito.when(exchangeRateService
                        .getExchangeRate(BASE_CURRENCY, entry.getKey(), hostEnvironment))
                            .thenReturn(rate);

                mvc.perform(
                        get(EXCHANGE_URL
                                .replace(FROM_PATTERN, BASE_CURRENCY)
                                .replace(TO_PATTERN, entry.getKey())
                        ))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.fromCurrency").value(BASE_CURRENCY))
                        .andExpect(jsonPath("$.toCurrency").value(entry.getKey()))
                        .andExpect(jsonPath("$.conversionMultiple").value(multiple))
                        .andExpect(jsonPath("$.rateTimestamp").value(rateTimestamp))
                        .andExpect(jsonPath("$.transactionTimestamp").value(transactionTimestamp))
                        .andExpect(jsonPath("$.environment").value(environment));

                verify(exchangeRateService, times(1))
                        .getExchangeRate(BASE_CURRENCY, entry.getKey(), hostEnvironment);
                verifyNoMoreInteractions(exchangeRateService);
            }
        }
    }
}