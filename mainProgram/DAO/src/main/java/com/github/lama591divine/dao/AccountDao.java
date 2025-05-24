package com.github.lama591divine.dao;

import com.github.lama591divine.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountDao extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(UUID accountNumber);
}