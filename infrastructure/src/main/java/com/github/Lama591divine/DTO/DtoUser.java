package com.github.Lama591divine.DTO;

import com.github.Lama591divine.enums.Gender;
import com.github.Lama591divine.enums.HairColor;

import java.util.HashSet;
import java.util.Set;

public class DtoUser {
    private String login;
    private String name;
    private int age;
    private Gender gender;
    private HairColor hairColor;
    private Set<String> friends;

    public DtoUser(String login, String name, int age, Gender gender, HairColor hairColor, Set<String> friends) {
        this.login = login;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.hairColor = hairColor;
        this.friends = friends;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public HairColor getHairColor() {
        return hairColor;
    }

    public Set<String> getFriends() {
        return new HashSet<>(friends);
    }
}
