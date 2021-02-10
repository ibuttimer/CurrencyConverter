package com.example.microservices.currencyexchangeservice.config;

import com.example.microservices.currencyexchangeservice.common.Profiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfileInfoContributor implements InfoContributor {

    @Autowired
    private List<Profiles> activeProfiles;


    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("profiles", activeProfiles);
    }
}
