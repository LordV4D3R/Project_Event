package com.antran.projectevent.repository;

import com.antran.projectevent.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
