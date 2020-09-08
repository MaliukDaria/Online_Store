package com.online.store.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, I> {
    T create(T item);

    T update(T item);

    Optional<T> getById(I id);

    boolean delete(I id);

    List<T> getAll();
}
