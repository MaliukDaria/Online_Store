package com.online.store.service;

import java.util.List;

public interface GenericService<T, I> {
    T create(T item);

    T getById(I id);

    List<T> getAll();

    boolean delete(I id);
}
