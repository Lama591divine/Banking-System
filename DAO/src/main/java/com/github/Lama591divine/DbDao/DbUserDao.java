package com.github.Lama591divine.DbDao;

import com.github.Lama591divine.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;

public class DbUserDao extends SessionDb<User> {
    public DbUserDao() {
        super(User.class);
    }

    @Override
    public User getObjectById(String id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            if (user != null) {
                Hibernate.initialize(user.getAccounts());
                Hibernate.initialize(user.getFriends());
            }
            return user;
        }
    }
}


