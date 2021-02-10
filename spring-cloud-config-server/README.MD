# Configuration Server

# Table of Contents
1. [Features](#features)
1. [Instructions](#instructions)
    1. [Setup](#setup)
    1. [Build and Run](#build-and-run)
1. [Actuator](#actuator)
1. [Configurations](#configurations)
1. [Additional Information](#additional-information)
   1. [Reference Documentation](#reference-documentation)
   1. [Guides](#guides)

## Features

- A Spring Cloud Configuration Server providing support for externalized configuration.
- Configurations stored in a local git repository.
- [Spring Boot - Actuator](http://localhost:8888/actuator)
- Eureka client

## Instructions
### Setup
After cloning the repository:
1. Change to the [git-localconfig-repo](git-localconfig-repo) folder on the local machine, and initialise the configuration repository. On Windows run

   ```
   > setup.bat
   ```
   or on linux
   ```
   $ setup.sh
   ```

2. Update `application.properties` to point to the local repository.
    - Save a copy of the file [src/main/resources/application.properties.sample](spring-cloud-config-server/src/main/resources/application.properties.sample)
      as `application.properties`.
    - Replace the `<uri to repository>` with the path to the [git-localconfig-repo](git-localconfig-repo) folder on the local machine.
      On a Windows machine, the path will take the form

        ```
        file:///C:/path/to/project/microservices/git-localconfig-repo
        ```
        See [Spring Cloud Config Server](https://cloud.spring.io/spring-cloud-config/multi/multi__spring_cloud_config_server.html#_spring_cloud_config_server)
      for more information.


### Build and Run
Open a terminal in the `spring-cloud-config-server` folder and run
```
$ mvn clean package
$ java -jar target/spring-cloud-config-server-0.0.1-SNAPSHOT.jar
```
or on Windows run
```
> build.cmd
> run.cmd
```
Alternatively, build and run via an IDE.

## Actuator
http://localhost:8888/actuator

## Configurations
|Service|Configuration Server|
|-------|--------------------|
|currency-exchange|http://localhost:8888/currency-exchange/default|
|currency-conversion|http://localhost:8888/currency-conversion/default|

## Additional Information

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.2/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.2/maven-plugin/reference/html/#build-image)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.4.2/reference/htmlsingle/#production-ready)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.4.2/reference/htmlsingle/#using-boot-devtools)

### Guides
The following guides illustrate how to use some features concretely:

* [Centralized Configuration](https://spring.io/guides/gs/centralized-configuration/)
