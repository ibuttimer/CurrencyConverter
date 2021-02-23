# Currency Conversion (Kubernetes)

A microservice based application providing currency conversion, 
based on the core concepts from the [Master Microservices with Spring Boot and Spring Cloud](https://www.udemy.com/course/microservices-with-spring-boot-and-spring-cloud/)
course on [Udemy](https://www.udemy.com/).

This is the [Kubernetes](https://kubernetes.io/) branch of the application. It utilises Google Kubernetes Engine on [Google Cloud Platform](https://cloud.google.com/)
to run the microservices.

# Table of Contents
1. [Services](#services)
    1. [Currency Exchange Service](#currency-exchange-service)
    1. [Currency Conversion Service](#currency-conversion-service)
1. [Architecture Diagram](#architecture-diagram)
1. [Setup and Build](#setup-and-build)
    1. [Build Docker image](#build-docker-image)
    1. [Run](#run)
        1. [Windows](#windows)
        1. [Service URLs](#service-urls)
1. [Kubernetes](#kubernetes)
    1. [Setup](#setup)
    1. [gcloud CLI](#gcloud-cli)
     1. [Deploy](#deploy)
        1. [Manually](#manually)
        1. [YAML configuration](#yaml-configuration)


## Services
### Currency Exchange Service
A microservice utilising [Open Exchange Rates](https://openexchangerates.org/) to provide a currency exchange rate service.
See [currency-exchange-service](currency-exchange-service/README.MD) for more details.

- [Actuator](http://localhost:8000/actuator)
- [OpenAPI Swagger UI](http://localhost:8000/swagger-ui.html)
- [Exchange rate example](currency-exchange-service/README.MD#get-exchange-rate)

### Currency Conversion Service
A microservice utilising the [Currency Exchange Service](#currency-exchange-service) to provide a currency conversion service.
See [currency-conversion-service](currency-conversion-service/README.MD) for more details.

- [Actuator](http://localhost:8100/actuator)
- [OpenAPI Swagger UI](http://localhost:8100/swagger-ui.html)
- [Currency conversion example](currency-conversion-service/README.MD#currency-conversion)

## Architecture Diagram
![Architecture Diagram](misc/microservices.jpg)

## Setup and Build
See the individual services for instructions:
- [Currency Exchange Service](currency-exchange-service/README.MD#instructions)
- [Currency Conversion Service](currency-conversion-service/README.MD#instructions)

### Build Docker image
See the individual services for instructions:
- [Currency Exchange Service](currency-exchange-service/README.MD#docker)
- [Currency Conversion Service](currency-conversion-service/README.MD#docker)

### Run
#### Windows
To run all microservices: 
- Open Windows Powershell in the [docker](docker) folder.
- Ensure Docker Desktop is running
- Run `docker-compose up`

#### Service URLs

| Service | URL | Credentials |
|---------|-----|------------|
| Currency exchange | See [Get exchange rate](currency-exchange-service/README.MD#get-exchange-rate) | - |
| Currency conversion | See [Currency conversion](currency-conversion-service/README.MD#currency-conversion) | - |

## Kubernetes
### Setup
- Push docker images to [Docker Hub](https://hub.docker.com/)
  ```shell
  > docker login
  > docker push ibuttimer/ccm-currency-exchange-service:0.0.1-SNAPSHOT-K
  > docker push ibuttimer/ccm-currency-conversion-service:0.0.1-SNAPSHOT-K
  ```
- Create a project on [Google Cloud Platform](https://cloud.google.com/)

  e.g. A project titled *Udemy Kubernetes* will get an id like `udemy-kubernetes-305209`

- Create a cluster on Google Kubernetes Engine

  e.g. `currency-cluster`

### gcloud CLI
Configure gcloud CLI for project
   ```shell
   > gcloud config set project udemy-kubernetes-305209
   > gcloud container clusters get-credentials currency-cluster
   ```
  **Note:** If the default compute/zone does not match the compute/zone of the project, either [update the
  default setting](https://cloud.google.com/compute/docs/gcloud-compute#set_default_zone_and_region_in_your_local_client),
  or specify the correct compute/zone, e.g.
   ```shell
   > gcloud container clusters get-credentials currency-cluster --zone=us-central1-c
   ```

### Deploy
- Verify no unnecessary services are running, by executing
   ```shell
   > kubectl get all
    NAME                 TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
    service/kubernetes   ClusterIP   10.12.0.1    <none>        443/TCP   72m
   ```
    If any unnecessary services other than `service/kubernetes` is running, delete them.
    E.g. if the service name were `unnessary-service`
   ```shell
   > kubectl delete all -l app=unnessary-service
   ```
    This may also be done from the *Workloads* and *Services & Ingress* tabs of *Kubernetes Engine*
    on [Google Cloud Platform](https://cloud.google.com/).
#### Manually
- Create currency exchange service deployment
   ```shell
   > kubectl create deployment currency-exchange --image=ibuttimer/ccm-currency-exchange-service:0.0.1-SNAPSHOT-K
   ```
- Expose currency exchange service
   ```shell
   > kubectl expose deployment currency-exchange --type=LoadBalancer --port=8000
   ```
- Create currency conversion service deployment
   ```shell
   > kubectl create deployment currency-conversion --image=ibuttimer/ccm-currency-conversion-service:0.0.1-SNAPSHOT-K
   ```
- Expose currency conversion service
   ```shell
   > kubectl expose deployment currency-conversion --type=LoadBalancer --port=8100
   ```
- Verify deployments and services
   ```shell
   > kubectl get pods
   NAME                                   READY   STATUS    RESTARTS   AGE
   currency-conversion-7578694c5f-bg9nv   1/1     Running   0          23m
   currency-exchange-7998864f48-7hfl5     1/1     Running   0          32m
   > kubectl get deployments
   NAME                  READY   UP-TO-DATE   AVAILABLE   AGE
   currency-conversion   1/1     1            1           80m
   currency-exchange     1/1     1            1           34m
   > kubectl get services
   NAME                  TYPE           CLUSTER-IP     EXTERNAL-IP      PORT(S)          AGE
   currency-conversion   LoadBalancer   10.76.10.157   130.211.192.40   8100:32635/TCP   80m
   currency-exchange     LoadBalancer   10.76.9.197    35.202.138.168   8000:30391/TCP   47m
   kubernetes            ClusterIP      10.76.0.1      <none>           443/TCP          85m
   ```
- Verify functionality
  
    **Note:** Remember to replace the ip address with those specified by `EXTERNAL-IP` in the `kubectl get services` output. 
    - Browser
      
        http://35.202.138.168:8000/currency-exchange/from/usd/to/eur
        http://130.211.192.40:8100/currency-conversion/from/usd/to/eur/quantity/10
    - PowerShell
   ```shell
   > Invoke-WebRequest -Uri http://35.202.138.168:8000/currency-exchange/from/usd/to/eur -UseBasicParsing
   > Invoke-WebRequest -Uri http://130.211.192.40:8100/currency-conversion/from/usd/to/eur/quantity/10 -UseBasicParsing
   ```

#### YAML configuration
Use a YAML configuration to deploy services
    
- From the [kubernetes](kubernetes) folder, execute
   ```shell
   > kubectl apply -f currency-exchange.yaml
   > kubectl apply -f currency-conversion.yaml
   ```
