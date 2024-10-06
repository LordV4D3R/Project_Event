package com.antran.projectevent.controller;

import com.antran.projectevent.constant.common.AppConstants;
import com.antran.projectevent.dto.LoginRequest;
import com.antran.projectevent.dto.RegisterRequest;
import com.antran.projectevent.dto.TokenResponse;
import com.antran.projectevent.exception.ResourceNotFoundException;
import com.antran.projectevent.model.Account;
import com.antran.projectevent.service.interfaceservice.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    //Get all users
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    //Get user by id
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAccountById(@PathVariable UUID id) {
        Optional<Account> account = accountService.getAccountById(id);
        return account.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Account not found"));
    }

    //Add new user
    @PostMapping
    public Account addAccount(@RequestBody Account account) {
        return accountService.addAccount(account);
    }

    //Update user by id
    @PutMapping("/{id}")
    public Account updateAccountById(@PathVariable UUID id, @RequestBody Account account) {
        return accountService.updateAccountById(id, account);
    }

    //Delete user by id
    @DeleteMapping("/{id}")
    public void deleteAccountById(@PathVariable UUID id) {
        accountService.deleteAccountById(id);
    }

    //Login
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
    TokenResponse tokenResponse = accountService.login(loginRequest);

    if (tokenResponse == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    return ResponseEntity.ok(tokenResponse);
    }

    //Register
//    @PostMapping("/register")
//    public Account register(@RequestBody RegisterRequest registerRequest) {
//        return accountService.register(registerRequest);
//    }

}
