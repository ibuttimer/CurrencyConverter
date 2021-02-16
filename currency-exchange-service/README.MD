# Currency Exchange Service

# Table of Contents
1. [Features](#features)
1. [Instructions](#instructions)
   1. [Setup](#setup)
      1. [API key](#api-key)
      1. [MySQL database](#mysql-database)
      1. [Environment variables](#environment-variables)
   1. [Build and Run](#build-and-run)
   1. [Docker](#docker)
1. [Actuator](#actuator)
1. [OpenAPI Swagger UI](#openapi-swagger-ui)
   1. [Get exchange rate](#get-exchange-rate)
1. [Additional Information](#additional-information)
    1. [Reference Documentation](#reference-documentation)
    1. [Guides](#guides)

## Features
- Utilises [Open Exchange Rates](https://openexchangerates.org/) to provide the latest currency exchange rates.
- [OpenAPI Swagger UI](http://localhost:8000/swagger-ui.html)
- [Spring Boot - Actuator](http://localhost:8000/actuator)
- [Zipkin client](https://docs.spring.io/spring-cloud-sleuth/docs/current-SNAPSHOT/reference/html/index.html)
- Eureka client
- MySQL database
- H2 database (for test)

   The latest rates provided by [Open Exchange Rates](https://openexchangerates.org/) are stored in the database. 

## Instructions
### Setup
#### API key
- Save a copy of the file [api-keys.properties.sample](src/main/resources/api-keys.properties.sample)
as `api-keys.properties`.
- Replace the `<app_id>` with an [Open Exchange Rates App ID](https://docs.openexchangerates.org/docs/authentication)  

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

- **MYSQL_EXCHANGE_URL** - the [hosts](https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-jdbc-url-format.html) element of the MySQL JDBC connection

  E.g. `MYSQL_EXCHANGE_URL=//localhost:3306/currency_exchange` will connect to a local MySQL server running on the default port.

**Note:** [set-localdev-env.bat](set-localdev-env.bat) will set these variable for a localhost default environment.


### Build and Run
Open a terminal in the `currency-exchange-service` folder and run
```
$ mvn clean package
$ java -jar target/currency-exchange-service-0.0.1-SNAPSHOT.jar
```
or on Windows run
```
> build.cmd
> run.cmd
```
Alternatively, build and run via an IDE.

### Docker
To build a docker image, open a terminal in the `currency-exchange-service` folder and run
```
> dockerbuild.cmd
```
Or alternatively run the maven `spring-boot:build-image` goal in an IDE.

## Actuator
http://localhost:8000/actuator

## OpenAPI Swagger UI
The Eureka dashboard is available at http://localhost:8000/swagger-ui.html

### Get exchange rate

`GET` `/currency-exchange/from/{from}/to/{to}`

   `{from}` - ISO 4217 currency code of currency to convert from, e.g. USD

   `{to}` - ISO 4217 currency code of currency to convert to, e.g. EUR

See [docs.openexchangerates.org](https://docs.openexchangerates.org/docs/supported-currencies) for list of supported currencies.

E.g. The United States Dollar/Euro rate

| Access | URL |
|--------|-----|
| Directly | http://localhost:8000/currency-exchange/from/USD/to/EUR |
| API Gateway | http://localhost:8765/currency-exchange/from/USD/to/EUR |

```json
{
  "id": 1,
  "fromCurrency": "USD",
  "toCurrency": "EUR",
  "conversionMultiple": 0.83,
  "environment": "8000",
  "rateTimestamp": 1612382400,
  "transactionTimestamp": 1612882480
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
