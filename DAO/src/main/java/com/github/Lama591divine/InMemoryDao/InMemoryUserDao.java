package com.github.Lama591divine.InMemoryDao;

import com.github.Lama591divine.Dao;
import com.github.Lama591divine.User;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserDao implements Dao<User> {
    private final List<User> users;

    public InMemoryUserDao() {
        users = new ArrayList<>();
    }

    @Override
    public void create(User user) {
        users.add(user);
    }

    @Override
    public void delete(User user) {
        users.remove(user);
    }

    @Override
    public void update(User user) {
        for (int id = 0; id < users.size(); id++) {
            if (users.get(id).getLogin().equals(user.getLogin())) {
                users.set(id, user);
                return;
            }
        }
        users.add(user);
    }

    @Override
    public User getObjectById(String login) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users);
    }

    @Override
    public void close() {
        users.clear();
    }
}