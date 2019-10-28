package com.bill.customer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class ApplicationMessageSource {
    @Autowired
    MessageSource messageSource;

    public String getMessage(String key, Object[] args){
       return messageSource.getMessage(key, args, null);
    }
}
