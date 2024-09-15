package com.antran.projectevent.service;

import com.antran.projectevent.model.Account;
import com.antran.projectevent.repository.AccountRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    //Get all accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    //Get account by id
    public Account getAccountById(UUID id) {
        return accountRepository.findById(id).orElse(null);
    }

    //Update account by id
    public Account updateAccountById(UUID id, Account updateAccount) {
        return accountRepository.findById(id).map(existingAccount -> {
            BeanUtils.copyProperties(updateAccount, existingAccount, "id", "feedbacks", "orders");
            return accountRepository.save(existingAccount);
        }).orElse(null);
    }

    //Add new account
    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }

    //Delete account by id
    public void deleteAccountById(UUID id) {
        accountRepository.deleteById(id);
    }
}
