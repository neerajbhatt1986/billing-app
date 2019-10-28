package com.bill.customer.dto;

import javax.validation.constraints.NotBlank;

public class UserRegisterRequest {

    @NotBlank
    String username;
    @NotBlank
    String name;
    @NotBlank
    String password;
    @NotBlank
    String password2;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
