# Currency Conversion Service (Kubernetes)

# Table of Contents
1. [Features](#features)
1. [Instructions](#instructions)
   1. [Setup](#setup)
       1. [Environment variables](#environment-variables)
   1. [Build, Test and Run](#build-test-and-run)
        1. [Integration test prerequisites](#integration-test-prerequisites)
        1. [Build](#build)
        1. [Run](#run)
   1. [Docker](#docker)
1. [Actuator](#actuator)
1. [OpenAPI Swagger UI](#openapi-swagger-ui)
    1. [Currency conversion](#currency-conversion)
1. [Additional Information](#additional-information)
    1. [Reference Documentation](#reference-documentation)
    1. [Guides](#guides)


## Features
- Utilises exchange rates provided by the [Currency Exchange Service](currency-exchange-service/README.MD) to perform currency conversion.
- [Spring Cloud OpenFeign](https://cloud.spring.io/spring-cloud-openfeign/reference/html/)
- [Spring Cloud LoadBalancer](https://docs.spring.io/spring-cloud-commons/docs/current/reference/html/#spring-cloud-loadbalancer)
- Fault tolerance via [Resilience4j](https://github.com/resilience4j/resilience4j)
- [OpenAPI Swagger UI](http://localhost:8100/swagger-ui.html)
- [Spring Boot - Actuator](http://localhost:8100/actuator)
- H2 database

## Instructions
### Setup
#### Environment variables
Set the following environment variables
- **CURRENCY_EXCHANGE_URL** - the url of the currency-exchange microservice to utilise

  E.g. `CURRENCY_EXCHANGE_URL=http://localhost:8000` will connect to a local currency-exchange microservice running on the default port.

**Note:** [set-localdev-env.bat](set-localdev-env.bat) will set these variable for a localhost default environment.


### Build, Test and Run
The application may be built and run via an IDE or terminal commands.

#### Integration test prerequisites
- Set service environment variables using [set-localdev-env.bat](set-localdev-env.bat)
- Set the environment variable `ENV=integration-test`
- The following services must be running in order to perform tests
    - [Currency Exchange Service](../currency-exchange-service/README.MD)
    
       **Note:** Must be running in one of the following profiles; *'dev'*, *'unit_test'*, *'qa'* or *'integration'*.
    
       E.g. the [Java VM options](https://docs.oracle.com/javase/8/docs/platform/jvmti/jvmti.html#tooloptions) may be set in Windows using 
     
       ```shell
       > set JAVA_TOOL_OPTIONS=-Dspring.profiles.active=dev -Dspring.cloud.config.profile=dev
       ```
#### Build
Open a terminal in the `currency-conversion-service` folder and run
```shell
$ mvn clean package
```
or on Windows run
```shell
> build.cmd
```
#### Run
Open a terminal in the `currency-conversion-service` folder and run
```shell
$ java -jar target/currency-conversion-service-0.0.1-SNAPSHOT-K.jar
```
or on Windows run
```shell
> run.cmd
```

### Docker
To build a docker image, open a terminal in the `currency-conversion-service` folder and run
```shell
> dockerbuild.cmd
```
Or alternatively run the maven `spring-boot:build-image` goal in an IDE.

## Actuator
http://localhost:8100/actuator

## OpenAPI Swagger UI
The Eureka dashboard is available at http://localhost:8100/swagger-ui.html

### Currency conversion

`GET` `/currency-conversion/from/{from}/to/{to}/quantity/{quantity}`

   `{from}` - ISO 4217 currency code of currency to convert from, e.g. USD

   `{to}` - ISO 4217 currency code of currency to convert to, e.g. EUR

   `{quantity}` - quantity of from currency to convert, e.g. 10.3

See [docs.openexchangerates.org](https://docs.openexchangerates.org/docs/supported-currencies) for list of supported currencies.

E.g. Convert $10.30 United States Dollar to Euro

| Access | URL |
|--------|-----|
| Directly | http://localhost:8100/currency-conversion/from/USD/to/EUR/quantity/10.3 |

```json
{
  "id": 1,
  "fromCurrency": "usd",
  "toCurrency": "eur",
  "conversionMultiple": 0.83,
  "environment": "8000",
  "rateTimestamp": 1612382400,
  "transactionTimestamp": 1612900097,
  "quantity": 10.3,
  "totalCalculatedAmount": 8.549
}
```

## Additional Information

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.2/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.2/maven-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.4.2/reference/htmlsingle/#using-boot-devtools)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.4.2/reference/htmlsingle/#production-ready)
* [Eureka Server](https://docs.spring.io/spring-cloud-netflix/docs/current/reference/html/#spring-cloud-eureka-server)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
* [Service Registration and Discovery with Eureka and Spring Cloud](https://spring.io/guides/gs/service-registration-and-discovery/)
