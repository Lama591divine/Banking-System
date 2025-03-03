package com.github.Lama591divine.interfaces;

import com.github.Lama591divine.entities.User;

import java.util.ArrayList;

public interface UserRepository {
     void add(User user);

     void remove(User user);

     void clear();

     User getUser(String login);

     ArrayList<User> getAllUsers();
}
