package com.github.Lama591divine.entities;

import java.util.HashSet;
import java.util.Set;

/**
 * The {@code User} class represents a user in the banking system.
 * Each user has a login, name, age, gender, hair color, and a list of friends.
 */
public class User {

    private String login;
    private String name;
    private int age;
    private Gender gender;
    private HairColor hairColor;
    private Set<User> friends;

    /**
     * @param login     the unique login of the user
     * @param name      the name of the user
     * @param age       the age of the user
     * @param gender    the gender of the user
     * @param hairColor the hair color of the user
     */
    public User(String login, String name, int age, Gender gender, HairColor hairColor) {
        this.login = login;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.hairColor = hairColor;
        this.friends = new HashSet<>();
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

    /**
     * Adds a user to the friend list of this user and reciprocates the friendship.
     *
     * @param friend the user to be added as a friend
     */
    public void addFriend(User friend) {
        friends.add(friend);
        friend.getFriends().add(this);
    }

    /**
     * Removes a user from the friend list of this user and reciprocates the removal.
     *
     * @param friend the user to be removed from the friend list
     */
    public void removeFriend(User friend) {
        friends.remove(friend);
        friend.getFriends().remove(this);
    }

    /**
     * Displays the list of friends of this user.
     * If no friends are present, a message indicating no friends is displayed.
     */
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
