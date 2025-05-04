package com.github.lama591divine.dao;

import com.github.lama591divine.entitys.User;
import com.github.lama591divine.enums.Gender;
import com.github.lama591divine.enums.HairColor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, String> {
    List<User> findByHairColorAndGender(HairColor hairColor, Gender gender);
}
