package com.example.microservices.currencyconversionservice;

import com.example.microservices.currencyconversionservice.common.Profiles;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableFeignClients
public class CurrencyConversionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConversionServiceApplication.class, args);
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
}
