package com.antran.projectevent.service;

import com.antran.projectevent.constant.enums.AccountRole;
import com.antran.projectevent.constant.enums.AccountStatus;
import com.antran.projectevent.exception.ResourceNotFoundException;
import com.antran.projectevent.model.Account;
import com.antran.projectevent.model.dto.LoginRequest;
import com.antran.projectevent.model.dto.RegisterRequest;
import com.antran.projectevent.model.dto.TokenData;
import com.antran.projectevent.model.dto.TokenResponse;
import com.antran.projectevent.repository.AccountRepository;
import com.antran.projectevent.service.interfaceservice.IAuthenticationService;
import com.antran.projectevent.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements IAuthenticationService, UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired @Lazy
    PasswordEncoder passwordEncoder;

    //User login
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
    public Account register(RegisterRequest registerRequest) {
        try {
            String originalPassword = registerRequest.getPassword();
            Account account = new Account();
            account.setUsername(registerRequest.getUsername());
            account.setPassword(passwordEncoder.encode(originalPassword));
            account.setMainEmail(registerRequest.getMainEmail());
            account.setFullName(registerRequest.getFullName());
            account.setMainPhoneNumber(registerRequest.getMainPhoneNumber());
            account.setBirthDate(registerRequest.getBirthDate());
            account.setSex(registerRequest.getSex());
            account.setMainAddress(registerRequest.getMainAddress());
            account.setAccountRole(AccountRole.MEMBER);
            account.setAccountStatus(AccountStatus.ACTIVE);
            Account newAccount = accountRepository.save(account);
            return newAccount;
        }
        catch (Exception e) {
            if (e.getMessage().contains(registerRequest.getUsername())) {
                throw new ResourceNotFoundException("Username already exists");
            } else if (e.getMessage().contains(registerRequest.getUsername())) {
                throw new ResourceNotFoundException("Email already exists");
            } else if (e.getMessage().contains(registerRequest.getMainPhoneNumber())) {
                throw new ResourceNotFoundException("Phone number already exists");
            } else {
                throw new ResourceNotFoundException("Error");
            }
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
