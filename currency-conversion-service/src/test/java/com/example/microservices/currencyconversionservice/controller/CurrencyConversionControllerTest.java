package com.example.microservices.currencyconversionservice.controller;

import com.example.microservices.currencyconversionservice.CurrencyConversionServiceApplication;
import com.example.microservices.currencyconversionservice.common.ActuatorInfo;
import com.example.microservices.currencyconversionservice.common.Profiles;
import com.example.microservices.currencyconversionservice.model.ExchangeRateSource;
import com.example.microservices.currencyconversionservice.service.CurrencyConversionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.hamcrest.number.BigDecimalCloseTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.example.microservices.currencyconversionservice.common.Currency.CURRENCIES;
import static com.example.microservices.currencyconversionservice.common.Currency.EUR;
import static com.example.microservices.currencyconversionservice.common.UrlConfig.*;
import static com.example.microservices.currencyconversionservice.model.ExchangeRate.nowTimestamp;
import static org.awaitility.Awaitility.with;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = CurrencyConversionServiceApplication.class
)
@EnabledIfEnvironmentVariable(named = "ENV", matches = "integration-test")
@AutoConfigureMockMvc
class CurrencyConversionControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    CurrencyConversionService currencyConversionService;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Value("${currency-conversion.api-gateway}")
    private String apiGateway;

    static List<Profiles> testProfiles = List.of(
            Profiles.DEV, Profiles.UNIT_TEST,
            Profiles.QA, Profiles.INTEGRATION);

    static List<String> testApps = List.of(
            API_GATEWAY_APP_NAME,
            CONFIG_SERVER_APP_NAME,
            CURRENCY_EXCHANGE_APP_NAME
    );

    static final String BASE_CURRENCY = EUR;

    ExchangeRateSource rateSource;

    @BeforeEach
    public void beforeEach() throws Exception {

        // use index of currency description as rate
        AtomicInteger index  = new AtomicInteger();
        ExchangeRateSource exchangeRateSource = ExchangeRateSource.builder()
                .base(BASE_CURRENCY)
                .timestamp(nowTimestamp())
                .rates(CURRENCIES.entrySet().stream()
                        .filter(e -> !e.getKey().equals(BASE_CURRENCY))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e -> BigDecimal.valueOf(index.incrementAndGet())))
                )
                .build();

        for (String service : testApps) {
            if (getServiceInstance(service).isEmpty()){
                fail("'" + service + "' service not available");
            }
            if (service.equals(CURRENCY_EXCHANGE_APP_NAME)) {
                ResponseEntity<ActuatorInfo> actuatorInfoResponse = getActuatorInfo(getGatewayUrl(CURRENCY_EXCHANGE_ACTUATOR_INFO_URL));

                if (actuatorInfoResponse.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
                    // actuator not ready, so wait for redirect response
                    with().pollInterval(100, TimeUnit.MILLISECONDS)
                            .and().with().pollDelay(20, TimeUnit.MILLISECONDS)
                            .and().atMost(5, TimeUnit.SECONDS)
                            .await("service available").until(
                                new GetActuatorInfoCallable(testRestTemplate, getGatewayUrl(CURRENCY_EXCHANGE_ACTUATOR_INFO_URL)), equalTo(true));
                }

                actuatorInfoResponse = getActuatorInfo(getGatewayUrl(CURRENCY_EXCHANGE_ACTUATOR_INFO_URL));
                if (actuatorInfoResponse.getStatusCode().is3xxRedirection()) {
                    // get response from redirected url
                    actuatorInfoResponse = testRestTemplate
                            .getForEntity(
                                    actuatorInfoResponse.getHeaders().getLocation(), ActuatorInfo.class);
                }

                if (Arrays.stream(
                        Objects.requireNonNull(
                                actuatorInfoResponse.getBody()).getProfiles().toArray(new String[0]))
                            .map(Profiles::from)
                            .filter(p -> testProfiles.contains(p))
                            .findFirst()
                            .isEmpty()) {
                    fail("'" + service + "' service not in a test profile");
                }

                // save rates for test
                rateSource = saveRates(exchangeRateSource);
            }
        }
    }

    private ResponseEntity<ActuatorInfo> getActuatorInfo(String url) {
         return testRestTemplate.getForEntity(url, ActuatorInfo.class);

    }

    static class GetActuatorInfoCallable implements Callable<Boolean> {

        TestRestTemplate testRestTemplate;
        String url;

        public GetActuatorInfoCallable(TestRestTemplate testRestTemplate, String url) {
            this.testRestTemplate = testRestTemplate;
            this.url = url;
        }

        @Override
        public Boolean call() throws Exception {
            return testRestTemplate.getForEntity(url, ActuatorInfo.class)
                    .getStatusCode().is3xxRedirection();
        }
    }



    @DisplayName("Convert currency")
    @Test
    public void convertCurrency() throws Exception {
        for (Map.Entry<String, String> entry : CURRENCIES.entrySet()) {

            if (!entry.getKey().equals(BASE_CURRENCY)) {
                BigDecimal multiple = rateSource.getRates().get(entry.getKey());
                BigDecimal quantity = BigDecimal.TEN;
                BigDecimal amount = multiple.multiply(quantity);

                mvc.perform(
                        get(CONVERSION_URL
                                .replace(FROM_PATTERN, BASE_CURRENCY)
                                .replace(TO_PATTERN, entry.getKey())
                                .replace(QUANTITY_PATTERN, quantity.toString())
                        ))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").exists())
                        .andExpect(jsonPath("$.fromCurrency").value(BASE_CURRENCY))
                        .andExpect(jsonPath("$.toCurrency").value(entry.getKey()))
                        .andExpect(jsonPath("$.conversionMultiple", new BigDecimalCloseTo(multiple, BigDecimal.ZERO), BigDecimal.class))
                        .andExpect(jsonPath("$.quantity").value(quantity))
                        .andExpect(jsonPath("$.totalCalculatedAmount", new BigDecimalCloseTo(amount, BigDecimal.ZERO), BigDecimal.class))
                        .andExpect(jsonPath("$.environment").exists())
                        .andExpect(jsonPath("$.rateTimestamp").value(rateSource.getTimestamp()))
                        .andExpect(jsonPath("$.transactionTimestamp").exists());
            }
        }
    }

    private ExchangeRateSource saveRates(ExchangeRateSource exchangeRateSource) throws Exception {
        ExchangeRateSource rateSource = testRestTemplate.postForEntity(
                getGatewayUrl(CURRENCY_EXCHANGE_RATES_UPDATE_URL),
                exchangeRateSource,
                ExchangeRateSource.class
        ).getBody();

        assert rateSource != null;
        assertEquals(rateSource.getTimestamp(), exchangeRateSource.getTimestamp());
        assertEquals(rateSource.getBase(), exchangeRateSource.getBase());
        assertEquals(rateSource.getRates(), exchangeRateSource.getRates());

        return rateSource;
    }

    private String getGatewayUrl(String path) {
        return UriComponentsBuilder
                .fromUriString(apiGateway)
                .path(path)
                .build()
                .toUriString();
    }


    /**
     * Get an instance of the specified microservice
     * @param serviceName - name of microservice
     * @return
     */
    private Optional<ServiceInstance> getServiceInstance(String serviceName) {
        return discoveryClient.getInstances(serviceName).stream()
                .filter(s -> s.getServiceId().equalsIgnoreCase(serviceName))
                .findFirst();
    }

}