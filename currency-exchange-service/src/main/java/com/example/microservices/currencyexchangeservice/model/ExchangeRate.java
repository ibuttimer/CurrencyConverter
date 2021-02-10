package com.example.microservices.currencyexchangeservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromCurrency;

    private String toCurrency;

    private BigDecimal conversionMultiple;

    private String environment;

    private Long rateTimestamp;

    private Long transactionTimestamp;

    public static long nowTimestamp() {
        return LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
    }
}
