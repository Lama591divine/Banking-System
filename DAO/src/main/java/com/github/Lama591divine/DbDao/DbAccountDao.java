package com.github.Lama591divine.DbDao;

import com.github.Lama591divine.Account;
import org.hibernate.Hibernate;
import org.hibernate.Session;

public class DbAccountDao extends SessionDb<Account> {
    public DbAccountDao() {
        super(Account.class);
    }

    @Override
    public Account getObjectById(String id) {
        try (Session session = sessionFactory.openSession()) {
            Account account = session.get(Account.class, id);
            if (account != null) {
                Hibernate.initialize(account.getOwner());
                Hibernate.initialize(account.getTransactions());
            }
            return account;
        }
    }
}
