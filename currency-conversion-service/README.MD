# Currency Conversion Service

# Table of Contents
1. [Features](#features)
1. [Instructions](#instructions)
   1. [Setup](#setup)
       1. [MySQL database](#mysql-database)
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
- [Zipkin client](https://docs.spring.io/spring-cloud-sleuth/docs/current-SNAPSHOT/reference/html/index.html)  
- Eureka client
- MySQL database
- H2 database (for test)

## Instructions
### Setup
#### MySQL database
- Execute the [create_mysql_schema.sql](src/main/resources/create_mysql_schema.sql) script on the MySQL server to create the schema and user.
- Save a copy of the file [mysql.properties.sample](src/main/resources/mysql.properties.sample)
  as `mysql.properties`.
- Update `username` and `password` as required. 

#### Environment variables
Set the following environment variables
- **SERVICE_URL_DEFAULT_ZONE** - [default Eureka service URL](https://cloud.spring.io/spring-cloud-netflix/multi/multi__service_discovery_eureka_clients.html) for clients

  E.g. `SERVICE_URL_DEFAULT_ZONE=http://localhost:8761/eureka` will connect to a local Eureka server running on the default port.

  See [Eureka default zone setting is not picked up when using environment variables](https://github.com/spring-cloud/spring-cloud-netflix/issues/2541) for additional information.

- **API_GATEWAY_URL** - url of [API gateway](https://cloud.spring.io/spring-cloud-gateway/reference/html/)

  E.g. `API_GATEWAY_URL=http://localhost:8765/` will connect to a local API gateway server running on port 8765.

- **CONFIG_SERVER_URL** - url of [configuration server](https://cloud.spring.io/spring-cloud-config/reference/html/)

  E.g. `CONFIG_SERVER_URL=http://localhost:8888/` will connect to a local configuration server running on the default port.

- **ZIPKIN_SERVER_URL** - url of [Zipkin server](https://docs.spring.io/spring-cloud-sleuth/docs/current-SNAPSHOT/reference/html/index.html)

  E.g. `ZIPKIN_SERVER_URL=localhost:9411/` will connect to a local Spring Cloud Sleuth server running on the default port.

- **MYSQL_CONVERSION_URL** - the [hosts](https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-jdbc-url-format.html) element of the MySQL JDBC connection

  E.g. `MYSQL_CONVERSION_URL=//localhost:3306/currency_conversion` will connect to a local MySQL server running on the default port.

**Note:** [set-localdev-env.bat](set-localdev-env.bat) will set these variable for a localhost default environment.


### Build, Test and Run
The application may be built and run via an IDE or terminal commands.

#### Integration test prerequisites
- Set service environment variables using [set-localdev-env.bat](set-localdev-env.bat)
- Set the environment variable `ENV=integration-test`
- The following services must be running in order to perform tests
    - [Naming Server](../naming-server/README.MD)
    - [Configuration Server](../spring-cloud-config-server/README.MD)
    - [API Gateway](../api-gateway/README.MD)
    - [Currency Exchange Service](../currency-exchange-service/README.MD)
    
       **Note:** Must be running in one of the following profiles; *'dev'*, *'unit_test'*, *'qa'* or *'integration'*.
    
       E.g. the [Java VM options](https://docs.oracle.com/javase/8/docs/platform/jvmti/jvmti.html#tooloptions) may be set in Windows using 
     
       ```shell
       set JAVA_TOOL_OPTIONS=-Dspring.profiles.active=dev -Dspring.cloud.config.profile=dev
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
$ java -jar target/currency-conversion-service-0.0.1-SNAPSHOT.jar
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
| API Gateway | http://localhost:8765/currency-conversion/from/USD/to/EUR/quantity/10.3 |

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
