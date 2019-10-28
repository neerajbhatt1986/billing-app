package com.bill.customer.exception;

public class AuthenticationException extends RuntimeException{

    public AuthenticationException(String message){
        super(message);
    }

}
