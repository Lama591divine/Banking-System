package com.github.Lama591divine.entities;

import java.util.HashSet;
import java.util.Set;
import com.github.Lama591divine.enums.HairColor;
import com.github.Lama591divine.enums.Gender;

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

    public void addFriend(String friendid) {
        friends.add(friendid);
    }

    public void removeFriend(String friendid) {
        friends.remove(friendid);
    }

    public void showFriends() {
        if (friends.isEmpty()) {
            System.out.println(login + " has no friends.");
        }
        else {
            System.out.println("Friends of " + login + ":");
            for (String friend : friends) {
                System.out.println("- " + friend);
            }
        }
    }
}
