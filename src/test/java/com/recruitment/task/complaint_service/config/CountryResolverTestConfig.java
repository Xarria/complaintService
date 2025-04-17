package com.recruitment.task.complaint_service.config;

import com.recruitment.task.complaint_service.domain.service.CountryResolver;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CountryResolverTestConfig {
    @Bean
    public CountryResolver countryResolver() {
        return ip -> "127.0.0.1".equals(ip) ? "Undefined" : "PL";
    }
}