package com.github.Lama591divine;

import com.github.Lama591divine.entities.Account;
import com.github.Lama591divine.interfaces.AccountRepository;

import java.util.ArrayList;

/**
 * The {@code InMemmoryAccountRepository} class provides an in-memory implementation of the
 * {@code AccountRepository} interface, storing accounts in a list.
 */
public class InMemmoryAccountRepository implements AccountRepository {
    private ArrayList<Account> accounts;

    /**
     * Constructs an empty in-memory account repository.
     */
    public InMemmoryAccountRepository() {
        accounts = new ArrayList<>();
    }

    /**
     * Adds a new account to the repository.
     *
     * @param account the account to add
     */
    public void add(Account account) {
        accounts.add(account);
    }

    /**
     * Removes an account from the repository if it exists.
     *
     * @param account the account to remove
     */
    public void remove(Account account) {
        if (accounts.contains(account)) {
            accounts.remove(account);
        }
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
    public Account getAccount(String id) {
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
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
    public ArrayList<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }
}
