package com.example.microservices.currencyexchangeservice;

import com.example.microservices.currencyexchangeservice.common.Profiles;
import com.example.microservices.currencyexchangeservice.service.ExchangeRateSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableScheduling
public class CurrencyExchangeServiceApplication {

	Logger logger = LoggerFactory.getLogger(CurrencyExchangeServiceApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(CurrencyExchangeServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner verifyProfiles(Environment environment) {
		return args -> {
			for (String profileStr : environment.getActiveProfiles()) {
				Profiles profile = Profiles.from(profileStr);
			}
		};
	}

	@Bean
	public List<Profiles> activeProfiles(Environment environment) {
		return Arrays.stream(environment.getActiveProfiles())
				.map(Profiles::from)
				.collect(Collectors.toList());
	}

	@Bean
	CommandLineRunner configLogger(@Value("${currency-exchange.rate-scale}") String rateScale,
											 @Value("${spring.datasource.platform}") String dbPlatform,
											 @Value("${spring.datasource.url}") String dbUrl) {
		return args -> {
			logger.info("rate-scale: {}", rateScale);
			logger.info("datasource.platform: {}", dbPlatform);
			logger.info("datasource.url: {}", dbUrl);
		};
	}

	@Bean
	CommandLineRunner initExchangeRateSource(ExchangeRateSourceService service) {
		return args -> {
			service.updateRates();
		};
	}
}
