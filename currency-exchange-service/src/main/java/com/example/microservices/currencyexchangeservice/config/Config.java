package com.example.microservices.currencyexchangeservice.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.example.microservices.currencyexchangeservice.common.UrlConfig.CURRENCY_EXCHANGE_APP_NAME;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(CURRENCY_EXCHANGE_APP_NAME)
public class Config {
    private int rateScale;
    private String apiGateway;
}
