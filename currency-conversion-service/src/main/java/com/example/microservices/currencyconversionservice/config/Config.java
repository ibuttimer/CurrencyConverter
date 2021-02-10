package com.example.microservices.currencyconversionservice.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.example.microservices.currencyconversionservice.common.UrlConfig.CURRENCY_CONVERSION_APP_NAME;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(CURRENCY_CONVERSION_APP_NAME)
public class Config {
    private String apiGateway;
}
