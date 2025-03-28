package com.github.Lama591divine.repositoryes;

import com.github.Lama591divine.DTO.DtoUser;

import java.util.ArrayList;

/**
 * The {@code InMemoryUserRepository} class provides an in-memory implementation of the
 * {@code UserRepository} interface, storing users in a list.
 */
public class InMemoryUserRepository implements Repository<DtoUser> {
    private final ArrayList<DtoUser> users;

    /**
     * Constructs an empty in-memory user repository.
     */
    public InMemoryUserRepository() {
        users = new ArrayList<>();
    }

    /**
     * Adds a new user to the repository.
     *
     * @param user the user to add
     */
    public void add(DtoUser user) {
        users.add(user);
    }

    /**
     * Removes a user from the repository.
     *
     * @param user the user to remove
     */
    public void remove(DtoUser user) {
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
    public DtoUser getObject(String login) {
        for (DtoUser user : users) {
            if (user.login().equals(login)) {
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
    public ArrayList<DtoUser> getAll() {
        return new ArrayList<>(users);
    }
}
