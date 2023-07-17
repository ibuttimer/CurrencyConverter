package com.example.microservices.apigateway.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        value = {"spring.cloud.service-registry.auto-registration.enabled",
                "spring.cloud.discovery.enabled",
                "eureka.client.register-with-eureka",
                "eureka.client.fetch-registry",
                "eureka.client.enabled"},
        matchIfMissing = true
)
@EnableDiscoveryClient
public class EurekaConfig {
}
