package com.billing.client;

import com.billing.dto.ErrorResponse;
import com.billing.exception.AppException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AppErrorDecoder implements ErrorDecoder{
    @Autowired
    ObjectMapper objectMapper;

    private final ErrorDecoder defaultErrorDecoder = new ErrorDecoder.Default();

    @Override
    public Exception decode(String methodKey, Response response)  {
        if (response.status() >= 400 && response.status() <= 499) {
            try{
                String message = IOUtils.toString(response.body().asInputStream());
                ErrorResponse errorResponse = objectMapper.readValue(message, ErrorResponse.class);
                throw new AppException(errorResponse.getMessage());
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }
}
