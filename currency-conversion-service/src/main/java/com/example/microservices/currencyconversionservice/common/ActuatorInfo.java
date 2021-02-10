package com.example.microservices.currencyconversionservice.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActuatorInfo {
    private AppInfo app;
    private List<String> profiles;
}
