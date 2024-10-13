package com.antran.projectevent.model.dto;

import com.antran.projectevent.constant.enums.Sex;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String mainEmail;
    private String mainPhoneNumber;
    private String fullName;
    private LocalDate birthDate;
    private Sex sex;
    private String mainAddress;
}


