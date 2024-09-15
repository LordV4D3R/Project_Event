package com.antran.projectevent;

import com.antran.projectevent.model.Account;
import com.antran.projectevent.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Configuration
public class LoadData {
    private static final Logger logger = LoggerFactory.getLogger(LoadData.class);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    UUID id = UUID.randomUUID();

    @Bean
    CommandLineRunner commandLineRunner(AccountRepository accountRepository) {
        return args -> {
            logger.info("Preloading " + accountRepository.save(new Account("an123", "12345", "an@gmail.com", "An",
                    "0909090909", LocalDate.parse("31/12/2000", formatter), Account.sex.MALE,
                    "123 Washiton", Account.systemRole.ADMIN, Account.accountStatus.ACTIVE, null, null)));

            logger.info("Preloading " + accountRepository.save(new Account("dat123", "12345", "dat@gmail.com", "Dat",
                    "0987654321", LocalDate.parse("30/12/2000", formatter), Account.sex.MALE,
                    "123 Washiton", Account.systemRole.MEMBER, Account.accountStatus.ACTIVE, null, null)));

            logger.info("Preloading " + accountRepository.save(new Account("na123", "12345",
                    "na@gmail.com", "Nhut Anh", "0987654321",
                    LocalDate.parse("29/12/2000", formatter), Account.sex.MALE, "123 Washiton",
                    Account.systemRole.MEMBER, Account.accountStatus.ACTIVE, null, null)));

            logger.info("Preloading " + accountRepository.save(new Account("na123", "12345",
                    "na@gmail.com", "Nhut Anh", "0987654321",
                    LocalDate.parse("29/12/2000", formatter), Account.sex.MALE, "123 Washiton",
                    Account.systemRole.MEMBER, Account.accountStatus.ACTIVE, null, null)));

        };
    }
        private void addAccountIfNotExists(AccountRepository accountRepository, String username, String password,
                                       String email, String fullName, String phoneNumber, LocalDate birthDate,
                                       Account.sex sex, String address, Account.systemRole role,
                                       Account.accountStatus status) {
        Optional<Account> existingAccount = accountRepository.findByUsername(username);
        if (existingAccount.isEmpty()) {
            logger.info("Preloading " + accountRepository.save(new Account(username, password, email, fullName,
                                    phoneNumber, birthDate, sex, address, role, status, null, null)));
        } else {
            logger.info("Account with username " + username + " already exists.");
        }
    }
}

