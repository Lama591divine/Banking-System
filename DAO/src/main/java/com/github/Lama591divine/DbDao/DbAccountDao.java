package com.github.Lama591divine.DbDao;

import com.github.Lama591divine.Account;

public class DbAccountDao extends SessionDb<Account> {
    public DbAccountDao() {
        super(Account.class);
    }
}
