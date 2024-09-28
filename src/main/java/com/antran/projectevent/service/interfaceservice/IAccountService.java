package com.antran.projectevent.service.interfaceservice;

import com.antran.projectevent.dto.LoginRequest;
import com.antran.projectevent.dto.RegisterRequest;
import com.antran.projectevent.dto.TokenResponse;
import com.antran.projectevent.model.Account;

import java.util.List;
import java.util.UUID;

public interface IAccountService {
    List<Account> getAllAccounts();
    Account getAccountById(UUID id);
    Account updateAccountById(UUID id, Account updateAccount);
    Account addAccount(Account account);
    void deleteAccountById(UUID id);
    TokenResponse login(LoginRequest loginRequest);
    Account register(RegisterRequest registerRequest);
}
