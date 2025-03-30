package com.github.Lama591divine;

import lombok.Getter;

import java.util.Set;

@Getter
public class User {
    private final String login;
    private final String name;
    private final int age;
    private final Gender gender;
    private final HairColor hairColor;
    private final Set<String> friends;

    public User(String login, String name, int age, Gender gender, HairColor hairColor, Set<String> friends) {
        this.login = login;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.hairColor = hairColor;
        this.friends = friends;
    }
}
