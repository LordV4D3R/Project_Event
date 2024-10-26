package com.antran.projectevent.controller;

import com.antran.projectevent.constant.common.BusinessResult;
import com.antran.projectevent.model.Account;
import com.antran.projectevent.model.dto.LoginRequest;
import com.antran.projectevent.model.dto.RegisterRequest;
import com.antran.projectevent.model.dto.TokenResponse;
import com.antran.projectevent.service.interfaceservice.IAccountService;
import com.antran.projectevent.service.interfaceservice.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*") // Allow all origins
public class AccountAuthenticationController {
    @Autowired
    private IAuthenticationService authenticationService;


    //Login
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        try {
//            TokenResponse tokenResponse = authenticationService.login(loginRequest);
//            return ResponseEntity.ok(tokenResponse);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//    }

    //Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            BusinessResult<TokenResponse> result = authenticationService.login(loginRequest);
            if (result.getStatusCode() > 0) {
                return ResponseEntity.ok(result.getData());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getMessage());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //Register
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest) {
        try {
            authenticationService.register(registerRequest);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
