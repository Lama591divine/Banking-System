package com.github.Lama591divine;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserDao implements Dao<User> {
    private final List<User> users;

    public InMemoryUserDao() {
        users = new ArrayList<>();
    }

    public void add(User user) {
        users.add(user);
    }

    public void remove(User user) {
        users.remove(user);
    }

    public User getObjectById(String login) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getAll() {
        return new ArrayList<>(users);
    }
}