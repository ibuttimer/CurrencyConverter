package com.example.microservices.currencyexchangeservice.repository;

import com.example.microservices.currencyexchangeservice.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
}
