package com.github.Lama591divine.DbDao;

import com.github.Lama591divine.User;

public class DbUserDao extends SessionDb<User> {
    public DbUserDao() {
        super(User.class);
    }
}


