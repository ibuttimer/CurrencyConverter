package com.example.microservices.currencyconversionservice.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppInfo {
    private String name;
    private String description;
    private String version;
}
