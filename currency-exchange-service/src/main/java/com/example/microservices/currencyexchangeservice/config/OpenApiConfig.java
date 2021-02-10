package com.example.microservices.currencyexchangeservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    public static final String OK = "200";   // HttpStatus.OK
    public static final String CREATED = "201"; // HttpStatus.CREATED
    public static final String BAD_REQUEST = "400"; // HttpStatus.BAD_REQUEST
    public static final String NOT_FOUND = "404";   // HttpStatus.NOT_FOUND
    public static final String INTERNAL_SERVER_ERROR = "500";   // HttpStatus.INTERNAL_SERVER_ERROR


    @Bean
    public OpenAPI customOpenAPI(@Value("${info.app.name}") String appName,
                                 @Value("${info.app.description}") String appDescription,
                                 @Value("${info.app.version}") String appVersion) {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title(appName)
                        .description(appDescription)
                        .version(appVersion)
                        .contact(new Contact().name("Ian Buttimer"))
                        .license(new License().name("MIT License").url("https://opensource.org/licenses/MIT")));
    }
}
