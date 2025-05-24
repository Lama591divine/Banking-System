package com.github.lama591divine.dao;

import com.github.lama591divine.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDao extends JpaRepository<Transaction, Long> {}
