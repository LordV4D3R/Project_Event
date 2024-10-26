package com.antran.projectevent.service.interfaceservice;

import com.antran.projectevent.constant.common.BusinessResult;
import com.antran.projectevent.model.dto.LoginRequest;
import com.antran.projectevent.model.dto.RegisterRequest;
import com.antran.projectevent.model.dto.TokenResponse;
import com.antran.projectevent.model.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAccountService {
    BusinessResult<List<Account>> getAllAccounts(String search);
    Optional<Account> getAccountById(UUID id);
    Account updateAccountById(UUID id, Account updateAccount);
    Account addAccount(Account account);
    void deleteAccountById(UUID id);
}
