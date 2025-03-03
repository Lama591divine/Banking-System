package com.github.Lama591divine;

import com.github.Lama591divine.entities.Account;
import com.github.Lama591divine.interfaces.AccountRepository;

import java.util.ArrayList;

public class InMemmoryAccountRepository implements AccountRepository {
    private ArrayList<Account> accounts;

    public InMemmoryAccountRepository() {
        accounts = new ArrayList<>();
    }

    public void add(Account account) {
        accounts.add(account);
    }

    public void remove(Account account) {
        if(accounts.contains(account)) {
            accounts.remove(account);
        }
    }

    public void clear() {
        accounts.clear();
    }

    public Account getAccount(String id) {
        for (Account account : accounts) {
            if(account.getId().equals(id)){
                return account;
            }
        }
        return null;
    }

    public ArrayList<Account> getAccounts() {
        return new ArrayList<Account>(accounts);
    }
}

