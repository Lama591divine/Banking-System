package com.github.lama591divine.dao;

import com.github.lama591divine.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, String> {
}
