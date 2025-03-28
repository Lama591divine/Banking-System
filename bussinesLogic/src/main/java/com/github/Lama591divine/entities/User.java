package com.github.Lama591divine.entities;

import java.util.HashSet;
import java.util.Set;
import com.github.Lama591divine.enums.HairColor;
import com.github.Lama591divine.enums.Gender;
import lombok.Getter;

/**
 * The {@code User} class represents a user in the banking system.
 * Each user has a login, name, age, gender, hair color, and a list of friends.
 */
@Getter
public class User {

    private final String login;
    private final String name;
    private final int age;
    private final Gender gender;
    private final HairColor hairColor;
    private final Set<String> friends;

    /**
     * Constructs a new User.
     *
     * @param login     the unique login of the user
     * @param name      the name of the user
     * @param age       the age of the user
     * @param gender    the gender of the user
     * @param hairColor the hair color of the user
     * @param friends   a set of friend logins
     */
    public User(String login, String name, int age, Gender gender, HairColor hairColor, Set<String> friends) {
        this.login = login;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.hairColor = hairColor;
        this.friends = friends;
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

    /**
     * Displays the list of friends of the user.
     * If the user has no friends, an appropriate message is printed.
     */
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
