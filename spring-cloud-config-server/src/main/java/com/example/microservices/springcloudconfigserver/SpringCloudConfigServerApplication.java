package com.example.microservices.springcloudconfigserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

import java.util.Arrays;

@EnableConfigServer
@SpringBootApplication
public class SpringCloudConfigServerApplication {

	private static final Logger log = LoggerFactory.getLogger(SpringCloudConfigServerApplication.class);

	public static void main(String[] args) {
		log.info(
				String.format("Application starting with command-line arguments: %s.%n" +
						"To kill this application, press Ctrl + C.", Arrays.toString(args))
		);
		SpringApplication.run(SpringCloudConfigServerApplication.class, args);
	}

}
