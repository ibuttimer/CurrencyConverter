package com.example.microservices.currencyconversionservice.model;

import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CurrencyConversion that = (CurrencyConversion) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

}
