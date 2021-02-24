# Naming Server

# Table of Contents
1. [Features](#features)
1. [Instructions](#instructions)
    1. [Build and Run](#build-and-run)
    1. [Docker](#docker)
1. [Dashboard](#dashboard)
1. [Additional Information](#additional-information)
    1. [Reference Documentation](#reference-documentation)
    1. [Guides](#guides)

## Features

- A Eureka Service Registry

## Instructions
### Build and Run
Open a terminal in the `naming-server` folder and run
```shell
$ mvn clean package
$ java -jar target/naming-server-0.0.1-SNAPSHOT.jar
```
or on Windows run
```shell
> build.cmd
> run.cmd
```
Alternatively, build and run via an IDE.

### Docker
To build a docker image, open a terminal in the `naming-server` folder and run
```shell
> dockerbuild.cmd
```
Or alternatively run the maven `spring-boot:build-image` goal in an IDE.

## Dashboard
The Eureka dashboard is available at http://localhost:8761/

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
