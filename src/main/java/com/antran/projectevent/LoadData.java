package com.antran.projectevent;

import com.antran.projectevent.model.Account;
import com.antran.projectevent.repository.AccountRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;


@Configuration
public class LoadData implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    public void loadDataFromJsonToSqlServer() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Đăng ký JavaTimeModule để hỗ trợ LocalDateTime
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Không ghi ngày dưới dạng timestamp

        try {
            // Read JSON file from resources
            InputStream inputStream = new ClassPathResource("data/AccountData.json").getInputStream();
            List<Account> accounts = objectMapper.readValue(inputStream, new TypeReference<List<Account>>() {});

            // Save data to the database
            if (accounts != null && !accounts.isEmpty()) {
                accountRepository.saveAll(accounts);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        loadDataFromJsonToSqlServer();
        System.out.println("Data loaded successfully");
    }

}

