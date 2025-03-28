package com.github.Lama591divine.DTO;

import com.github.Lama591divine.enums.Gender;
import com.github.Lama591divine.enums.HairColor;

import java.util.HashSet;
import java.util.Set;

/**
 * The {@code DtoUser} class is a Data Transfer Object (DTO) that represents a user
 * in the banking system. It is used to transfer user data between different application layers.
 */
public record DtoUser(String login, String name, int age, Gender gender, HairColor hairColor, Set<String> friends) {

    @Override
    public Set<String> friends() {
        return new HashSet<>(friends);
    }
}
