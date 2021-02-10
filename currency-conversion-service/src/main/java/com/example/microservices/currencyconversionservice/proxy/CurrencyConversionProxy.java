package com.example.microservices.currencyconversionservice.proxy;

import com.example.microservices.currencyconversionservice.model.ExchangeRate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.example.microservices.currencyconversionservice.common.UrlConfig.*;

@FeignClient(name = CURRENCY_EXCHANGE_APP_NAME)  // client-side load balancing, when url not specified & registered with eureka server
public interface CurrencyConversionProxy {

    @GetMapping(EXCHANGE_URL)
    ExchangeRate getCurrencyExchange(@PathVariable(name = FROM_PV) String fromCurrency,
                                     @PathVariable(name = TO_PV) String toCurrency);
}
