package com.github.lama591divine.dao;

import com.github.lama591divine.entitys.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDao extends JpaRepository<Account, String> {
}
