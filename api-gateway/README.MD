# API Gateway

# Table of Contents
1. [Features](#features)
1. [Instructions](#instructions)
    1. [Build and Run](#build-and-run)
1. [Actuator](#actuator)
1. [Service URLs](#service-urls)
1. [Additional Information](#additional-information)
   1. [Reference Documentation](#reference-documentation)
   1. [Guides](#guides)

## Features

- Spring Cloud Gateway Server
- [Spring Boot - Actuator](http://localhost:8765/actuator)
- Eureka client

## Instructions
### Build and Run
Open a terminal in the `api-gateway` folder and run
```
$ mvn clean package
$ java -jar target/api-gateway-0.0.1-SNAPSHOT.jar
```
or on Windows run
```
> build.cmd
> run.cmd
```
Alternatively, build and run via an IDE.

## Actuator
http://localhost:8765/actuator

## Service URLs
|Service|API Gateway|
|-------|--------------------|
|currency-exchange|http://localhost:8765/currency-exchange/from/usd/to/eur|
|currency-conversion|http://localhost:8765/currency-conversion/from/usd/to/eur/quantity/10.3|

## Additional Information

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.2/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.2/maven-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.4.2/reference/htmlsingle/#using-boot-devtools)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.4.2/reference/htmlsingle/#production-ready)
* [Eureka Discovery Client](https://docs.spring.io/spring-cloud-netflix/docs/current/reference/html/#service-discovery-eureka-clients)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
* [Service Registration and Discovery with Eureka and Spring Cloud](https://spring.io/guides/gs/service-registration-and-discovery/)
* [Using Spring Cloud Gateway](https://github.com/spring-cloud-samples/spring-cloud-gateway-sample)
