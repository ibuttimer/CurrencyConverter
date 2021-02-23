package com.example.microservices.currencyexchangeservice.common;

public enum Flavour {
    DEFAULT,        // default microservice
    KUBERNETES;     // kubernetes microservice

    public boolean is(String flavour) {
        return this.name().equalsIgnoreCase(flavour);
    }

}
