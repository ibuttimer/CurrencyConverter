package com.example.microservices.currencyexchangeservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Task to update exchange rates
 */
@Component
public class ScheduledExchangeRateSourceTask {

    @Autowired
    private ExchangeRateSourceService service;

    // update every hour
    private static final int MILLISEC_PER_HOUR = 60 * 60 * 1000;

    @Scheduled(initialDelay = MILLISEC_PER_HOUR, fixedRate = MILLISEC_PER_HOUR)
    public void checkServiceConfig() {
        service.updateRates();
    }
}
