package com.github.Lama591divine.interfaces;

import com.github.Lama591divine.entities.Account;

import java.util.ArrayList;

public interface AccountRepository {
     void add(Account account);

     void remove(Account account);

     void clear();

     Account getAccount(String id);

     ArrayList<Account> getAccounts();
}
