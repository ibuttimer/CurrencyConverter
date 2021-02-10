package com.example.microservices.currencyconversionservice.repository;

import com.example.microservices.currencyconversionservice.model.CurrencyConversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyConversionRepository extends JpaRepository<CurrencyConversion, Long> {
}
