package com.example.microservices.currencyexchangeservice.common;

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
