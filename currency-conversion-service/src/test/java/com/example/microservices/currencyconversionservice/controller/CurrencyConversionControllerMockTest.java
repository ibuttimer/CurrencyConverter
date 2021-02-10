package com.example.microservices.currencyconversionservice.controller;

import com.example.microservices.currencyconversionservice.model.CurrencyConversion;
import com.example.microservices.currencyconversionservice.service.CurrencyConversionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Map;

import static com.example.microservices.currencyconversionservice.common.Currency.CURRENCIES;
import static com.example.microservices.currencyconversionservice.common.Currency.USD;
import static com.example.microservices.currencyconversionservice.common.UrlConfig.*;
import static com.example.microservices.currencyconversionservice.model.ExchangeRate.nowTimestamp;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CurrencyConversionController.class)
class CurrencyConversionControllerMockTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CurrencyConversionService currencyConversionService;

    @DisplayName("Convert currency")
    @Test
    public void convertCurrency() throws Exception {
        final String environment = "test";
        final String fromCurrency = USD;
        final Long transactionTimestamp = nowTimestamp();
        final Long rateTimestamp = transactionTimestamp - 60;

        long id = 0;
        for (Map.Entry<String, String> entry : CURRENCIES.entrySet()) {

            if (!entry.getKey().equals(fromCurrency)) {
                // use hashcode of currency description as rate
                BigDecimal multiple = BigDecimal.valueOf(entry.getValue().hashCode());
                BigDecimal quantity = BigDecimal.TEN;
                BigDecimal amount = multiple.multiply(quantity);
                CurrencyConversion conversion = CurrencyConversion.conversionBuilder()
                        .id(++id)
                        .fromCurrency(fromCurrency)
                        .toCurrency(entry.getKey())
                        .conversionMultiple(multiple)
                        .quantity(quantity)
                        .totalCalculatedAmount(amount)
                        .rateTimestamp(rateTimestamp)
                        .transactionTimestamp(transactionTimestamp)
                        .environment(environment)
                        .build();

                Mockito.when(currencyConversionService
                        .getCurrencyConversion(fromCurrency, entry.getKey(), quantity))
                            .thenReturn(conversion);

                mvc.perform(
                        get(CONVERSION_URL
                                .replace(FROM_PATTERN, fromCurrency)
                                .replace(TO_PATTERN, entry.getKey())
                                .replace(QUANTITY_PATTERN, quantity.toString())
                        ))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(id))
                        .andExpect(jsonPath("$.fromCurrency").value(fromCurrency))
                        .andExpect(jsonPath("$.toCurrency").value(entry.getKey()))
                        .andExpect(jsonPath("$.conversionMultiple").value(multiple))
                        .andExpect(jsonPath("$.quantity").value(quantity))
                        .andExpect(jsonPath("$.totalCalculatedAmount").value(amount))
                        .andExpect(jsonPath("$.environment").value(environment))
                        .andExpect(jsonPath("$.rateTimestamp").value(rateTimestamp))
                        .andExpect(jsonPath("$.transactionTimestamp").value(transactionTimestamp));

                verify(currencyConversionService, times(1))
                        .getCurrencyConversion(fromCurrency, entry.getKey(), quantity);
                verifyNoMoreInteractions(currencyConversionService);
            }
        }

    }

}