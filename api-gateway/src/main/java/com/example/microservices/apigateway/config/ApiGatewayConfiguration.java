package com.example.microservices.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.microservices.apigateway.common.UrlConfig.*;


@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path(CONVERSION_BASE_URL + "/**")
                        .uri("lb://" + CURRENCY_CONVERSION_APP_NAME)
                )
                .route(p -> p.path(EXCHANGE_BASE_URL + "/**")
                        .uri("lb://" + CURRENCY_EXCHANGE_APP_NAME)
                )
                .build();
    }
}
