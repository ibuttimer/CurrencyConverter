package com.example.microservices.currencyexchangeservice.controller;

import com.example.microservices.currencyexchangeservice.CurrencyExchangeServiceApplication;
import com.example.microservices.currencyexchangeservice.model.ExchangeRateSource;
import com.example.microservices.currencyexchangeservice.repository.ExchangeRateRepository;
import com.example.microservices.currencyexchangeservice.repository.ExchangeRateSourceRepository;
import com.example.microservices.currencyexchangeservice.service.ExchangeRateService;
import com.example.microservices.currencyexchangeservice.service.ExchangeRateSourceService;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.number.BigDecimalCloseTo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.microservices.currencyexchangeservice.common.Currency.*;
import static com.example.microservices.currencyexchangeservice.common.UrlConfig.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = CurrencyExchangeServiceApplication.class
)
@AutoConfigureMockMvc
class CurrencyExchangeControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ExchangeRateRepository exchangeRateRepository;

    @Autowired
    ExchangeRateSourceRepository exchangeRateSourceRepository;

    @Autowired
    ExchangeRateService exchangeRateService;

    @Autowired
    ExchangeRateSourceService exchangeRateSourceService;

    static Map<String, BigDecimal> rates;
    static long timestamp = 123456789L;
    static final String BASE_CURRENCY = EUR;

    @BeforeAll
    static void beforeAll() {
        // use hashcode of currency description as rate
        rates = CURRENCIES.entrySet().stream()
                .filter(e -> !e.getKey().equals(BASE_CURRENCY))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> BigDecimal.valueOf(
                                            e.getValue().hashCode())));
    }

    ExchangeRateSource exchangeRateSource;

    @BeforeEach
    void beforeEach() {
        exchangeRateRepository.deleteAll();
        exchangeRateSourceRepository.deleteAll();

        ExchangeRateSource rateSource = ExchangeRateSource.builder()
                .base(BASE_CURRENCY)
                .rates(rates)
                .timestamp(timestamp)
                .build();

        exchangeRateSource = exchangeRateSourceRepository.save(rateSource);

        assertEquals(1, exchangeRateSourceRepository.count());
        assertTrue(exchangeRateSource.getRates().size() > 0);
        for (String currency : exchangeRateSource.getRates().keySet()) {
            assertEquals(rates.get(currency), exchangeRateSource.getRates().get(currency));
        }
    }

    @DisplayName("Get exchange rate")
    @Test
    public void getExchangeRate() throws Exception {
        final String fromCurrency = exchangeRateSource.getBase();

        for (Map.Entry<String, String> entry : CURRENCIES.entrySet()) {

            if (!entry.getKey().equals(fromCurrency)) {
                // hashcode of currency description is rate
                BigDecimal multiple = BigDecimal.valueOf(entry.getValue().hashCode());

                mvc.perform(
                        get(EXCHANGE_URL
                                .replace(FROM_PATTERN, fromCurrency)
                                .replace(TO_PATTERN, entry.getKey())
                        ))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").exists())
                        .andExpect(jsonPath("$.fromCurrency").value(fromCurrency))
                        .andExpect(jsonPath("$.toCurrency").value(entry.getKey()))
                        .andExpect(jsonPath("$.conversionMultiple", new BigDecimalCloseTo(multiple, BigDecimal.ZERO), BigDecimal.class))
                        .andExpect(jsonPath("$.rateTimestamp").value(timestamp))
                        .andExpect(jsonPath("$.transactionTimestamp").exists());
            }
        }
    }

}