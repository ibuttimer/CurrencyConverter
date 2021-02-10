# Currency Conversion

A microservice based application providing currency conversion, 
based on the core concepts from the [Master Microservices with Spring Boot and Spring Cloud](https://www.udemy.com/course/microservices-with-spring-boot-and-spring-cloud/)
course on [Udemy](https://www.udemy.com/).

# Table of Contents
1. [Naming server](#naming-server)
1. [Configuration server](#configuration-server)
1. [API Gateway server](#api-gateway-server)
1. [Currency Exchange Service](#currency-exchange-service)
1. [Currency Conversion Service](#currency-conversion-service)
1. [Architecture Diagram](#architecture-diagram)

## Naming server
A Netflix Eureka server providing a microservice registration and discovery service.
See [naming-server](naming-server/README.MD) for more details, and [dashboard](naming-server/README.MD#dashboard) for details of the Eureka dashboard in particular.

## Configuration server
A Spring Cloud Configuration Server providing support for externalized configuration, from a local git repository.
See [spring-cloud-config-server](spring-cloud-config-server/README.MD) for more details.

## API Gateway server
A Spring Cloud Gateway Server providing API routing.
See [api-gateway](api-gateway/README.MD) for more details.

## Currency Exchange Service
A microservice utilising [Open Exchange Rates](https://openexchangerates.org/) to provide a currency exchange rate service.
See [currency-exchange-service](currency-exchange-service/README.MD) for more details.

- [Actuator](http://localhost:8000/actuator)
- [OpenAPI Swagger UI](http://localhost:8000/swagger-ui.html)
- [Exchange rate example](currency-exchange-service/README.MD#get-exchange-rate)

## Currency Conversion Service
A microservice utilising the [Currency Exchange Service](#currency-exchange-service) to provide a currency conversion service.
See [currency-conversion-service](currency-conversion-service/README.MD) for more details.

- [Actuator](http://localhost:8100/actuator)
- [OpenAPI Swagger UI](http://localhost:8100/swagger-ui.html)
- [Currency conversion example](currency-conversion-service/README.MD#currency-conversion)

## Architecture Diagram
![Architecture Diagram](misc/microservices.jpg)