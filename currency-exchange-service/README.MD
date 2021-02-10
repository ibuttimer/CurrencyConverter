# Currency Exchange Service

# Table of Contents
1. [Features](#features)
1. [Instructions](#instructions)
   1. [Setup](#setup)
      1. [API key](#api-key)
      1. [MySQL database](#mysql-database)
   1. [Build and Run](#build-and-run)
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
- Eureka client
- MySQL database
- H2 database (for test)

   The latest rates provided by [Open Exchange Rates](https://openexchangerates.org/) are stored in the database. 

## Instructions
### Setup
#### API key
- Save a copy of the file [api-keys.properties.sample](currency-exchange-service/src/main/resources/api-keys.properties.sample)
as `api-keys.properties`.
- Replace the `<app_id>` with an [Open Exchange Rates App ID](https://docs.openexchangerates.org/docs/authentication)  
#### MySQL database
- Execute the [create_mysql_schema.sql](currency-exchange-service/src/main/resources/create_mysql_schema.sql) script on the MySQL server to create the schema and user.
- Save a copy of the file [mysql.properties.sample](currency-exchange-service/src/main/resources/mysql.properties.sample)
  as `mysql.properties`.
- Replace the `<path to database>` with the url of the database, 
  e.g. `//localhost:3306/currency_exchange` will connect to a local MySQL server running on the default port.

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
