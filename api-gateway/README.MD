# API Gateway

# Table of Contents
1. [Features](#features)
1. [Instructions](#instructions)
    1. [Setup](#setup)
      1. [Environment variables](#environment-variables)
    1. [Build and Run](#build-and-run)
    1. [Docker](#docker)
1. [Actuator](#actuator)
1. [Service URLs](#service-urls)
1. [Additional Information](#additional-information)
   1. [Reference Documentation](#reference-documentation)
   1. [Guides](#guides)

## Features

- Spring Cloud Gateway Server
- [Spring Boot - Actuator](http://localhost:8765/actuator)
- [Zipkin client](https://docs.spring.io/spring-cloud-sleuth/docs/current-SNAPSHOT/reference/html/index.html)
- Eureka client

## Instructions
### Setup
#### Environment variables
Set the following environment variables
- **SERVICE_URL_DEFAULT_ZONE** - [default Eureka service URL](https://cloud.spring.io/spring-cloud-netflix/multi/multi__service_discovery_eureka_clients.html) for clients

  E.g. `SERVICE_URL_DEFAULT_ZONE=http://localhost:8761/eureka` will connect to a local Eureka server running on the default port.

  See [Eureka default zone setting is not picked up when using environment variables](https://github.com/spring-cloud/spring-cloud-netflix/issues/2541) for additional information.

- **ZIPKIN_SERVER_URL** - url of [Zipkin server](https://docs.spring.io/spring-cloud-sleuth/docs/current-SNAPSHOT/reference/html/index.html)

  E.g. `ZIPKIN_SERVER_URL=localhost:9411/` will connect to a local Spring Cloud Sleuth server running on the default port.

**Note:** [set-localdev-env.bat](set-localdev-env.bat) will set these variable for a localhost default environment.


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

### Docker
To build a docker image, open a terminal in the `api-gateway` folder and run
```
> dockerbuild.cmd
```
Or alternatively run the maven `spring-boot:build-image` goal in an IDE.

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
