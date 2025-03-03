package com.github.Lama591divine;

import java.util.ArrayList;

public class UserRepository {
    private ArrayList<User> users;

    public UserRepository() {
        users = new ArrayList<>();
    }

    public void add(User user) {
        users.add(user);
    }

    public void remove(User user) {
        users.remove(user);
    }

    public void clear() {
        users.clear();
    }

    public User getUser(String login) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    public ArrayList<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}


