package com.online.store.dao;

import com.online.store.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    User create(User user);

    User update(User user);

    Optional<User> getById(Long id);

    boolean delete(Long id);

    List<User> getAll();
}
