package com.antran.projectevent.service;

import com.antran.projectevent.dto.LoginRequest;
import com.antran.projectevent.dto.RegisterRequest;
import com.antran.projectevent.dto.TokenData;
import com.antran.projectevent.dto.TokenResponse;
import com.antran.projectevent.exception.ResourceNotFoundException;
import com.antran.projectevent.model.Account;
import com.antran.projectevent.repository.AccountRepository;
import com.antran.projectevent.service.interfaceservice.IAccountService;
import com.antran.projectevent.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    //Get all accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Lấy người dùng theo ID
    public Optional<Account> getAccountById(UUID id) {
        try {
            return accountRepository.findById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Account not found");
        }
    }

    //Update account by id
    public Account updateAccountById(UUID id, Account updateAccount) {
        try {
            Account account = accountRepository.findById(id).get();
            account.setUsername(updateAccount.getUsername());
            account.setPassword(updateAccount.getPassword());
            account.setMainEmail(updateAccount.getMainEmail());
            return accountRepository.save(account);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Account not found");
        }
    }

    //Add new account
    public Account addAccount(Account account) {
        try {
            return accountRepository.save(account);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Account with email already exists");
        }
    }

    //Delete account by id
    public void deleteAccountById(UUID id) {
        try {
            accountRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Account not found");
        }
    }

    //Login
//    public TokenResponse login(LoginRequest loginRequest) {
//
//        Optional<Account> accountOpt = accountRepository.findByUsername(loginRequest.getIdentifier());
//        if (!accountOpt.isPresent()) {
//            accountOpt = accountRepository.findByMainEmail(loginRequest.getIdentifier());
//        }
//        if (accountOpt.isPresent() && accountOpt.get().getPassword().equals(loginRequest.getPassword())) {
//            String accessToken = jwtUtil.generateToken(accountOpt.get().getUsername());
//            String refreshToken = jwtUtil.generateToken(accountOpt.get().getUsername()); // Simplified for example
//            return new TokenResponse(accessToken, refreshToken);
//        } else {
//            return null;
//        }
//    }
    public TokenResponse login(LoginRequest loginRequest){
        try {
            Optional<Account> accountOpt = accountRepository.findByUsername(loginRequest.getIdentifier());
            if (!accountOpt.isPresent()) {
                accountOpt = accountRepository.findByMainEmail(loginRequest.getIdentifier());
            }
            if (accountOpt.isPresent() && accountOpt.get().getPassword().equals(loginRequest.getPassword())) {
                TokenData tokenResponse = new TokenData();
                tokenResponse.setAccountRole(accountOpt.get().getAccountRole());
                tokenResponse.setFullName(accountOpt.get().getFullName());
                tokenResponse.setUsername(accountOpt.get().getUsername());

                String accessToken = jwtUtil.generateToken(tokenResponse, 1000 * 60 * 60 * 10);
                String refreshToken = jwtUtil.generateToken(tokenResponse, 1000 * 60 * 60 * 60); // Simplified for example
                return new TokenResponse(accessToken, refreshToken);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new ResourceNotFoundException("Account not found");
        }
    }

    //User registration
//    public Account register(RegisterRequest registerRequest) {
//        Account account = new Account();
//        account.setUsername(registerRequest.getUsername());
//        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
//        account.setMainEmail(registerRequest.getMainEmail());
//        return accountRepository.save(account);
//    }



}
