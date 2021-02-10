package com.example.microservices.currencyconversionservice.common;

public class UrlConfig {

    private UrlConfig() {
        // non instantiable
    }

    public static final String CURRENCY_CONVERSION_APP_NAME = "currency-conversion";
    public static final String CURRENCY_EXCHANGE_APP_NAME = "currency-exchange";
    public static final String API_GATEWAY_APP_NAME = "api-gateway";
    public static final String NAMING_SERVER_APP_NAME = "naming-server";
    public static final String CONFIG_SERVER_APP_NAME = "config-server";

    public static final String FROM_PV = "from";
    public static final String TO_PV = "to";
    public static final String QUANTITY_PV = "quantity";
    public static final String FROM_PATTERN = "{"+FROM_PV+"}";
    public static final String TO_PATTERN = "{"+TO_PV+"}";
    public static final String QUANTITY_PATTERN = "{"+QUANTITY_PV+"}";

    public static final String CONVERSION_BASE_URL = "/currency-conversion";
    public static final String CONVERSION_PART_URL = "/from/"+FROM_PATTERN+"/to/"+TO_PATTERN+"/quantity/"+QUANTITY_PATTERN;
    public static final String CONVERSION_URL = CONVERSION_BASE_URL + CONVERSION_PART_URL;

    public static final String EXCHANGE_BASE_URL = "/currency-exchange";
    public static final String EXCHANGE_PART_URL = "/from/"+FROM_PATTERN+"/to/"+TO_PATTERN;
    public static final String EXCHANGE_URL = EXCHANGE_BASE_URL + EXCHANGE_PART_URL;

    public static final String RATES_UPDATE_PART_URL = "/rates-update";
    public static final String CURRENCY_EXCHANGE_RATES_UPDATE_URL = EXCHANGE_BASE_URL + RATES_UPDATE_PART_URL;

    public static final String ACTUATOR_INFO_PART_URL = "/actuator/info";
    public static final String CURRENCY_EXCHANGE_ACTUATOR_INFO_URL = EXCHANGE_BASE_URL + ACTUATOR_INFO_PART_URL;
    public static final String CURRENCY_CONVERSION_ACTUATOR_INFO_URL = CONVERSION_BASE_URL + ACTUATOR_INFO_PART_URL;

}
