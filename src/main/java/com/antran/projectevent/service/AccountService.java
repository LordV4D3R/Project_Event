package com.antran.projectevent.service;

import com.antran.projectevent.dto.LoginRequest;
import com.antran.projectevent.dto.RegisterRequest;
import com.antran.projectevent.dto.TokenResponse;
import com.antran.projectevent.exception.ResourceNotFoundException;
import com.antran.projectevent.model.Account;
import com.antran.projectevent.repository.AccountRepository;
import com.antran.projectevent.service.interfaceservice.IAccountService;
import com.antran.projectevent.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Get all accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    //Get account by id
    public Account getAccountById(UUID id) {
        return accountRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Account not found with id " + id));
    }

    //Update account by id
    public Account updateAccountById(UUID id, Account updateAccount) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account != null) {
            account.setUsername(updateAccount.getUsername());
            account.setPassword(updateAccount.getPassword());
            account.setMainEmail(updateAccount.getMainEmail());
            account.setFullName(updateAccount.getFullName());
            account.setMainPhoneNumber(updateAccount.getMainPhoneNumber());
            account.setBirthDate(updateAccount.getBirthDate());
            account.setSex(updateAccount.getSex());
            account.setMainAddress(updateAccount.getMainAddress());
            account.setSystemRole(updateAccount.getSystemRole());
            account.setAccountStatus(updateAccount.getAccountStatus());
            return accountRepository.save(account);
        } else {
            return null;
        }
    }

    //Add new account
    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }

    //Delete account by id
    public void deleteAccountById(UUID id) {
        accountRepository.deleteById(id);
    }

    //Login
    public TokenResponse login(LoginRequest loginRequest) {
        Optional<Account> accountOpt = accountRepository.findByUsername(loginRequest.getIdentifier());
        if (!accountOpt.isPresent()) {
            accountOpt = accountRepository.findByEmail(loginRequest.getIdentifier());
        }
        if (accountOpt.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), accountOpt.get().getPassword())) {
            String accessToken = jwtUtil.generateToken(accountOpt.get().getUsername());
            String refreshToken = jwtUtil.generateToken(accountOpt.get().getUsername()); // Simplified for example
            return new TokenResponse(accessToken, refreshToken);
        } else {
            return null;
        }
    }

    //User registration
    public Account register(RegisterRequest registerRequest) {
        Account account = new Account();
        account.setUsername(registerRequest.getUsername());
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        account.setMainEmail(registerRequest.getMainEmail());
        return accountRepository.save(account);
    }



}
