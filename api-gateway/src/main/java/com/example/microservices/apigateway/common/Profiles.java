package com.example.microservices.apigateway.common;

public enum Profiles {
    DEFAULT, DEV, UNIT_TEST, QA, INTEGRATION, PRODUCTION;

    public static Profiles from(String profile) throws IllegalArgumentException {
        return valueOf(profile.toUpperCase());
    }
}
