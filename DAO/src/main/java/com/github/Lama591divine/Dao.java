package com.github.Lama591divine;

import java.util.List;

public interface Dao<T>  {
    void create(T object);
    void delete(T object);
    void update(T object);
    T getObjectById(String id);
    List<T> getAll();
    void close();
}
