package com.online.store.service;

import java.util.List;

public interface GenericService<T, I> {
    T create(T item);

    T get(I id);

    List<T> getAll();

    boolean delete(I id);
}
