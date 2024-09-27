package com.antran.projectevent;

import com.antran.projectevent.model.Account;
import com.antran.projectevent.repository.AccountRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;


@Configuration
public class LoadData {

    @Autowired
    private AccountRepository accountRepository;

    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        //Load Data
        TypeReference<List<Account>> accountReference = new TypeReference<List<Account>>() {};
        InputStream inputStream = new ClassPathResource("data/AccountData.json").getInputStream();
        List<Account> accounts = mapper.readValue(inputStream, accountReference);

        for (Account account : accounts) {
            Optional<Account> existingAccount = accountRepository.findById(account.getId());
            if (existingAccount.isEmpty()) {
                accountRepository.save(account);
            }
        }

        //Load Success
        System.out.println("Data loaded successfully");

    }
}

