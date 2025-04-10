package com.github.Lama591divine.DbDao;

import com.github.Lama591divine.Dao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public abstract class SessionDb<T> implements Dao<T> {
    protected final SessionFactory sessionFactory;
    private final Class<T> clazz;

    public SessionDb(Class<T> clazz, SessionFactory sessionFactory) {
        this.clazz = clazz;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(T object) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(object);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(T object) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(object);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(T object) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(object);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<T> getAll() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM " + clazz.getSimpleName();
            return session.createQuery(hql, clazz).getResultList();
        }
    }

    public abstract T getObjectById(String id);
}
