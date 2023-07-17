package com.example.microservices.currencyexchangeservice.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

/**
 * Log all configuration properties on application startup
 * Based on <a href="https://gist.github.com/Christian-Oette/a0bb05cb703fea20d28bbf46b7ce7574">Christian-Oette/LoggingListener.java</a>
 * with minor changes.
 *
 * Usage:
 *  SpringApplication app = new SpringApplication(MyApplication.class);
 *  app.addListeners(new LoggingListener());
 *  app.run(args);
 */
@Slf4j
public class LoggingListener implements ApplicationListener<ApplicationPreparedEvent> {

    private final List<String> SENSITIVE_PROPERTIES = List.of("password", "credential", "token");
    private final int MAX_SENSITIVE_LEN = 4;

    @Override
    public void onApplicationEvent(final ApplicationPreparedEvent event) {
        logProperties(event.getApplicationContext());
    }

    public void logProperties(ApplicationContext applicationContext) {
        final Environment env = applicationContext.getEnvironment();
        final String newLine = System.lineSeparator();
        final StringBuilder stringBuilder = new StringBuilder(newLine);

        stringBuilder.append("====== Environment and configuration ======")
                .append(newLine)
                .append("Active profiles: ")
                .append(Arrays.toString(env.getActiveProfiles()))
                .append(newLine);

        if (env instanceof AbstractEnvironment) {
            final boolean logPasswords = env.getProperty("LOG_PASSWORDS", Boolean.class, Boolean.FALSE);
            final MutablePropertySources sources = ((AbstractEnvironment) env).getPropertySources();
            StreamSupport.stream(sources.spliterator(), false)
                    .filter(ps -> ps instanceof EnumerablePropertySource)
                    .map(this::castToEnumerablePropertySource)
                    .map(EnumerablePropertySource::getPropertyNames)
                    .flatMap(Arrays::stream)
                    .distinct()
                    .sorted()
                    .forEach(prop -> appendProperty(env, prop, stringBuilder, logPasswords));
        } else {
            stringBuilder.append("Unable to read properties");
        }
        log.info(stringBuilder.toString());
    }

    private EnumerablePropertySource<?> castToEnumerablePropertySource(final PropertySource<?> propertySource) {
        return (EnumerablePropertySource<?>) propertySource;
    }

    private void appendProperty(final Environment env, final String prop, final StringBuilder stringBuilder, final boolean logPasswords) {
        boolean isASensitiveProperty;
        if (logPasswords) {
            isASensitiveProperty = false;
        } else {
            final String propLower = prop.toLowerCase();
            isASensitiveProperty = SENSITIVE_PROPERTIES.stream()
                    .anyMatch(propLower::contains);
        }
        try {
            String value = env.getProperty(prop);
            if (isASensitiveProperty) {
                value = StringUtils.abbreviate(value, MAX_SENSITIVE_LEN);
            }
            stringBuilder.append(String.format("%s: %s%n", prop, value));
        } catch (Exception ex) {
            stringBuilder.append(String.format("%s: %s%n", prop, ex.getMessage()));
        }
    }
}