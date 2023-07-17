# Configuration Server

# Table of Contents
1. [Features](#features)
2. [Instructions](#instructions)
    1. [Setup](#setup)
      1. [Repository](#repository)
      2. [Environment variables](#environment-variables)
    2. [Build and Run](#build-and-run)
    3. [Docker](#docker)
3. [Actuator](#actuator)
4. [Configurations](#configurations)
5. [Additional Information](#additional-information)
   1. [Reference Documentation](#reference-documentation)
   2. [Guides](#guides)

## Features

- A Spring Cloud Configuration Server providing support for externalized configuration.
- Configurations stored in a local git repository.
- [Spring Boot - Actuator](http://localhost:8888/actuator)
- Eureka client

## Instructions
### Setup
#### Repository
Change to the [git-localconfig-repo](../git-localconfig-repo) folder on the local machine, and initialise the configuration repository. On Windows run

   ```shell
   > setup.bat
   ```
   or on linux
   ```shell
   $ setup.sh
   ```

#### Environment variables
Set the following environment variables
- **SERVICE_URL_DEFAULT_ZONE** - [default Eureka service URL](https://cloud.spring.io/spring-cloud-netflix/multi/multi__service_discovery_eureka_clients.html) for clients

  E.g. `SERVICE_URL_DEFAULT_ZONE=http://localhost:8761/eureka` will connect to a local Eureka server running on the default port.

  See [Eureka default zone setting is not picked up when using environment variables](https://github.com/spring-cloud/spring-cloud-netflix/issues/2541) for additional information.

- **API_GATEWAY_URL** - url of [API gateway](https://cloud.spring.io/spring-cloud-gateway/reference/html/)

  E.g. `API_GATEWAY_URL=http://localhost:8765/` will connect to a local API gateway server running on port 8765.

- **CONFIG_GIT_URI** - uri of the git repository of configurations

  E.g. `CONFIG_GIT_URI=file:///C:/path/to/project/git-localconfig-repo` will connect to a local repository on a Windows machine.
  
  *On Windows, you need an extra "/" in the file URL if it is absolute with a drive prefix*.
  See [Spring Cloud Config Server](https://cloud.spring.io/spring-cloud-config/multi/multi__spring_cloud_config_server.html#_spring_cloud_config_server)
  for more information.


**Note:** [set-localdev-env.bat](set-localdev-env.bat) will set these variable for a localhost default environment.

### Build and Run
Open a terminal in the `spring-cloud-config-server` folder and run
```shell
$ mvn clean package
$ java -jar target/spring-cloud-config-server-0.0.2-SNAPSHOT.jar
```
or on Windows run
```shell
> build.cmd
> run.cmd
```
Alternatively, build and run via an IDE.

### Docker
To build a docker image, open a terminal in the `spring-cloud-config-server` folder and run
```shell
> dockerbuild.cmd
```
Or alternatively run the maven `spring-boot:build-image` goal in an IDE.

## Actuator
http://localhost:8888/actuator

## Configurations
| Service             | Configuration Server                              |
|---------------------|---------------------------------------------------|
| currency-exchange   | http://localhost:8888/currency-exchange/default   |
| currency-conversion | http://localhost:8888/currency-conversion/default |

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
