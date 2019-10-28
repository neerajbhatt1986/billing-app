package com.bill.customer.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${mailjet.sender.email}")
    String mailjetSenderEmail;

    @Value("${spring.application.name}")
    String applicationName;

    @Value("${activation.base.url}")
    String activationBaseUrl;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expired.in}")
    long expirationTime;

    public String getMailjetSenderEmail() {
        return mailjetSenderEmail;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getActivationBaseUrl() {
        return activationBaseUrl;
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public long getExpirationTime() {
        return expirationTime;
    }
}
