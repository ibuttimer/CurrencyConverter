package com.example.microservices.currencyconversionservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeRateSource {

    private Long id;

    private String disclaimer;

    private String license;

    private Long timestamp;

    private String base;

    private Map<String, BigDecimal> rates;
}
