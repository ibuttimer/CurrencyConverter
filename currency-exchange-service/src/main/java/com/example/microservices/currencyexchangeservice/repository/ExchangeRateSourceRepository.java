package com.example.microservices.currencyexchangeservice.repository;

import com.example.microservices.currencyexchangeservice.model.ExchangeRateSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateSourceRepository extends JpaRepository<ExchangeRateSource, Long> {

    ExchangeRateSource findFirstByOrderByIdDesc();
}
