package com.github.Lama591divine.repositoryes;

import com.github.Lama591divine.DTO.DtoAccount;

import java.util.ArrayList;

/**
 * The {@code InMemoryAccountRepository} class provides an in-memory implementation of the
 * {@code AccountRepository} interface, storing accounts in a list.
 */
public class InMemoryAccountRepository implements Repository<DtoAccount> {
    private final ArrayList<DtoAccount> accounts;

    /**
     * Constructs an empty in-memory account repository.
     */
    public InMemoryAccountRepository() {
        accounts = new ArrayList<>();
    }

    /**
     * Adds a new account to the repository.
     *
     * @param account the account to add
     */
    public void add(DtoAccount account) {
        accounts.add(account);
    }

    /**
     * Removes an account from the repository if it exists.
     *
     * @param account the account to remove
     */
    public void remove(DtoAccount account) {
        accounts.removeIf(a -> a.id().equals(account.id()));
    }


    /**
     * Clears all accounts from the repository.
     */
    public void clear() {
        accounts.clear();
    }

    /**
     * Retrieves an account by its unique identifier.
     *
     * @param id the ID of the account to retrieve
     * @return the account if found, otherwise {@code null}
     */
    public DtoAccount getObject(String id) {
        for (DtoAccount account : accounts) {
            if (account.id().equals(id)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Returns a copy of the list of all stored accounts.
     *
     * @return a new list containing all accounts
     */
    public ArrayList<DtoAccount> getAll() {
        return new ArrayList<>(accounts);
    }
}
