package com.example.microservices.apigateway;

import com.example.microservices.apigateway.config.LoggingListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ApiGatewayApplication.class);
		app.addListeners(new LoggingListener());
		app.run(args);
	}

}
