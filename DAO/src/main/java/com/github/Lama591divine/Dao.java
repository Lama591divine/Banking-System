package com.github.Lama591divine;

import java.util.List;

public interface Dao<T> {
    void add(T object);
    void remove(T object);
    T getObjectById(String id);
    List<T> getAll();
}
