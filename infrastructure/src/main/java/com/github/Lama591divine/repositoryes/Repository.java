package com.github.Lama591divine.repositoryes;

import java.util.ArrayList;

public interface Repository<T> {
    void add(T object);

    void remove(T object);

    void clear();

    T getObject(String id);

    ArrayList<T> getAll();
}
