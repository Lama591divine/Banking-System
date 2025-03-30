package com.github.Lama591divine.InMemoryDao;

import com.github.Lama591divine.Account;
import com.github.Lama591divine.Dao;

import java.util.ArrayList;
import java.util.List;

public class InMemoryAccountDao implements Dao<Account> {
    private final List<Account> accounts;

    public InMemoryAccountDao() {
        accounts = new ArrayList<>();
    }

    @Override
    public void create(Account account) {
        accounts.add(account);
    }

    @Override
    public void delete(Account account) {
        accounts.removeIf(a -> a.getId().equals(account.getId()));
    }

    @Override
    public void update(Account account) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getId().equals(account.getId())) {
                accounts.set(i, account);
                return;
            }
        }
        accounts.add(account);
    }


    @Override
    public Account getObjectById(String id) {
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return account;
            }
        }
        return null;
    }

    @Override
    public List<Account> getAll() {
        return new ArrayList<>(accounts);
    }

    @Override
    public void close() {
        accounts.clear();
    }
}
