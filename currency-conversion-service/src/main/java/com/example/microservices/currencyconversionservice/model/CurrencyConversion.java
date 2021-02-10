package com.example.microservices.currencyconversionservice.model;

import lombok.*;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class CurrencyConversion extends ExchangeRate {

    @Builder(builderMethodName = "conversionBuilder")
    public CurrencyConversion(Long id, String fromCurrency, String toCurrency, BigDecimal conversionMultiple, String environment, Long rateTimestamp, Long transactionTimestamp, BigDecimal quantity, BigDecimal totalCalculatedAmount) {
        super(id, fromCurrency, toCurrency, conversionMultiple, environment, rateTimestamp, transactionTimestamp);
        this.quantity = quantity;
        this.totalCalculatedAmount = totalCalculatedAmount;
    }

    private BigDecimal quantity;

    private BigDecimal totalCalculatedAmount;

    public static CurrencyConversion from(ExchangeRate exchangeRate, BigDecimal quantity) {
        return conversionBuilder()
                .fromCurrency(exchangeRate.getFromCurrency())
                .toCurrency(exchangeRate.getToCurrency())
                .conversionMultiple(exchangeRate.getConversionMultiple())
                .quantity(quantity)
                .totalCalculatedAmount(
                        quantity.multiply(exchangeRate.getConversionMultiple()))
                .rateTimestamp(exchangeRate.getRateTimestamp())
                .transactionTimestamp(nowTimestamp())
                .environment(exchangeRate.getEnvironment())
                .build();
    }
}
