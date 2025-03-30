package com.github.Lama591divine;

import java.util.ArrayList;
import java.util.List;

public class InMemoryAccountDao implements Dao<Account> {
    private final List<Account> accounts;

    public InMemoryAccountDao() {
        accounts = new ArrayList<>();
    }

    public void add(Account account) {
        accounts.add(account);
    }

    public void remove(Account account) {
        accounts.removeIf(a -> a.getId().equals(account.getId()));
    }

    public Account getObjectById(String id) {
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return account;
            }
        }
        return null;
    }

    public List<Account> getAll() {
        return new ArrayList<>(accounts);
    }
}
