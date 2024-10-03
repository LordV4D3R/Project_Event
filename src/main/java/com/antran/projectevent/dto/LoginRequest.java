package com.antran.projectevent.dto;

public class LoginRequest {
    private String password;
    private String identifier;

    //Getter and Setter

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
