package com.online.store.dao.impl;

import com.online.store.dao.UserDao;
import com.online.store.db.Storage;
import com.online.store.lib.Dao;
import com.online.store.model.User;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public User create(User user) {
        Storage.addUser(user);
        return user;
    }

    @Override
    public User update(User user) {
        IntStream.range(0, Storage.users.size())
                .filter(i -> Storage.users.get(i).getId().equals(user.getId()))
                .forEach(i -> Storage.users.set(i, user));
        return user;
    }

    @Override
    public Optional<User> getById(Long id) {
        return Storage.users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean delete(Long userId) {
        return Storage.users.removeIf(u -> u.getId().equals(userId));
    }

    @Override
    public List<User> getAll() {
        return Storage.users;
    }
}
