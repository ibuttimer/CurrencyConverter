package com.example.microservices.currencyconversionservice.proxy;

import com.example.microservices.currencyconversionservice.model.ExchangeRate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.example.microservices.currencyconversionservice.common.UrlConfig.*;

/* Kubernetes automatically created environment variables; e.g. CURRENCY_EXCHANGE_SERVICE_HOST is set for the 'currency-exchange' service
    but the 'currency-exchange' service has to be running first
@FeignClient(name = CURRENCY_EXCHANGE_APP_NAME, url = "${CURRENCY_EXCHANGE_SERVICE_HOST:http://localhost}:8000")
 */
@FeignClient(name = CURRENCY_EXCHANGE_APP_NAME, url = "${CURRENCY_EXCHANGE_URL:http://localhost:8000}")
public interface CurrencyConversionProxy {

    @GetMapping(EXCHANGE_URL)
    ExchangeRate getCurrencyExchange(@PathVariable(name = FROM_PV) String fromCurrency,
                                     @PathVariable(name = TO_PV) String toCurrency);
}
