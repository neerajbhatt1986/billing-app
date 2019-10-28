package com.bill.customer.dto.request;

public class MailAuthenticationRequest {

    private String authenticationCode;
    private String email;

    public String getAuthenticationCode() {
        return authenticationCode;
    }

    public void setAuthenticationCode(String authenticationCode) {
        this.authenticationCode = authenticationCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
