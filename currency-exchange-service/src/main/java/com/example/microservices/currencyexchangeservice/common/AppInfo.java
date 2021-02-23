package com.example.microservices.currencyexchangeservice.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representing the app info portion of the response of the actuator/info endpoint
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppInfo {
    private String name;
    private String description;
    private String version;
}
