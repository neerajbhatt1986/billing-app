package com.bill.customer.dto.response;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {
    int errorCode;
    List<String> errors = new ArrayList<>();

    public ErrorResponse(int errorCode, List<String> errors) {
        this.errorCode = errorCode;
        this.errors = errors;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
