package com.bill.customer.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class AppConfiguration implements WebMvcConfigurer {

    @Autowired
    SecurityInterceptor securityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(securityInterceptor).excludePathPatterns("/h2-console/**", "/user/register", "/login", "/activate/account/**", "/mail/authentication");
    }
}
