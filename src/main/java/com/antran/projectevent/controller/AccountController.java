package com.antran.projectevent.controller;

import com.antran.projectevent.model.Account;
import com.antran.projectevent.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    //Get all users
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    //Get user by id
    @GetMapping("/{id}")
    private Account getAccountById(UUID id) {
        return accountService.getAccountById(id);
    }

    //Add new user
    @PostMapping
    public Account addAccount(Account account) {
        return accountService.addAccount(account);
    }

    //Delete user by id
    @DeleteMapping("/{id}")
    public void deleteAccountById(UUID id) {
        accountService.deleteAccountById(id);
    }

}
