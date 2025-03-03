package com.github.Lama591divine;

import com.github.Lama591divine.entities.User;
import com.github.Lama591divine.interfaces.UserRepository;

import java.util.ArrayList;

/**
 * The {@code InMemmoryUserRepository} class provides an in-memory implementation of the
 * {@code UserRepository} interface, storing users in a list.
 */
public class InMemmoryUserRepository implements UserRepository {
    private ArrayList<User> users;

    /**
     * Constructs an empty in-memory user repository.
     */
    public InMemmoryUserRepository() {
        users = new ArrayList<>();
    }

    /**
     * Adds a new user to the repository.
     *
     * @param user the user to add
     */
    public void add(User user) {
        users.add(user);
    }

    /**
     * Removes a user from the repository.
     *
     * @param user the user to remove
     */
    public void remove(User user) {
        users.remove(user);
    }

    /**
     * Clears all users from the repository.
     */
    public void clear() {
        users.clear();
    }

    /**
     * Retrieves a user by their login.
     *
     * @param login the login of the user to retrieve
     * @return the user if found, otherwise {@code null}
     */
    public User getUser(String login) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Returns a copy of the list of all stored users.
     *
     * @return a new list containing all users
     */
    public ArrayList<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}
