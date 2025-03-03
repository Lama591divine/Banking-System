package com.github.Lama591divine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class User {

    private String login;
    private String name;
    private int age;
    private Gender gender;
    private HairColor hairColor;
    private Set<User> friends;
    private ArrayList<Account> accounts;

    public User(String login, String name, int age, Gender gender, HairColor hairColor) {
        this.login = login;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.hairColor = hairColor;
        this.friends = new HashSet<>();
        this.accounts = new ArrayList<>();
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

    public Set<User> getFriends() {
        return new HashSet<>(friends);
    }

    public void addFriend(User friend) {
        friends.add(friend);
        friend.getFriends().add(this);
    }

    public void removeFriend(User friend) {
        friends.remove(friend);
        friend.getFriends().remove(this);
    }

    public void showFriends() {
        if (friends.isEmpty()) {
            System.out.println(login + " has no friends.");
        } else {
            System.out.println("Friends of " + login + ":");
            for (User friend : friends) {
                System.out.println("- " + friend.getLogin());
            }
        }
    }
}
