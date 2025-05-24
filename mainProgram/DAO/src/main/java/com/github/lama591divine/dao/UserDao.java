package com.github.lama591divine.dao;

import com.github.lama591divine.entities.User;
import com.github.lama591divine.enums.Gender;
import com.github.lama591divine.enums.HairColor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {
    List<User> findByHairColorAndGender(HairColor hairColor, Gender gender);
    Optional<User> findByLogin(String login);
}
