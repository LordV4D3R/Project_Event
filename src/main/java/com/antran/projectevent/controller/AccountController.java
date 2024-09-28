package com.antran.projectevent.controller;

import com.antran.projectevent.dto.LoginRequest;
import com.antran.projectevent.dto.RegisterRequest;
import com.antran.projectevent.dto.TokenResponse;
import com.antran.projectevent.model.Account;
import com.antran.projectevent.service.interfaceservice.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public Account getAccountById(@PathVariable UUID id) {
        return accountService.getAccountById(id);
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
    public TokenResponse login(@RequestBody LoginRequest loginRequest) {
        return accountService.login(loginRequest);
    }

    //Register
    @PostMapping("/register")
    public Account register(@RequestBody RegisterRequest registerRequest) {
        return accountService.register(registerRequest);
    }

}
