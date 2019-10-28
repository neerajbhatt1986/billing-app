package com.bill.customer.config.security;

import com.bill.customer.dto.response.ErrorResponse;
import com.bill.customer.exception.AuthenticationException;
import com.bill.customer.exception.CustomerPortalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class AppExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(AuthenticationException.class);

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleException(AuthenticationException securityInterceptor){
        List<String> details = new ArrayList<>();
        details.add(securityInterceptor.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), details);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomerPortalException.class)
    public ResponseEntity<Object> handleException(CustomerPortalException customerPortalException){
        List<String> details = new ArrayList<>();
        details.add(customerPortalException.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception){
        log.error(exception.getMessage(), exception);
        List<String> details = new ArrayList<>();
        details.add(exception.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), details);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
