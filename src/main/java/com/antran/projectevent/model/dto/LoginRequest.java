package com.antran.projectevent.model.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String password;
    private String identifier;

}
