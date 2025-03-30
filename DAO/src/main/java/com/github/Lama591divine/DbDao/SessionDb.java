package com.github.Lama591divine.DbDao;

import com.github.Lama591divine.Account;
import com.github.Lama591divine.Dao;
import com.github.Lama591divine.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public abstract class SessionDb<T> implements Dao<T> {
    protected final SessionFactory sessionFactory;
    protected Session session;
    private final Class<T> clazz;

    public SessionDb(Class<T> clazz) {
        this.clazz = clazz;
        this.sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Account.class)
                .buildSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @Override
    public void create(T object) {
        session.persist(object);
        session.getTransaction().commit();
        session.beginTransaction();
    }

    @Override
    public void delete(T object) {
        session.remove(object);
        session.getTransaction().commit();
        session.beginTransaction();
    }

    @Override
    public void update(T object) {
        session.merge(object);
        session.getTransaction().commit();
        session.beginTransaction();
    }


    @Override
    public T getObjectById(String id) {
        return session.get(clazz, id);
    }

    @Override
    public List<T> getAll() {
        String hql = "FROM " + clazz.getSimpleName();
        return session.createQuery(hql, clazz).getResultList();
    }

    @Override
    public void close(){
        session.close();
    }
}
