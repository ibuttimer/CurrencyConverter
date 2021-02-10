package com.example.microservices.currencyexchangeservice.service;

import com.example.microservices.currencyexchangeservice.config.Config;
import com.example.microservices.currencyexchangeservice.exception.CurrencyNotFoundException;
import com.example.microservices.currencyexchangeservice.model.ExchangeRate;
import com.example.microservices.currencyexchangeservice.model.ExchangeRateSource;
import com.example.microservices.currencyexchangeservice.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.microservices.currencyexchangeservice.model.ExchangeRate.nowTimestamp;

@Service
public class ExchangeRateService {

    @Autowired
    private Config config;

    @Autowired
    private ExchangeRateSourceService exchangeRateSourceService;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    public ExchangeRate getExchangeRate(String fromCurrency, String toCurrency, String environment) {
        ExchangeRateSource rateSource = exchangeRateSourceService.getLatest();

        /* https://openexchangerates.org/documentation
         * For example, if you need to convert British Pounds (GBP) to Hong Kong Dollars (HKD),
         * the calculation is equal to converting the GBP to USD first, then converting those USD to
         * HKD - but we can do this in a single step.
         * // let usd_gbp = 0.6438, usd_hkd = 7.7668
         * gbp_hkd = usd_hkd * (1 / usd_gbp)
         * In other words, if you have the following exchange rates relative to US Dollars
         * (the common 'base'): USD/GBP 0.6438 and USD/HKD 7.7668, then you can calculate
         * GBP/HKD 12.0639 by multiplying 7.7668 by the inverse of 0.6438, or 7.7668 * (1 / 0.6438).
         */

        BigDecimal fromRate = getRate(rateSource, fromCurrency);
        BigDecimal toRate = getRate(rateSource, toCurrency);

        ExchangeRate rate = ExchangeRate.builder()
                    .fromCurrency(fromCurrency)
                    .toCurrency(toCurrency)
                    .conversionMultiple(
                            toRate.divide(fromRate, config.getRateScale(), RoundingMode.HALF_DOWN))
                    .rateTimestamp(rateSource.getTimestamp())
                    .transactionTimestamp(nowTimestamp())
                    .environment(environment)
                    .build();

        return exchangeRateRepository.save(rate);
    }

    /**
     * Get the rate for the specified currency
     * @param rateSource - rates data to use
     * @param currency - currency to get rate for
     * @return
     */
    private BigDecimal getRate(ExchangeRateSource rateSource, String currency) {
        AtomicReference<BigDecimal> rate = new AtomicReference<>();
        if (rateSource.getBase().equalsIgnoreCase(currency)) {
            rate.set(BigDecimal.ONE);
        } else {
            Map<String, BigDecimal> rates = rateSource.getRates();
            rates.keySet().stream()
                    .filter(k -> k.equalsIgnoreCase(currency))
                    .findFirst()
                    .ifPresentOrElse(k -> {
                        rate.set(rates.get(k));
                    }, () -> {
                        throw new CurrencyNotFoundException("Currency not found: '" + currency + "'");
                    });
        }
        return rate.get();
    }

}
