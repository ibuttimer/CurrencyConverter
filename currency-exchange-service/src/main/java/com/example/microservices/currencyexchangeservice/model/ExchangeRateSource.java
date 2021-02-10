package com.example.microservices.currencyexchangeservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ExchangeRateSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    @JsonIgnore
    private String disclaimer;

    @Transient
    @JsonIgnore
    private String license;

    private Long timestamp;

    private String base;

    @ElementCollection
    @MapKeyColumn(name="currency")
    @Column(name="rate")
    @CollectionTable(name="currency_rates", joinColumns=@JoinColumn(name="exchange_rate_source_id"))
    private Map<String, BigDecimal> rates;
}
